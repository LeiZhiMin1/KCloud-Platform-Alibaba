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

package org.laokou.admin.dto.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.laokou.common.i18n.dto.PageQuery;
import org.laokou.common.i18n.utils.StringUtil;

/**
 * @author laokou
 */
@Data
@Schema(name = "ResourceListQry", description = "资源列表查询参数")
public class ResourceListQry extends PageQuery {

	@Schema(name = "id", description = "ID")
	private Long id;

	@Schema(name = "status", description = "资源审批状态 0待审批 1审批中 -1驳回审批 2通过审批")
	private Integer status;

	@Schema(name = "code", description = "资源类型 audio音频 video视频  image图片")
	private String code;

	@Schema(name = "title", description = "资源名称")
	private String title;

	public void setTitle(String title) {
		this.title = StringUtil.like(title);
	}

}
