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

package com.xzixi.framework.webapps.sso.server.service.impl;

import com.xzixi.framework.webapps.sso.server.model.AppAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.AppAccessTokenMountValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;
import com.xzixi.framework.webapps.sso.server.service.IAppAccessTokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xzixi.framework.webapps.common.constant.TokenConstant.*;

/**
 * @author xuelingkang
 * @date 2020-11-04
 */
@Service
public class AppAccessTokenServiceImpl extends AbstractTokenService implements IAppAccessTokenService {

    @Override
    public String getClaimsKey() {
        return "APP_ACCESS_LOGIN_USER_KEY";
    }

    @Override
    public TokenInfo createAndSave(int userId, String appUid, String refreshToken) {
        String uuid = UUID.randomUUID().toString();
        String jwtToken = getJwtToken(uuid);
        AppAccessTokenValue appAccessTokenValue = new AppAccessTokenValue(userId, refreshToken);
        redisTemplate.boundValueOps(getRedisKey(uuid, appUid)).set(appAccessTokenValue, APP_ACCESS_TOKEN_FOR_CHECK_EXPIRE_MINUTE, TimeUnit.MINUTES);
        return new TokenInfo(uuid, jwtToken);
    }

    @Override
    public AppAccessTokenValue getTokenValue(String appAccessTokenUUid, String appUid) {
        return (AppAccessTokenValue) redisTemplate.boundValueOps(getRedisKey(appAccessTokenUUid, appUid)).get();
    }

    @Override
    public void delete(String appAccessTokenUUid, String appUid) {
        redisTemplate.opsForValue().getOperations().delete(getRedisKey(appAccessTokenUUid, appUid));
    }

    @Override
    public void mount(String appAccessTokenUuid, String appAccessToken, String appUid, String refreshTokenUuid) {
        String key = getMountKey(appAccessTokenUuid, appUid, refreshTokenUuid);
        AppAccessTokenMountValue appAccessTokenMountValue = new AppAccessTokenMountValue(appUid, appAccessToken);
        redisTemplate.boundValueOps(key).set(appAccessTokenMountValue, APP_ACCESS_TOKEN_NODE_EXPIRE_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public AppAccessTokenMountValue getMountTokenValue(String mountKey) {
        return (AppAccessTokenMountValue) redisTemplate.boundValueOps(mountKey).get();
    }

    @Override
    public long getMountExpire(String appAccessTokenUuid, String appUid, String refreshTokenUuid) {
        String key = getMountKey(appAccessTokenUuid, appUid, refreshTokenUuid);
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.MILLISECONDS);
        if (expire == null) {
            return 0;
        }
        return expire;
    }

    private String getRedisKey(String appUid, String uuid) {
        return String.format(APP_ACCESS_TOKEN_FOR_CHECK_KEY_TEMPLATE, appUid, uuid);
    }

    private String getMountKey(String appAccessTokenUuid, String appUid, String refreshTokenUuid) {
        return String.format(APP_ACCESS_TOKEN_NODE_KEY_TEMPLATE, refreshTokenUuid, appUid, appAccessTokenUuid);
    }
}
