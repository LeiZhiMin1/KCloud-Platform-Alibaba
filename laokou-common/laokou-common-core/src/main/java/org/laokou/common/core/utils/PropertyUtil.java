/*
 * Copyright (c) 2022-2024 KCloud-Platform-IoT Author or Authors. All Rights Reserved.
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

package org.laokou.common.core.utils;

import lombok.SneakyThrows;
import org.laokou.common.i18n.utils.ResourceUtil;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * properties工具类.
 *
 * @author laokou
 */
public final class PropertyUtil {

	/**
	 * 从nacos获取配置文件并转为属性.
	 * @param bindName 配置前缀
	 * @param clazz 类
	 * @param location 文件名称
	 * @param format 格式
	 * @param <T> 泛型
	 * @return 属性
	 */
	@SneakyThrows
	public static <T> T getProperties(String bindName, Class<T> clazz, String location, String format) {
		StandardEnvironment environment = new StandardEnvironment();
		Resource resource = ResourceUtil.getResource(location);
		List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader().load(format, resource);
		propertySourceList.forEach(propertySource -> environment.getPropertySources().addLast(propertySource));
		return Binder.get(environment).bindOrCreate(bindName, clazz);
	}

}
