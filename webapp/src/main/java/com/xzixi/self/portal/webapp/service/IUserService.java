package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;

/**
 * @author 薛凌康
 */
public interface IUserService extends IBaseService<User> {

    String BASE_CACHE_NAME = "user:base";
    String CASUAL_CACHE_NAME = "user:casual";

    /**
     * 构建UserVO对象
     *
     * @param user 用户
     * @return UserVO
     */
    UserVO buildUserVO(User user);
}
