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
package io.laokou.auth.domain.sys.repository.mapper;
import io.laokou.auth.interfaces.vo.SysMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * 菜单类
 * @author  Kou Shenhai
 */
@Mapper
@Repository
public interface SysMenuMapper {

    /**
     * 查询所有权限列表
     * @return
     */
    List<String> getPermissionsList();

    /**
     * 查询用户权限列表
     * @param userId
     * @return
     */
    List<String> getPermissionsListByUserId(@Param("userId") Long userId);

    /**
     * 获取所有的资源列表
     * @return
     */
    List<SysMenuVO> getMenuList(@Param("type")Integer type);

    /**
     * 通过userId查询资源权限
     * @param userId
     * @return
     */
    List<SysMenuVO> getMenuListByUserId(@Param("userId") Long userId, @Param("type")Integer type);

}
