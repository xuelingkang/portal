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

import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.webapps.common.constant.SecurityConstant;
import com.xzixi.framework.webapps.sso.server.service.ITokenService;
import com.xzixi.framework.webapps.sso.server.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 退出登录
 *
 * @author 薛凌康
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private ITokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String signature = WebUtils.getHeaderOrParameter(request, SecurityConstant.AUTHENTICATION_HEADER_NAME, SecurityConstant.AUTHENTICATION_PARAMETER_NAME);
        if (StringUtils.isNotBlank(signature) && !Objects.equals(SecurityConstant.NULL_TOKEN, signature)) {
            try {
                tokenService.deleteToken(signature);
            } catch (Exception e) {
                Result<?> result = new Result<>(401, "非法认证！", null);
                WebUtils.printJson(response, result);
                return;
            }
        }
        Result<?> result = new Result<>(200, "退出成功！", null);
        WebUtils.printJson(response, result);
    }
}
