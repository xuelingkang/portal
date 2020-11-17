/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.system.service;

import com.xzixi.framework.boot.persistent.service.IBaseService;
import com.xzixi.framework.webapps.common.model.po.RoleAuthorityLink;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IRoleAuthorityLinkService extends IBaseService<RoleAuthorityLink> {

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByRoleId(Integer roleId);

    /**
     * 根据角色id集合查询
     *
     * @param roleIds 角色id集合
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByRoleIds(Collection<Integer> roleIds);

    /**
     * 根据权限id查询
     *
     * @param authorityId 权限id
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByAuthorityId(Integer authorityId);

    /**
     * 根据权限id集合查询
     *
     * @param authorityIds 权限id集合
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByAuthorityIds(Collection<Integer> authorityIds);
}
