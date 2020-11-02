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

import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.common.model.po.Role;
import com.xzixi.framework.webapps.common.model.vo.AuthorityVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IAuthorityService extends IBaseService<Authority>,
        IVoService<Authority, AuthorityVO, AuthorityVO.BuildOption> {

    /**
     * 根据角色id查询权限
     *
     * @param roleId 角色id
     * @return 权限
     */
    Collection<Authority> listByRoleId(Integer roleId);

    /**
     * 根据角色id查询权限
     *
     * @param roleIds 角色id
     * @return 权限
     */
    Collection<Authority> listByRoleIds(Collection<Integer> roleIds);

    /**
     * 根据角色条件查询权限集合
     *
     * @param queryParams 角色条件
     * @return List&lt;Authority>
     */
    Collection<Authority> listByRoleParams(QueryParams<Role> queryParams);

    /**
     * 根据权限id删除权限，同时删除与角色的关联
     *
     * @param ids 权限id
     */
    void removeAuthoritiesByIds(Collection<Integer> ids);

    /**
     * 生成权限签名
     *
     * @param authority 权限
     * @return 权限签名
     */
    String genSignal(Authority authority);
}
