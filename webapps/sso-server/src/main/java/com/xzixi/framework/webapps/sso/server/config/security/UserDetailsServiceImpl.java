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

package com.xzixi.framework.webapps.sso.server.config.security;

import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.exception.RemoteException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.webapps.common.feign.RemoteUserService;
import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.webapps.common.model.vo.UserDetailsImpl;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new ClientException(400, "用户名username不能为空！");
        }

        Result<User> getOneUserResult = remoteUserService.getOne(new QueryParams<>(new User().setUsername(username)));
        if (getOneUserResult.getCode() != 200) {
            throw new RemoteException(getOneUserResult.getCode(), getOneUserResult.getMessage());
        }

        User user = getOneUserResult.getData();
        if (user == null) {
            throw new UsernameNotFoundException("用户名：" + username + "不存在！");
        }

        Result<UserVO> getByIdResult = remoteUserService.getById(user.getId());
        if (getByIdResult.getCode() != 200) {
            throw new RemoteException(getByIdResult.getCode(), getByIdResult.getMessage());
        }

        return new UserDetailsImpl(getByIdResult.getData());
    }
}
