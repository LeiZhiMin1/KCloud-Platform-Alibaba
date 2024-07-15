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

package org.laokou.common.elasticsearch.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static org.laokou.common.elasticsearch.annotation.Constant.POST_TAGS;
import static org.laokou.common.elasticsearch.annotation.Constant.PRE_TAGS;

/**
 * @author laokou
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Search {

	private Highlight highlight;

	private List<Field> fields;

	private co.elastic.clients.elasticsearch._types.query_dsl.Query query;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Highlight {

		private List<String> preTags = List.of(PRE_TAGS);

		private List<String> postTags = List.of(POST_TAGS);

		private boolean requireFieldMatch;

		private List<HighlightField> fields;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Field {

		private List<String> names;

		private String value;

		private QueryType type;

		private Query query;

		private Condition condition = Condition.EQ;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class HighlightField {

		private String name;

		private Integer numberOfFragments = 0;

		private Integer fragmentSize = 0;

	}

}
