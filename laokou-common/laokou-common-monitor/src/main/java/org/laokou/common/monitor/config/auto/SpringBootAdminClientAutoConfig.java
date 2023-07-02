/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.laokou.common.monitor.config.auto;

import de.codecentric.boot.admin.client.config.ClientProperties;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.config.SpringBootAdminClientEnabledCondition;
import de.codecentric.boot.admin.client.registration.*;
import de.codecentric.boot.admin.client.registration.metadata.CompositeMetadataContributor;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import de.codecentric.boot.admin.client.registration.metadata.StartupDateMetadataContributor;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.ServletContext;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ExtractingResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

/**
 * @author laokou
 */
@ComponentScan(value = "org.laokou.common.monitor")
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@AutoConfiguration
@Conditional(SpringBootAdminClientEnabledCondition.class)
@AutoConfigureAfter({ WebEndpointAutoConfiguration.class, RestTemplateAutoConfiguration.class,
        WebClientAutoConfiguration.class })
@EnableConfigurationProperties({ ClientProperties.class, InstanceProperties.class, ServerProperties.class,
        ManagementServerProperties.class })
public class SpringBootAdminClientAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationRegistrator registrator(RegistrationClient registrationClient, ClientProperties client,
                                              ApplicationFactory applicationFactory) {
        return new DefaultApplicationRegistrator(applicationFactory, registrationClient, client.getAdminUrl(),
                client.isRegisterOnce());
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistrationApplicationListener registrationListener(ClientProperties client,
                                                                ApplicationRegistrator registrator, Environment environment) {
        RegistrationApplicationListener listener = new RegistrationApplicationListener(registrator);
        listener.setAutoRegister(client.isAutoRegistration());
        listener.setAutoDeregister(client.isAutoDeregistration(environment));
        listener.setRegisterPeriod(client.getPeriod());
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public StartupDateMetadataContributor startupDateMetadataContributor() {
        return new StartupDateMetadataContributor();
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
    public static class ServletConfiguration {

        @Bean
        @Lazy(false)
        @ConditionalOnMissingBean
        public ApplicationFactory applicationFactory(InstanceProperties instance, ManagementServerProperties management,
                                                     ServerProperties server, ServletContext servletContext, PathMappedEndpoints pathMappedEndpoints,
                                                     WebEndpointProperties webEndpoint, ObjectProvider<List<MetadataContributor>> metadataContributors,
                                                     DispatcherServletPath dispatcherServletPath) {
            return new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints,
                    webEndpoint,
                    new CompositeMetadataContributor(metadataContributors.getIfAvailable(Collections::emptyList)),
                    dispatcherServletPath);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public static class ReactiveConfiguration {

        @Bean
        @Lazy(false)
        @ConditionalOnMissingBean
        public ApplicationFactory applicationFactory(InstanceProperties instance, ManagementServerProperties management,
                                                     ServerProperties server, PathMappedEndpoints pathMappedEndpoints, WebEndpointProperties webEndpoint,
                                                     ObjectProvider<List<MetadataContributor>> metadataContributors, WebFluxProperties webFluxProperties) {
            return new ReactiveApplicationFactory(instance, management, server, pathMappedEndpoints, webEndpoint,
                    new CompositeMetadataContributor(metadataContributors.getIfAvailable(Collections::emptyList)),
                    webFluxProperties);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(RestTemplateBuilder.class)
    public static class BlockingRegistrationClientConfig {

        @Bean
        @ConditionalOnMissingBean
        public RegistrationClient registrationClient(ClientProperties client) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
            RestTemplateBuilder builder = new RestTemplateBuilder().setConnectTimeout(client.getConnectTimeout());
            builder.setReadTimeout(client.getReadTimeout());
            if (client.getUsername() != null && client.getPassword() != null) {
                builder = builder.basicAuthentication(client.getUsername(), client.getPassword());
            }
            RestTemplate build = builder.build();
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            disableSsl(httpClientBuilder);
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            CustomMappingJackson2HttpMessageConverter converter = new CustomMappingJackson2HttpMessageConverter();
            messageConverters.add(converter);
            build.setMessageConverters(messageConverters);
            build.setRequestFactory(requestFactory);
            build.setErrorHandler(new ExtractingResponseErrorHandler());
            return new BlockingRegistrationClient(build);
        }

        @NonNullApi
        static class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
            @Override
            public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
                List<MediaType> mediaTypes = new ArrayList<>(2);
                mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
                mediaTypes.add(MediaType.valueOf("application/json"));
                super.setSupportedMediaTypes(mediaTypes);
            }
        }

        private static void disableSsl(HttpClientBuilder builder) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(sslContext)
                    .setTlsVersions("TLSv1.2")
                    .setHostnameVerifier((s, sslSession) -> true)
                    .build();
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder
                    .create().setSSLSocketFactory(sslConnectionSocketFactory).build();
            builder.setConnectionManager(poolingHttpClientConnectionManager);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(WebClient.Builder.class)
    @ConditionalOnMissingBean(RestTemplateBuilder.class)
    public static class ReactiveRegistrationClientConfig {

        @Bean
        @ConditionalOnMissingBean
        public RegistrationClient registrationClient(ClientProperties client, WebClient.Builder webClient) {
            if (client.getUsername() != null && client.getPassword() != null) {
                webClient = webClient.filter(basicAuthentication(client.getUsername(), client.getPassword()));
            }
            return new ReactiveRegistrationClient(webClient.build(), client.getReadTimeout());
        }

    }
}
