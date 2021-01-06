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
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import com.xzixi.framework.webapps.remote.service.RemoteUserService;
import com.xzixi.framework.webapps.sso.common.constant.SsoConstant;
import com.xzixi.framework.webapps.sso.common.constant.TokenConstant;
import com.xzixi.framework.webapps.sso.common.model.AppAccessTokenValue;
import com.xzixi.framework.webapps.sso.common.model.UserDetailsImpl;
import com.xzixi.framework.webapps.sso.common.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuelingkang
 * @date 2021-01-05
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头或请求参数上的accessToken
        String accessToken = WebUtils.getHeaderOrParameter(request, SsoConstant.ACCESS_TOKEN_HEADER_NAME, SsoConstant.ACCESS_TOKEN_NAME);
        // redis key
        String key = String.format(TokenConstant.APP_ACCESS_TOKEN_KEY_TEMPLATE, accessToken);
        // 查询redis
        AppAccessTokenValue accessTokenValue = (AppAccessTokenValue) redisTemplate.boundValueOps(key).get();
        // 设置当前登录人信息
        if (accessTokenValue != null) {
            UserVO userVO = remoteUserService.getById(accessTokenValue.getUserId()).getData();
            if (userVO != null) {
                UserDetails userDetails = new UserDetailsImpl(userVO);
                if (!userDetails.isEnabled()) {
                    Result<?> result = new Result<>(HttpStatus.UNAUTHORIZED.value(), 0, "账户未激活！");
                    WebUtils.printJson(response, result);
                    return;
                }
                if (!userDetails.isAccountNonLocked()) {
                    Result<?> result = new Result<>(HttpStatus.UNAUTHORIZED.value(), 0, "账户已被锁定！");
                    WebUtils.printJson(response, result);
                    return;
                }
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
