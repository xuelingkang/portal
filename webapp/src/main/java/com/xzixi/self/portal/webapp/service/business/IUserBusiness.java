package com.xzixi.self.portal.webapp.service.business;

import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.data.IUserData;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IUserBusiness extends IBaseBusiness<User, IUserData> {

    /**
     * 保存用户
     *
     * @param user 用户
     * @param roles 权限
     */
    void save(User user, Collection<? extends Role> roles);

    /**
     * 构建UserVO对象
     *
     * @param user 用户
     * @return UserVO
     */
    UserVO buildUserVO(User user);
}