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

package com.xzixi.framework.webapps.system.service.impl;

import com.xzixi.framework.boot.core.model.search.QueryParams;
import com.xzixi.framework.boot.persistent.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.common.model.po.UserRoleLink;
import com.xzixi.framework.webapps.system.data.IUserRoleLinkData;
import com.xzixi.framework.webapps.system.service.IUserRoleLinkService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class UserRoleLinkServiceImpl extends BaseServiceImpl<IUserRoleLinkData, UserRoleLink> implements IUserRoleLinkService {

    @Override
    public List<UserRoleLink> listByUserId(Integer userId) {
        return list(new QueryParams<>(new UserRoleLink().setUserId(userId)));
    }

    @Override
    public List<UserRoleLink> listByUserIds(Collection<Integer> userIds) {
        return list(new QueryParams<UserRoleLink>().in("userId", userIds));
    }

    @Override
    public List<UserRoleLink> listByRoleId(Integer roleId) {
        return list(new QueryParams<>(new UserRoleLink().setRoleId(roleId)));
    }

    @Override
    public List<UserRoleLink> listByRoleIds(Collection<Integer> roleIds) {
        return list(new QueryParams<UserRoleLink>().in("roleId", roleIds));
    }
}
