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

package com.xzixi.framework.webapps.sso.client.service;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.sso.common.model.AppCheckTokenResponse;
import com.xzixi.framework.webapps.sso.common.model.RefreshAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xuelingkang
 * @date 2020-12-02
 */
@FeignClient(value = "portal-sso-server", contextId = "sso-server")
public interface RemoteSsoService {

    @GetMapping("/token/refresh-app-access-token")
    Result<RefreshAccessTokenResponse> refreshAppAccessToken(@RequestParam("appUid") String appUid,
                                                             @RequestParam("refreshToken") String refreshToken,
                                                             @RequestParam("timestamp") long timestamp,
                                                             @RequestParam("sign") String sign);

    @PostMapping("/token/check-app-access-token")
    Result<AppCheckTokenResponse> checkAppAccessToken(@RequestParam("appUid") String appUid,
                                                      @RequestParam("appAccessToken") String appAccessToken,
                                                      @RequestParam("refreshToken") String refreshToken,
                                                      @RequestParam("timestamp") long timestamp,
                                                      @RequestParam("sign") String sign);

    @GetMapping("/logout")
    void logout(@RequestParam("appUid") String appUid,
                @RequestParam("refreshToken") String refreshToken,
                @RequestParam("timestamp") long timestamp,
                @RequestParam("sign") String sign);
}
