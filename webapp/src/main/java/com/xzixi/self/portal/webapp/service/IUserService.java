package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.webmvc.service.IBaseService;
import com.xzixi.self.portal.framework.webmvc.service.IVoService;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IUserService extends IBaseService<User>, IVoService<User, UserVO, UserVO.BuildOption> {

    /**
     * 检查用户名
     *
     * @param username 用户名
     */
    void checkUsername(String username);

    /**
     * 检查邮箱
     *
     * @param email 邮箱
     */
    void checkEmail(String email);

    /**
     * 保存用户
     *
     * @param user User
     */
    void saveUser(User user);

    /**
     * 发送激活账户邮件
     *
     * @param user 用户
     */
    void sendActivateUserMail(User user);

    /**
     * 根据用户id删除用户，同时删除与角色的关联
     *
     * @param ids 用户id集合
     */
    void removeUsersByIds(Collection<Integer> ids);
}
