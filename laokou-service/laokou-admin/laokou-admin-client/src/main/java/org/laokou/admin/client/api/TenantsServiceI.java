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
package org.laokou.admin.client.api;

import org.laokou.admin.client.dto.tenant.clientobject.TenantOptionListQry;
import org.laokou.admin.client.dto.common.clientobject.OptionCO;
import org.laokou.common.i18n.dto.Result;

import java.util.List;

/**
 * @author laokou
 */
public interface TenantsServiceI {

	/**
	 * 租户下拉列表
	 * @return Result<List<OptionCO>>
	 */
	Result<List<OptionCO>> optionList(TenantOptionListQry qry);

	// /**
	// * 分页查询租户
	// * @param qo
	// * @return
	// */
	// IPage<SysTenantVO> queryTenantPage(SysTenantQo qo);
	//
	// /**
	// * 新增租户
	// * @param dto
	// * @return
	// */
	// Boolean insertTenant(SysTenantDTO dto);
	//
	// /**
	// * 查询租户
	// * @param id
	// * @return
	// */
	// SysTenantVO getTenantById(Long id);
	//
	// /**
	// * 修改租户
	// * @param dto
	// * @return
	// */
	// Boolean updateTenant(SysTenantDTO dto);
	//
	// /**
	// * 删除租户
	// * @param id
	// * @return
	// */
	// Boolean deleteTenant(Long id);

}