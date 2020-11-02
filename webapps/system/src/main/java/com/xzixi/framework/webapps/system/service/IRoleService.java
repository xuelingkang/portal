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
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.Role;
import com.xzixi.framework.webapps.common.model.vo.RoleVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IRoleService extends IBaseService<Role>, IVoService<Role, RoleVO, RoleVO.BuildOption> {

    /**
     * 根据用户id查询角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    Collection<Role> listByUserId(Integer userId);

    /**
     * 根据角色id删除角色，同时删除与用户和权限的关联
     *
     * @param ids 角色id
     */
    void removeRolesByIds(Collection<Integer> ids);
}
