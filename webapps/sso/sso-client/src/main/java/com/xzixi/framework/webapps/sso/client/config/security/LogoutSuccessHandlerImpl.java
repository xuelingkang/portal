/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2021  xuelingkang@163.com.
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

package com.xzixi.framework.webapps.sso.client.config.security;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.sso.common.constant.SsoConstant;
import com.xzixi.framework.webapps.sso.common.constant.TokenConstant;
import com.xzixi.framework.webapps.sso.common.constant.UnAuthSubCode;
import com.xzixi.framework.webapps.sso.common.model.AppAccessTokenValue;
import com.xzixi.framework.webapps.sso.common.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出成功
 *
 * @author xuelingkang
 * @date 2021-01-06
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LogoutService logoutService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 获取请求头或请求参数上的accessToken
        String accessToken = WebUtils.getHeaderOrParameter(request, SsoConstant.ACCESS_TOKEN_HEADER_NAME, SsoConstant.ACCESS_TOKEN_NAME);
        // redis key
        String key = String.format(TokenConstant.APP_ACCESS_TOKEN_KEY_TEMPLATE, accessToken);
        // 查询redis
        AppAccessTokenValue accessTokenValue = (AppAccessTokenValue) redisTemplate.boundValueOps(key).get();
        if (accessTokenValue == null) {
            Result<?> result = new Result<>(HttpStatus.UNAUTHORIZED.value(), UnAuthSubCode.ACCESS_EXPIRE);
            WebUtils.printJson(response, result);
            return;
        }
        // 登出
        logoutService.logout(accessTokenValue.getRefreshToken());
    }
}
