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

package com.xzixi.framework.webapps.sso.server.service.impl;

import com.xzixi.framework.webapps.sso.server.service.IAppAccessTokenService;
import com.xzixi.framework.webapps.sso.server.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xzixi.framework.webapps.common.constant.TokenConstant.*;

/**
 * @author xuelingkang
 * @date 2020-11-04
 */
@Service
public class AppAccessTokenServiceImpl extends AbstractTokenService implements IAppAccessTokenService {

    @Autowired
    private IRefreshTokenService refreshTokenService;

    @Override
    public String getClaimsKey() {
        return "APP_ACCESS_LOGIN_USER_KEY";
    }

    @Override
    public String createAndSave(int userId, String appUid) {
        String uuid = UUID.randomUUID().toString();
        String jwtToken = getJwtToken(uuid);
        redisTemplate.boundValueOps(getRedisKey(uuid, appUid)).set(userId, APP_ACCESS_TOKEN_FOR_CHECK_EXPIRE_MINUTE, TimeUnit.MINUTES);
        return jwtToken;
    }

    @Override
    public void check(String appAccessToken, String appUid) {
        // TODO
    }

    @Override
    public void mount(String appAccessToken, String appUid, String refreshToken) {
        String refreshTokenUuid = refreshTokenService.decodeJwtToken(refreshToken);
        String appAccessTokenUuid = this.decodeJwtToken(appAccessToken);
        String key = String.format(APP_ACCESS_TOKEN_NODE_KEY_TEMPLATE, refreshTokenUuid, appUid, appAccessTokenUuid);
        Map<String, String> map = new HashMap<>();
        map.put(APP_ACCESS_TOKEN, appAccessToken);
        map.put(APP_UID, appUid);
        redisTemplate.boundValueOps(key).set(map, APP_ACCESS_TOKEN_NODE_EXPIRE_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public void logout(String redisKey) {
        // TODO
    }

    private String getRedisKey(String appUid, String uuid) {
        return String.format(APP_ACCESS_TOKEN_FOR_CHECK_KEY_TEMPLATE, appUid, uuid);
    }
}
