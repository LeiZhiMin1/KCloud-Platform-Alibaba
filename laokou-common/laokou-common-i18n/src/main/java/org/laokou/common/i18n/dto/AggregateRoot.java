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

package org.laokou.common.i18n.dto;

import lombok.Getter;

import java.time.Instant;

/**
 * 聚合根.
 *
 * @author laokou
 */
@Getter
public abstract class AggregateRoot<ID> extends Identifier<ID> {

	/**
	 * 创建人.
	 */
	protected ID creator;

	/**
	 * 编辑人.
	 */
	protected ID editor;

	/**
	 * 部门ID.
	 */
	protected ID deptId;

	/**
	 * 部门PATH.
	 */
	protected String deptPath;

	/**
	 * 租户ID.
	 */
	protected ID tenantId;

	/**
	 * 创建时间.
	 */
	protected Instant createDate;

	/**
	 * 修改时间.
	 */
	protected Instant updateDate;

	/**
	 * 数据源名称.
	 */
	protected String sourceName;

	/**
	 * 应用名称.
	 */
	protected String appName;

}
