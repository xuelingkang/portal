package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.compile.annotation.BaseCache;
import com.xzixi.self.portal.webapp.mapper.UserMapper;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.AuthorityVO;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.service.IRoleService;
import com.xzixi.self.portal.webapp.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
@BaseCache
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAuthorityService authorityService;

    @Override
    public UserVO buildUserVO(User user) {
        UserVO userVO = new UserVO(user);
        // 查询角色
        Collection<Role> roles = roleService.listByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) {
            return userVO;
        }
        Collection<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        // 查询权限
        Collection<Authority> authorities = authorityService.listByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(authorities)) {
            return userVO;
        }
        userVO.setAuthorities(authorities.stream().map(AuthorityVO::new).collect(Collectors.toSet()));
        // 权限标识
        userVO.setAuthoritySignals(authorities.stream().map(authority ->
                authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod()).collect(Collectors.toSet()));
        return userVO;
    }
}
