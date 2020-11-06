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

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.common.feign.RemoteUserService;
import com.xzixi.framework.webapps.common.model.po.Token;
import com.xzixi.framework.webapps.common.model.vo.sso.SsoServerLoginResponse;
import com.xzixi.framework.webapps.common.model.vo.TokenVO;
import com.xzixi.framework.webapps.common.model.vo.UserDetailsImpl;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import com.xzixi.framework.webapps.sso.server.service.ITokenService2;
import com.xzixi.framework.webapps.sso.server.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证成功
 * TODO 认证成功回调应用
 *
 * @author 薛凌康
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private ITokenService2 tokenService;
    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserVO user = userDetails.getUser();

        // TODO 响应
        long now = System.currentTimeMillis();
        SsoServerLoginResponse ssoServerLoginResponse = new SsoServerLoginResponse();
        ssoServerLoginResponse.setLoginTime(now);

        // TODO 重构
        // 保存token
        Token token = tokenService.saveToken(user.getId());

        // 返回token
        user.setPassword(null);
        user.setLoginTime(token.getLoginTime());
        TokenVO tokenVO = new TokenVO(token).setUser(user);
        Result<TokenVO> result = new Result<>(200, "登录成功！", tokenVO);
        WebUtils.printJson(response, result);

        // 设置登录时间
        remoteUserService.updateLoginTime(user.getId(), user.getLoginTime());
    }
}
