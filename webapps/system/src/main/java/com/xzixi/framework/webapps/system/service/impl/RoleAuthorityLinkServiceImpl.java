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

package com.xzixi.framework.webapps.system.service.impl;

import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.common.model.po.RoleAuthorityLink;
import com.xzixi.framework.webapps.system.data.IRoleAuthorityLinkData;
import com.xzixi.framework.webapps.system.service.IRoleAuthorityLinkService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class RoleAuthorityLinkServiceImpl extends BaseServiceImpl<IRoleAuthorityLinkData, RoleAuthorityLink> implements IRoleAuthorityLinkService {

    @Override
    public List<RoleAuthorityLink> listByRoleId(Integer roleId) {
        return list(new QueryParams<>(new RoleAuthorityLink().setRoleId(roleId)));
    }

    @Override
    public List<RoleAuthorityLink> listByRoleIds(Collection<Integer> roleIds) {
        return list(new QueryParams<RoleAuthorityLink>().in("roleId", roleIds));
    }

    @Override
    public List<RoleAuthorityLink> listByAuthorityId(Integer authorityId) {
        return list(new QueryParams<>(new RoleAuthorityLink().setAuthorityId(authorityId)));
    }

    @Override
    public List<RoleAuthorityLink> listByAuthorityIds(Collection<Integer> authorityIds) {
        return list(new QueryParams<RoleAuthorityLink>().in("authorityId", authorityIds));
    }
}
