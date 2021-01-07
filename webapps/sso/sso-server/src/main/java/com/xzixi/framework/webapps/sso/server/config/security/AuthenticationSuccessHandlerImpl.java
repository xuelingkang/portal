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

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.sso.common.constant.SsoConstant;
import com.xzixi.framework.webapps.sso.common.model.LoginSuccessResponse;
import com.xzixi.framework.webapps.sso.common.model.UserDetailsImpl;
import com.xzixi.framework.webapps.sso.common.util.WebUtils;
import com.xzixi.framework.webapps.sso.server.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证成功
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private IAuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 用户id
        int userId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
        // 应用uid
        String appUid = WebUtils.getParameter(request, ProjectConstant.APP_UID_NAME);
        // 返回url
        String returnUrl = WebUtils.getParameter(request, SsoConstant.RETURN_URL_NAME);

        // 设置登录信息
        LoginSuccessResponse loginSuccessResponse = authService.login(userId, appUid, returnUrl);

        // 响应
        Result<LoginSuccessResponse> result = new Result<>(HttpStatus.OK.value(), "登录成功！", loginSuccessResponse);
        WebUtils.printJson(response, result);
    }
}
