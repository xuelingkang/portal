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

package com.xzixi.framework.webapps.sso.client.controller;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.sso.client.service.RemoteSsoService;
import com.xzixi.framework.webapps.sso.common.model.AppCheckTokenResponse;
import com.xzixi.framework.webapps.sso.common.model.RefreshAccessTokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuelingkang
 * @date 2020-12-01
 */
@RestController
@RequestMapping(value = "/sso", produces = ProjectConstant.RESPONSE_MEDIA_TYPE)
@Api(tags = "sso")
@Validated
public class SsoController {

    @Value("${app-uid}")
    private String appUid;
    @Autowired
    private RemoteSsoService remoteSsoService;

    @GetMapping("/to-login")
    @ApiOperation(value = "重定向到登录页面")
    public void toLogin(
            @ApiParam(value = "返回url") @RequestParam(required = false, defaultValue = "") String returnUrl,
            HttpServletResponse response) throws IOException {
        String loginPageUrl = remoteSsoService.getLoginPageUrl(appUid, returnUrl).getData();
        response.sendRedirect(loginPageUrl);
    }

    @GetMapping("/refresh-access-token")
    @ApiOperation(value = "刷新access-token")
    public Result<RefreshAccessTokenResponse> refreshAccessToken(String refreshToken) {
        // TODO
        return null;
    }

    @PostMapping("/login/callback")
    @ApiOperation(value = "验证token")
    public Result<AppCheckTokenResponse> checkToken(String appAccessToken, String refreshToken) {
        // TODO
        return null;
    }

    @GetMapping("/logout/callback")
    @ApiOperation(value = "单点登出回调")
    public Result<?> logoutCallback(String accessToken, String appUid, long timestamp, String sign) {
        // TODO
        return null;
    }
}
