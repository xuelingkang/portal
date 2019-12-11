package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.mapper.RoleMapper;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.model.vo.AuthorityVO;
import com.xzixi.self.portal.webapp.model.vo.RoleVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.service.IRoleService;
import com.xzixi.self.portal.webapp.service.IUserRoleLinkService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserRoleLinkService userRoleLinkService;
    @Autowired
    private IAuthorityService authorityService;

    @Override
    public Collection<Role> listByUserId(Integer userId) {
        List<UserRoleLink> userRoleLinks = userRoleLinkService.list(new QueryWrapper<>(new UserRoleLink().setUserId(userId)));
        List<Integer> roleIds = userRoleLinks.stream().map(UserRoleLink::getRoleId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }
        return listByIds(roleIds);
    }

    @Override
    public RoleVO buildGuest() {
        Role guest = getOne(new QueryWrapper<>(new Role().setName(GUEST_ROLE_NAME)));
        return buildRoleVO(guest);
    }

    @Override
    public RoleVO buildRoleVO(Role role) {
        RoleVO roleVO = new RoleVO(role);
        // 查询权限
        Collection<Authority> authorities = authorityService.listByRoleIds(Collections.singletonList(role.getId()));
        if (CollectionUtils.isEmpty(authorities)) {
            return roleVO;
        }
        roleVO.setAuthorities(authorities.stream().map(AuthorityVO::new).collect(Collectors.toSet()));
        // 权限标识
        roleVO.setAuthoritySignals(authorities.stream().map(authority ->
                authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod())
                .collect(Collectors.toSet()));
        return roleVO;
    }
}
