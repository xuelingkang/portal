package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.data.IUserData;
import com.xzixi.self.portal.webapp.data.IUserRoleLinkData;
import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.service.IRoleService;
import com.xzixi.self.portal.webapp.service.IUserService;
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
public class UserServiceImpl extends BaseServiceImpl<User, IUserData> implements IUserService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private IUserRoleLinkData userRoleLinkData;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user, Collection<? extends Role> roles) {
        checkSaveUserProps(user);
        baseData.save(user);
        List<UserRoleLink> userRoleLinks = roles.stream().map(role ->
                new UserRoleLink().setUserId(user.getId()).setRoleId(role.getId()))
                .collect(Collectors.toList());
        userRoleLinkData.saveBatch(userRoleLinks);
    }

    @Override
    public UserVO buildUserVO(User user) {
        user = baseData.getById(user.getId());
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
        userVO.setAuthorities(authorities);
        // 权限标识
        userVO.setAuthoritySignals(authorities.stream().map(authority ->
                authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod()).collect(Collectors.toSet()));
        return userVO;
    }

    private void checkSaveUserProps(User user) {
        List<User> usersByUsername = baseData.list(new QueryWrapper<>(new User().setUsername(user.getUsername())));
        if (CollectionUtils.isNotEmpty(usersByUsername)) {
            throw new LogicException(400, "用户名重复！");
        }
        List<User> usersByEmail = baseData.list(new QueryWrapper<>(new User().setEmail(user.getEmail())));
        if (CollectionUtils.isNotEmpty(usersByEmail)) {
            throw new LogicException(400, "邮箱重复！");
        }
    }
}
