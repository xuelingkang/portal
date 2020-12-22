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
import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.remote.service.RemoteAppService;
import com.xzixi.framework.webapps.sso.server.constant.SsoServerConstant;
import com.xzixi.framework.webapps.sso.server.service.IAuthService;
import com.xzixi.framework.webapps.sso.server.util.WebUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 退出登录
 *
 * @author 薛凌康
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private RemoteAppService remoteAppService;
    @Autowired
    private IAuthService authService;
    @Autowired
    private ISignService signService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String appUid = WebUtils.getParameter(request, ProjectConstant.APP_UID_NAME);
        String refreshToken = WebUtils.getParameter(request, SsoServerConstant.REFRESH_TOKEN_NAME);
        String timestamp = WebUtils.getParameter(request, ProjectConstant.TIMESTAMP_NAME);
        String sign = WebUtils.getParameter(request, ProjectConstant.SIGN_NAME);

        // 验证参数
        Assert.hasText(appUid, "appUid不能为空！");
        Assert.hasText(refreshToken, "refreshToken不能为空！");
        Assert.isTrue(NumberUtils.isNumber(timestamp), "timestamp必须是数字！");
        Assert.hasText(sign, "sign不能为空！");

        // 查询应用
        App app = remoteAppService.getByUid(appUid).getData();
        Assert.notNull(app, "app不存在！");
        String secret = app.getSecret();

        Map<String, Object> params = new HashMap<>();
        params.put(ProjectConstant.APP_UID_NAME, appUid);
        params.put(SsoServerConstant.REFRESH_TOKEN_NAME, refreshToken);
        params.put(ProjectConstant.TIMESTAMP_NAME, Long.parseLong(timestamp));

        // 验证签名
        if (!signService.check(params, sign, secret)) {
            Result<?> result = new Result<>(403, "没有权限！", null);
            WebUtils.printJson(response, result);
            return;
        }

        // 执行单点登出
        authService.logout(refreshToken);

        Result<?> result = new Result<>(200, "退出成功！", null);
        WebUtils.printJson(response, result);
    }
}
