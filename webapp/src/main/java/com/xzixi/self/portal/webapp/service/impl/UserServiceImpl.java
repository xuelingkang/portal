package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.data.IUserData;
import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.framework.exception.ServerException;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.service.IRoleService;
import com.xzixi.self.portal.webapp.service.IUserRoleLinkService;
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
public class UserServiceImpl extends BaseServiceImpl<IUserData, User> implements IUserService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private IUserRoleLinkService userRoleLinkService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        // 检查属性
        checkSaveUserProps(user);
        // 保存用户
        if (!save(user)) {
            throw new ServerException(user, "保存用户失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsersByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            throw new ServerException(ids, "删除用户失败！");
        }

        List<UserRoleLink> userRoleLinks = userRoleLinkService.listByUserIds(ids);
        if (CollectionUtils.isNotEmpty(userRoleLinks)) {
            List<Integer> linkIds = userRoleLinks.stream().map(UserRoleLink::getId).collect(Collectors.toList());
            if (!userRoleLinkService.removeByIds(linkIds)) {
                throw new ServerException(userRoleLinks, "删除用户与角色的关联失败！");
            }
        }
    }

    @Override
    public UserVO buildUserVO(Integer id) {
        User user = getById(id);
        return buildUserVO(user);
    }

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
        userVO.setAuthorities(authorities);
        // 权限标识
        userVO.setAuthoritySignals(authorities.stream().map(authority ->
                authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod()).collect(Collectors.toSet()));
        return userVO;
    }


    private void checkSaveUserProps(User user) {
        List<User> usersByUsername = list(new QueryWrapper<>(new User().setUsername(user.getUsername())));
        if (CollectionUtils.isNotEmpty(usersByUsername)) {
            throw new LogicException(400, "用户名重复！");
        }
        List<User> usersByEmail = list(new QueryWrapper<>(new User().setEmail(user.getEmail())));
        if (CollectionUtils.isNotEmpty(usersByEmail)) {
            throw new LogicException(400, "邮箱重复！");
        }
    }
}
