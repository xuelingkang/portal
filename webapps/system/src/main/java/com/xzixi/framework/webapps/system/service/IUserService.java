/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.system.service;

import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.webapps.common.model.vo.UserVO;

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
