/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
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

package org.laokou.auth.module.oauth2.config;

import lombok.Data;
import org.laokou.common.core.config.OAuth2ResourceServerProperties;
import org.laokou.common.core.utils.MapUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

import static org.laokou.auth.module.oauth2.config.OAuth2AuthorizationServerProperties.PREFIX;
import static org.laokou.common.i18n.common.Constant.*;

/**
 * @author laokou
 */
@Data
@Configuration
@ConditionalOnProperty(havingValue = TRUE, matchIfMissing = true, prefix = PREFIX, name = ENABLED)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class OAuth2ResourceServerConfig {

	/**
	 * 不拦截拦截静态资源 如果您不想要警告消息并且需要性能优化，则可以为静态资源引入第二个过滤器链
	 * <a href="https://github.com/spring-projects/spring-security/issues/10938">...</a>
	 * @param http http
	 * @return defaultSecurityFilterChain
	 * @throws Exception Exception
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
			OAuth2ResourceServerProperties oAuth2ResourceServerProperties, Environment env) throws Exception {
		Map<String, Set<String>> uriMap = Optional
			.of(MapUtil.toUriMap(oAuth2ResourceServerProperties.getRequestMatcher().getIgnorePatterns(),
					env.getProperty(SPRING_APPLICATION_NAME)))
			.orElseGet(HashMap::new);
		return http
			.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.httpStrictTransportSecurity(
					hsts -> hsts.includeSubDomains(true).preload(true).maxAgeInSeconds(31536000)))
			.authorizeHttpRequests(request -> request
				.requestMatchers(HttpMethod.GET,
						Optional.ofNullable(uriMap.get(HttpMethod.GET.name()))
							.orElseGet(HashSet::new)
							.toArray(String[]::new))
				.permitAll()
				.requestMatchers(HttpMethod.POST,
						Optional.ofNullable(uriMap.get(HttpMethod.POST.name()))
							.orElseGet(HashSet::new)
							.toArray(String[]::new))
				.permitAll()
				.requestMatchers(HttpMethod.PUT,
						Optional.ofNullable(uriMap.get(HttpMethod.PUT.name()))
							.orElseGet(HashSet::new)
							.toArray(String[]::new))
				.permitAll()
				.requestMatchers(HttpMethod.DELETE,
						Optional.ofNullable(uriMap.get(HttpMethod.DELETE.name()))
							.orElseGet(HashSet::new)
							.toArray(String[]::new))
				.permitAll()
				.requestMatchers(HttpMethod.HEAD,
						Optional.ofNullable(uriMap.get(HttpMethod.HEAD.name()))
							.orElseGet(HashSet::new)
							.toArray(String[]::new))
				.permitAll()
				.requestMatchers(HttpMethod.PATCH,
						Optional.ofNullable(uriMap.get(HttpMethod.PATCH.name()))
							.orElseGet(HashSet::new)
							.toArray(String[]::new))
				.permitAll()
				.anyRequest()
				.authenticated())
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			// 自定义登录页面
			// https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
			// 登录页面 -> DefaultLoginPageGeneratingFilter
			.formLogin(Customizer.withDefaults())
			// 清除session
			.logout(logout -> logout.clearAuthentication(true).invalidateHttpSession(true))
			.build();
	}

}
