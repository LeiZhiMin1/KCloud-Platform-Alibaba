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

package org.laokou.common.i18n.utils;

import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Locale;

import static org.laokou.common.i18n.common.Constant.ROD;

/**
 * @author laokou
 */
public class LocaleUtil {

	public static Locale toLocale(String language) {
		if (StringUtil.isEmpty(language)) {
			return LocaleContextHolder.getLocale();
		}
		String[] str = language.split(ROD);
		// 国家 地区
		return new Locale(str[0], str[1]);
	}

}
