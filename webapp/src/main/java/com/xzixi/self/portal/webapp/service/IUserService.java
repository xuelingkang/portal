package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;

import java.util.Collection;

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
     * 根据用户id删除用户，同时删除与角色的关联
     *
     * @param ids 用户id集合
     * @return {@code true} 删除成功 {@code false} 删除失败
     */
    boolean removeUsersByIds(Collection<Integer> ids);

    /**
     * 构建UserVO对象
     *
     * @param user 用户
     * @return UserVO
     */
    UserVO buildUserVO(User user);
}
