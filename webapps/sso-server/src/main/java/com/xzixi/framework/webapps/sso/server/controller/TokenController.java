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

package com.xzixi.framework.webapps.sso.server.controller;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.sso.common.model.AppCheckTokenResponse;
import com.xzixi.framework.webapps.sso.common.model.LoginSuccessResponse;
import com.xzixi.framework.webapps.sso.common.model.RefreshAccessTokenResponse;
import com.xzixi.framework.webapps.sso.server.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@RestController
@RequestMapping(value = "/token", produces = ProjectConstant.RESPONSE_MEDIA_TYPE)
@Api(tags = "token")
@Validated
public class TokenController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login-again")
    @ApiOperation(value = "使用已有的ssoAccessToken再次登录")
    public Result<LoginSuccessResponse> loginAgain(
            @ApiParam(value = "ssoAccessToken", required = true) @NotBlank(message = "ssoAccessToken不能为空！") String ssoAccessToken,
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") String appUid) {
        // TODO
        return null;
    }

    @GetMapping("/refresh-sso-access-token")
    @ApiOperation(value = "刷新ssoAccessToken")
    public Result<RefreshAccessTokenResponse> refreshSsoAccessToken(
            @ApiParam(value = "refreshToken", required = true) @NotBlank(message = "refreshToken不能为空！") String refreshToken) {
        // TODO
        return null;
    }

    @GetMapping("/refresh-app-access-token")
    @ApiOperation(value = "刷新appAccessToken")
    public Result<RefreshAccessTokenResponse> refreshAppAccessToken(
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") String appUid,
            @ApiParam(value = "refreshToken", required = true) @NotBlank(message = "refreshToken不能为空！") String refreshToken,
            @ApiParam(value = "调用时间戳", required = true) long timestamp,
            @ApiParam(value = "签名", required = true) @NotBlank(message = "sign不能为空") String sign) {
        // TODO
        return null;
    }

    @PostMapping("/check-app-access-token")
    @ApiOperation(value = "验证token")
    public Result<AppCheckTokenResponse> checkAppAccessToken(
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") String appUid,
            @ApiParam(value = "appAccessToken", required = true) @NotBlank(message = "appAccessToken不能为空！") String appAccessToken,
            @ApiParam(value = "refreshToken", required = true) @NotBlank(message = "refreshToken不能为空！") String refreshToken,
            @ApiParam(value = "调用时间戳", required = true) long timestamp,
            @ApiParam(value = "签名", required = true) @NotBlank(message = "sign不能为空") String sign) {
        // TODO
        return null;
    }
}
