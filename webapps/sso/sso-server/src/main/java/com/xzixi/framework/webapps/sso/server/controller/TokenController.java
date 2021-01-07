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

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.remote.service.RemoteAppService;
import com.xzixi.framework.webapps.sso.common.model.AppCheckTokenResponse;
import com.xzixi.framework.webapps.sso.common.model.LoginSuccessResponse;
import com.xzixi.framework.webapps.sso.common.model.RefreshAccessTokenResponse;
import com.xzixi.framework.webapps.sso.server.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@RestController
@RequestMapping(value = "/token", produces = ProjectConstant.RESPONSE_MEDIA_TYPE)
@Api(tags = "token")
@Validated
public class TokenController {

    @Value("${sso.login-page-template}")
    private String loginPageTemplate;
    @Autowired
    private IAuthService authService;
    @Autowired
    private RemoteAppService remoteAppService;
    @Autowired
    private ISignService signService;

    @GetMapping("/get-login-page-url")
    @ApiOperation(value = "获取登录页面地址")
    public Result<String> getLoginPageUrl(
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") @RequestParam String appUid,
            @ApiParam(value = "返回url") @RequestParam(required = false, defaultValue = "") String returnUrl) {
        String loginPageUrl = String.format(loginPageTemplate, appUid, returnUrl);
        return new Result<>(loginPageUrl);
    }

    @PostMapping("/login-again")
    @ApiOperation(value = "使用已有的ssoAccessToken再次登录")
    public Result<LoginSuccessResponse> loginAgain(
            @ApiParam(value = "ssoAccessToken", required = true) @NotBlank(message = "ssoAccessToken不能为空！") @RequestParam String ssoAccessToken,
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") @RequestParam String appUid,
            @ApiParam(value = "返回url") @RequestParam(required = false) String returnUrl) {
        LoginSuccessResponse response = authService.login(ssoAccessToken, appUid, returnUrl);
        return new Result<>(response);
    }

    @GetMapping("/refresh-sso-access-token")
    @ApiOperation(value = "刷新ssoAccessToken")
    public Result<RefreshAccessTokenResponse> refreshSsoAccessToken(
            @ApiParam(value = "refreshToken", required = true) @NotBlank(message = "refreshToken不能为空！") @RequestParam String refreshToken) {
        RefreshAccessTokenResponse response = authService.refreshSsoAccessToken(refreshToken);
        return new Result<>(response);
    }

    @GetMapping("/refresh-app-access-token")
    @ApiOperation(value = "刷新appAccessToken")
    public Result<RefreshAccessTokenResponse> refreshAppAccessToken(
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") @RequestParam String appUid,
            @ApiParam(value = "refreshToken", required = true) @NotBlank(message = "refreshToken不能为空！") @RequestParam String refreshToken,
            @ApiParam(value = "调用时间戳", required = true) @RequestParam long timestamp,
            @ApiParam(value = "签名", required = true) @NotBlank(message = "sign不能为空") @RequestParam String sign) {
        // 查询app
        App app = remoteAppService.getByUid(appUid).getData();
        // 验签
        Map<String, Object> params = new HashMap<>();
        params.put("appUid", appUid);
        params.put("refreshToken", refreshToken);
        params.put("timestamp", timestamp);
        boolean signValid = signService.check(params, sign, app.getSecret());
        if (!signValid) {
            throw new ClientException(400, "签名错误");
        }
        // 刷新accessToken
        RefreshAccessTokenResponse response = authService.refreshAppAccessToken(refreshToken, appUid);
        return new Result<>(response);
    }

    @PostMapping("/check-app-access-token")
    @ApiOperation(value = "验证token")
    public Result<AppCheckTokenResponse> checkAppAccessToken(
            @ApiParam(value = "应用uid", required = true) @NotBlank(message = "appUid不能为空！") @RequestParam String appUid,
            @ApiParam(value = "appAccessToken", required = true) @NotBlank(message = "appAccessToken不能为空！") @RequestParam String appAccessToken,
            @ApiParam(value = "refreshToken", required = true) @NotBlank(message = "refreshToken不能为空！") @RequestParam String refreshToken,
            @ApiParam(value = "调用时间戳", required = true) @RequestParam long timestamp,
            @ApiParam(value = "签名", required = true) @NotBlank(message = "sign不能为空") @RequestParam String sign) {
        // 查询app
        App app = remoteAppService.getByUid(appUid).getData();
        // 验签
        Map<String, Object> params = new HashMap<>();
        params.put("appUid", appUid);
        params.put("appAccessToken", appAccessToken);
        params.put("refreshToken", refreshToken);
        params.put("timestamp", timestamp);
        boolean signValid = signService.check(params, sign, app.getSecret());
        if (!signValid) {
            throw new ClientException(400, "签名错误");
        }
        // 验证refreshToken和accessToken
        AppCheckTokenResponse response = authService.check(refreshToken, appAccessToken, appUid);
        return new Result<>(response);
    }
}
