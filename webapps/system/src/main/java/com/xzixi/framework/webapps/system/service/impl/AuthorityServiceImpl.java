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

import com.xzixi.framework.boot.core.exception.ServerException;
import com.xzixi.framework.boot.core.model.search.QueryParams;
import com.xzixi.framework.boot.persistent.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.common.model.po.Role;
import com.xzixi.framework.webapps.common.model.po.RoleAuthorityLink;
import com.xzixi.framework.webapps.common.model.vo.AuthorityVO;
import com.xzixi.framework.webapps.system.data.IAuthorityData;
import com.xzixi.framework.webapps.system.service.IAuthorityService;
import com.xzixi.framework.webapps.system.service.IRoleAuthorityLinkService;
import com.xzixi.framework.webapps.system.service.IRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<IAuthorityData, Authority> implements IAuthorityService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleAuthorityLinkService roleAuthorityLinkService;

    @Override
    public Collection<Authority> listByRoleId(Integer roleId) {
        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkService.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(roleAuthorityLinks)) {
            return null;
        }
        List<Integer> authorityIds = roleAuthorityLinks.stream()
                .map(RoleAuthorityLink::getAuthorityId).collect(Collectors.toList());
        return listByIds(authorityIds);
    }

    @Override
    public Collection<Authority> listByRoleIds(Collection<Integer> roleIds) {
        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkService.listByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(roleAuthorityLinks)) {
            return null;
        }
        List<Integer> authorityIds = roleAuthorityLinks.stream()
                .map(RoleAuthorityLink::getAuthorityId).collect(Collectors.toList());
        return listByIds(authorityIds);
    }

    @Override
    public Collection<Authority> listByRoleParams(QueryParams<Role> queryParams) {
        List<Role> roles = roleService.list(queryParams);
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        return listByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAuthoritiesByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            throw new ServerException(ids, "删除权限失败！");
        }

        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkService.listByAuthorityIds(ids);
        if (CollectionUtils.isNotEmpty(roleAuthorityLinks)) {
            List<Integer> linkIds = roleAuthorityLinks.stream().map(RoleAuthorityLink::getId).collect(Collectors.toList());
            if (!roleAuthorityLinkService.removeByIds(linkIds)) {
                throw new ServerException(roleAuthorityLinks, "删除角色和权限的关联失败！");
            }
        }
    }

    @Override
    public AuthorityVO buildVO(Authority authority, AuthorityVO.BuildOption option) {
        AuthorityVO authorityVO = new AuthorityVO(authority);
        authorityVO.setAuthoritySignal(genSignal(authority));
        return authorityVO;
    }

    @Override
    public List<AuthorityVO> buildVO(Collection<Authority> authorities, AuthorityVO.BuildOption option) {
        List<AuthorityVO> authorityVOList = authorities.stream().map(AuthorityVO::new).collect(Collectors.toList());
        authorityVOList.forEach(authorityVO -> authorityVO.setAuthoritySignal(genSignal(authorityVO)));
        return authorityVOList;
    }

    @Override
    public String genSignal(Authority authority) {
        return String.format("%s.%s.%s", authority.getProtocol(), authority.getPattern(), authority.getMethod());
    }
}
