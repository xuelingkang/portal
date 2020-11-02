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

import com.xzixi.framework.webapps.sso.server.util.WebUtils;
import com.xzixi.framework.boot.webmvc.model.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String message;
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用！";
        } else {
            message = "登录失败！";
        }
        Result<?> result = new Result<>(401, message, null);
        WebUtils.printJson(response, result);
    }
}
