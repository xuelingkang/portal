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

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.remote.service.RemoteAppService;
import com.xzixi.framework.webapps.sso.client.service.RemoteSsoService;
import com.xzixi.framework.webapps.sso.common.constant.TokenConstant;
import com.xzixi.framework.webapps.sso.common.model.AppAccessTokenValue;
import com.xzixi.framework.webapps.sso.common.model.AppCheckTokenResponse;
import com.xzixi.framework.webapps.sso.common.model.RefreshAccessTokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RemoteAppService remoteAppService;
    @Autowired
    private ISignService signService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
        App app = remoteAppService.getByUid(appUid).getData();
        long timestamp = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        params.put("appUid", appUid);
        params.put("refreshToken", refreshToken);
        params.put("timestamp", timestamp);
        String sign = signService.genSign(params, app.getSecret());
        Result<RefreshAccessTokenResponse> result = remoteSsoService.refreshAppAccessToken(appUid, refreshToken, timestamp, sign);
        if (result.getCode() == 200) {
            // 刷新成功后，在redis设置新的登录信息
            AppAccessTokenValue tokenValue = new AppAccessTokenValue(result.getData().getUserId(), refreshToken);
            String key = String.format(TokenConstant.APP_ACCESS_TOKEN_KEY_TEMPLATE, result.getData().getAccessToken());
            redisTemplate.boundValueOps(key).set(tokenValue, TokenConstant.APP_ACCESS_TOKEN_EXPIRE_MINUTE, TimeUnit.MINUTES);
        }
        return result;
    }

    @PostMapping("/login/callback")
    @ApiOperation(value = "验证token")
    public Result<AppCheckTokenResponse> checkToken(String appAccessToken, String refreshToken) {
        App app = remoteAppService.getByUid(appUid).getData();
        long timestamp = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        params.put("appUid", appUid);
        params.put("appAccessToken", appAccessToken);
        params.put("refreshToken", refreshToken);
        params.put("timestamp", timestamp);
        String sign = signService.genSign(params, app.getSecret());
        Result<AppCheckTokenResponse> result = remoteSsoService.checkAppAccessToken(appUid, appAccessToken, refreshToken, timestamp, sign);
        if (result.getCode() == 200) {
            // 如果验证通过，在redis设置登录信息
            AppAccessTokenValue tokenValue = new AppAccessTokenValue(result.getData().getUserId(), refreshToken);
            String key = String.format(TokenConstant.APP_ACCESS_TOKEN_KEY_TEMPLATE, appAccessToken);
            redisTemplate.boundValueOps(key).set(tokenValue, TokenConstant.APP_ACCESS_TOKEN_EXPIRE_MINUTE, TimeUnit.MINUTES);
        }
        return result;
    }

    @GetMapping("/logout/callback")
    @ApiOperation(value = "单点登出回调")
    public Result<?> logoutCallback(String accessToken, String appUid, long timestamp, String sign) {
        App app = remoteAppService.getByUid(appUid).getData();
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", accessToken);
        params.put("appUid", appUid);
        params.put("timestamp", timestamp);
        boolean signValid = signService.check(params, sign, app.getSecret());
        if (!signValid) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "签名错误");
        }
        // 移除redis中的登录信息
        String key = String.format(TokenConstant.APP_ACCESS_TOKEN_KEY_TEMPLATE, accessToken);
        redisTemplate.boundValueOps(key).expire(0, TimeUnit.SECONDS);
        return new Result<>();
    }
}
