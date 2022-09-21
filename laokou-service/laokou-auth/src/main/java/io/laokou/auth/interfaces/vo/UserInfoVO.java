/**
 * Copyright 2020-2022 Kou Shenhai
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
 */
package io.laokou.auth.interfaces.vo;

import io.laokou.common.vo.SysRoleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息VO
 * @author Kou Shenhai
 * @version 1.0
 * @date 2021/5/14 0014 下午 8:02
 */
@Data
@Builder
@ApiModel(value = "用户信息VO")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    @ApiModelProperty(name = "userId",value = "用户编号",required = true,example = "1341620898007281665")
    private Long userId;

    @ApiModelProperty(name = "imgUrl",value = "头像地址",required = true,example = "https://1.com//upload/node3/7904fff1c08a4883b40f1ee0336017dc.webp")
    private String imgUrl;

    @ApiModelProperty(name = "username",value = "用户名",required = true,example = "admin")
    private String username;

    @ApiModelProperty(name = "mobile",value = "手机号",example = "18974432576")
    private String mobile;

    @ApiModelProperty(name = "email",value = "电子邮箱",example = "2413176044@qq.com")
    private String email;

    @ApiModelProperty(name = "permissionList",value = "资源标识",required = true,example = "['sys:user:query']")
    private List<String> permissionList;

    @ApiModelProperty(name = "roles",value = "角色集合",required = true,example = "['超级管理员']")
    private List<SysRoleVO> roles;

}
