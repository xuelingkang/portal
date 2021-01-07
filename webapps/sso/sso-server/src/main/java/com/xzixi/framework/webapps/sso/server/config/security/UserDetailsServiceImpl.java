/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.sso.server.config.security;

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.webapps.common.model.params.UserSearchParams;
import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import com.xzixi.framework.webapps.remote.service.RemoteUserService;
import com.xzixi.framework.webapps.sso.common.model.UserDetailsImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "用户名username不能为空！");
        }

        UserSearchParams userSearchParams = new UserSearchParams();
        userSearchParams.setModel(new User().setUsername(username));
        User user = remoteUserService.getOne(userSearchParams).getData();

        if (user == null) {
            throw new UsernameNotFoundException("用户名：" + username + "不存在！");
        }

        return new UserDetailsImpl(new UserVO(user));
    }
}
