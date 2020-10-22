package com.xzixi.framework.webapps.content.service.impl;

import com.xzixi.framework.webapps.content.data.IRoleData;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.common.model.po.Role;
import com.xzixi.framework.webapps.common.model.po.RoleAuthorityLink;
import com.xzixi.framework.webapps.common.model.po.UserRoleLink;
import com.xzixi.framework.webapps.common.model.vo.RoleVO;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.content.service.IAuthorityService;
import com.xzixi.framework.webapps.content.service.IRoleAuthorityLinkService;
import com.xzixi.framework.webapps.content.service.IRoleService;
import com.xzixi.framework.webapps.content.service.IUserRoleLinkService;
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
public class RoleServiceImpl extends BaseServiceImpl<IRoleData, Role> implements IRoleService {

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
    public void removeRolesByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            throw new ServerException(ids, "删除角色失败！");
        }

        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkService.listByRoleIds(ids);
        if (CollectionUtils.isNotEmpty(roleAuthorityLinks)) {
            List<Integer> linkIds = roleAuthorityLinks.stream().map(RoleAuthorityLink::getId).collect(Collectors.toList());
            if (!roleAuthorityLinkService.removeByIds(linkIds)) {
                throw new ServerException(roleAuthorityLinks, "删除角色与权限的关联失败！");
            }
        }

        List<UserRoleLink> userRoleLinks = userRoleLinkService.listByRoleIds(ids);
        if (CollectionUtils.isNotEmpty(userRoleLinks)) {
            List<Integer> linkIds = userRoleLinks.stream().map(UserRoleLink::getId).collect(Collectors.toList());
            if (!userRoleLinkService.removeByIds(linkIds)) {
                throw new ServerException(userRoleLinks, "删除用户与角色的关联失败！");
            }
        }
    }

    @Override
    public RoleVO buildVO(Role role, RoleVO.BuildOption option) {
        RoleVO roleVO = new RoleVO(role);
        if (option.isAuthorities()) {
            // 查询权限
            Collection<Authority> authorities = authorityService.listByRoleId(role.getId());
            if (CollectionUtils.isNotEmpty(authorities)) {
                roleVO.setAuthorities(authorities);
                // 权限标识
                roleVO.setAuthoritySignals(authorities.stream().map(authority -> authorityService.genSignal(authority))
                        .collect(Collectors.toSet()));
            }
        }
        return roleVO;
    }

    @Override
    public List<RoleVO> buildVO(Collection<Role> roles, RoleVO.BuildOption option) {
        return roles.stream().map(role -> buildVO(role, option)).collect(Collectors.toList());
    }
}
