package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;

/**
 * @author 薛凌康
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 保存用户
     *
     * @param user User
     * @return {@code true} 保存成功 {@code false} 保存失败
     */
    boolean saveUser(User user);

    /**
     * 构建UserVO对象
     *
     * @param user 用户
     * @return UserVO
     */
    UserVO buildUserVO(User user);
}