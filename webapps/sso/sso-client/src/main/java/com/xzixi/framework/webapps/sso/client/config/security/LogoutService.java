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

import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.remote.service.RemoteAppService;
import com.xzixi.framework.webapps.sso.client.service.RemoteSsoService;
import com.xzixi.framework.webapps.sso.common.constant.SsoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 单点登出
 *
 * @author xuelingkang
 * @date 2021-01-08
 */
@Service
public class LogoutService {

    @Value("${app-uid}")
    private String appUid;
    @Autowired
    private RemoteAppService remoteAppService;
    @Autowired
    private RemoteSsoService remoteSsoService;
    @Autowired
    private ISignService signService;

    public void logout(String refreshToken) {
        App app = remoteAppService.getByUid(appUid).getData();
        long timestamp = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        params.put(ProjectConstant.APP_UID_NAME, appUid);
        params.put(SsoConstant.REFRESH_TOKEN_NAME, refreshToken);
        params.put(ProjectConstant.TIMESTAMP_NAME, timestamp);
        String sign = signService.genSign(params, app.getSecret());
        remoteSsoService.logout(appUid, refreshToken, timestamp, sign);
    }
}
