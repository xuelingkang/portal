package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.data.IAuthorityData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;
import com.xzixi.self.portal.webapp.model.vo.AuthorityVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.service.IRoleAuthorityLinkService;
import com.xzixi.self.portal.webapp.service.IRoleService;
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
    public Collection<Authority> listByRoleWrapper(QueryWrapper<Role> queryWrapper) {
        List<Role> roles = roleService.list(queryWrapper);
        List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        return listByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAuthoritiesByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            return false;
        }

        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkService.listByAuthorityIds(ids);
        if (CollectionUtils.isNotEmpty(roleAuthorityLinks)) {
            List<Integer> linkIds = roleAuthorityLinks.stream().map(RoleAuthorityLink::getId).collect(Collectors.toList());
            return roleAuthorityLinkService.removeByIds(linkIds);
        }

        return true;
    }

    @Override
    public AuthorityVO buildAuthorityVO(Integer id) {
        Authority authority = getById(id);
        return buildAuthorityVO(authority);
    }

    @Override
    public AuthorityVO buildAuthorityVO(Authority authority) {
        AuthorityVO authorityVO = new AuthorityVO(authority);
        authorityVO.setAuthoritySignal(authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod());
        return authorityVO;
    }
}
