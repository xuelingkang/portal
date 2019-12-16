package com.xzixi.self.portal.webapp.service.business.impl;

import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.business.IAuthorityBusiness;
import com.xzixi.self.portal.webapp.service.business.IRoleBusiness;
import com.xzixi.self.portal.webapp.service.business.IUserBusiness;
import com.xzixi.self.portal.webapp.service.data.IUserData;
import com.xzixi.self.portal.webapp.service.data.IUserRoleLinkData;
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
public class UserBusinessImpl extends BaseBusinessImpl<User, IUserData> implements IUserBusiness {

    @Autowired
    private IRoleBusiness roleBusiness;
    @Autowired
    private IAuthorityBusiness authorityBusiness;
    @Autowired
    private IUserRoleLinkData userRoleLinkData;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user, Collection<? extends Role> roles) {
        baseData.save(user);
        List<UserRoleLink> userRoleLinks = roles.stream().map(role ->
                new UserRoleLink().setUserId(user.getId()).setRoleId(role.getId()))
                .collect(Collectors.toList());
        userRoleLinkData.saveBatch(userRoleLinks);
    }

    @Override
    public UserVO buildUserVO(User user) {
        UserVO userVO = new UserVO(user);
        // 查询角色
        Collection<Role> roles = roleBusiness.listByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) {
            return userVO;
        }
        Collection<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        // 查询权限
        Collection<Authority> authorities = authorityBusiness.listByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(authorities)) {
            return userVO;
        }
        userVO.setAuthorities(authorities);
        // 权限标识
        userVO.setAuthoritySignals(authorities.stream().map(authority ->
                authority.getProtocol() + "." + authority.getPattern() + "." + authority.getMethod()).collect(Collectors.toSet()));
        return userVO;
    }
}
