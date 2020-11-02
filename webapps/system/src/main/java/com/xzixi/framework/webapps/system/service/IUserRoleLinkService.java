/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.system.service;

import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.webapps.common.model.po.UserRoleLink;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IUserRoleLinkService extends IBaseService<UserRoleLink> {

    /**
     * 根据用户id查询
     *
     * @param userId 用户id
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByUserId(Integer userId);

    /**
     * 根据用户id集合查询
     *
     * @param userIds 用户id集合
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByUserIds(Collection<Integer> userIds);

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByRoleId(Integer roleId);

    /**
     * 根据角色id集合查询
     *
     * @param roleIds 角色id集合
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByRoleIds(Collection<Integer> roleIds);
}
