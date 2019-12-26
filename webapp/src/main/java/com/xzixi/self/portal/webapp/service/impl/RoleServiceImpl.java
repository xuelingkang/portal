package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.webapp.data.IRoleData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.model.vo.RoleVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.service.IRoleAuthorityLinkService;
import com.xzixi.self.portal.webapp.service.IRoleService;
import com.xzixi.self.portal.webapp.service.IUserRoleLinkService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, IRoleData> implements IRoleService {

    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private IUserRoleLinkService userRoleLinkService;
    @Autowired
    private IRoleAuthorityLinkService roleAuthorityLinkService;

    @Override
    public Collection<Role> listByUserId(Integer userId) {
        List<UserRoleLink> userRoleLinks = userRoleLinkService.listByUserId(userId);
        List<Integer> roleIds = userRoleLinks.stream().map(UserRoleLink::getRoleId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }
        return listByIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeRolesByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            return false;
        }

        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkService.listByRoleIds(ids);
        if (CollectionUtils.isNotEmpty(roleAuthorityLinks)) {
            List<Integer> linkIds = roleAuthorityLinks.stream().map(RoleAuthorityLink::getId).collect(Collectors.toList());
            if (!roleAuthorityLinkService.removeByIds(linkIds)) {
                return false;
            }
        }

        List<UserRoleLink> userRoleLinks = userRoleLinkService.listByRoleIds(ids);
        if (CollectionUtils.isNotEmpty(userRoleLinks)) {
            List<Integer> linkIds = userRoleLinks.stream().map(UserRoleLink::getId).collect(Collectors.toList());
            if (!userRoleLinkService.removeByIds(linkIds)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public RoleVO buildRoleVO(Role role) {
        role = getById(role.getId());
        RoleVO roleVO = new RoleVO(role);
        // 查询权限
        Collection<Authority> authorities = authorityService.listByRoleIds(Collections.singletonList(role.getId()));
        if (CollectionUtils.isEmpty(authorities)) {
            return roleVO;
        }
        roleVO.setAuthorities(authorities);
        // 权限标识
        roleVO.setAuthoritySignals(authorities.stream().map(authority ->
                authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod())
                .collect(Collectors.toSet()));
        return roleVO;
    }
}
