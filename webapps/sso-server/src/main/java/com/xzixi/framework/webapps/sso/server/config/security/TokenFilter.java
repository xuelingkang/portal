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

import com.xzixi.framework.boot.webmvc.exception.RemoteException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.webapps.common.constant.SecurityConstant;
import com.xzixi.framework.webapps.common.feign.RemoteUserService;
import com.xzixi.framework.webapps.common.model.po.Token;
import com.xzixi.framework.webapps.common.model.vo.UserDetailsImpl;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import com.xzixi.framework.webapps.sso.server.service.ITokenService;
import com.xzixi.framework.webapps.sso.server.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 薛凌康
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private ITokenService tokenService;
    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String signature = WebUtils.getHeaderOrParameter(request, SecurityConstant.AUTHENTICATION_HEADER_NAME, SecurityConstant.AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotEmpty(signature) && !Objects.equals(SecurityConstant.NULL_TOKEN, signature)) {
            try {
                Token token = tokenService.getToken(signature);
                if (token != null) {
                    setAuthentication(token, response);
                }
            } catch (Exception e) {
                Result<?> result = new Result<>(401, "非法认证！", null);
                WebUtils.printJson(response, result);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 将用户信息保存到security上下文
     *
     * @param token token对象
     */
    private void setAuthentication(Token token, HttpServletResponse response) {
        Result<UserVO> getByIdResult = remoteUserService.getById(token.getUserId());
        if (getByIdResult.getCode() != 200) {
            throw new RemoteException(getByIdResult.getCode(), getByIdResult.getMessage());
        }

        UserVO userVO = getByIdResult.getData();
        if (userVO == null) {
            Result<?> result = new Result<>(401, "账户不存在或已被删除！", null);
            WebUtils.printJson(response, result);
            return;
        }

        // 检查是否激活
        if (!userVO.getActivated()) {
            Result<?> result = new Result<>(401, "账户未激活！", null);
            WebUtils.printJson(response, result);
            return;
        }

        // 检查是否锁定
        if (userVO.getLocked()) {
            Result<?> result = new Result<>(401, "账户已被锁定！", null);
            WebUtils.printJson(response, result);
            return;
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(userVO);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
