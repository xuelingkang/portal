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

import com.xzixi.framework.webapps.sso.server.model.SsoAccessTokenMountValue;
import com.xzixi.framework.webapps.sso.server.model.SsoAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;
import com.xzixi.framework.webapps.sso.server.service.ISsoAccessTokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xzixi.framework.webapps.sso.server.constant.TokenConstant.*;

/**
 * @author xuelingkang
 * @date 2020-11-03
 */
@Service
public class SsoAccessTokenServiceImpl extends AbstractTokenService implements ISsoAccessTokenService {

    @Override
    public String getClaimsKey() {
        return "SSO_ACCESS_LOGIN_USER_KEY";
    }

    @Override
    public TokenInfo createAndSave(int userId, String refreshToken) {
        String uuid = UUID.randomUUID().toString();
        String jwtToken = getJwtToken(uuid);
        SsoAccessTokenValue ssoAccessTokenValue = new SsoAccessTokenValue(userId, refreshToken);
        redisTemplate.boundValueOps(getRedisKey(uuid)).set(ssoAccessTokenValue, SSO_ACCESS_TOKEN_EXPIRE_MINUTE, TimeUnit.MINUTES);
        return new TokenInfo(uuid, jwtToken);
    }

    @Override
    public SsoAccessTokenValue getTokenValue(String ssoAccessTokenUuid) {
        return (SsoAccessTokenValue) redisTemplate.boundValueOps(getRedisKey(ssoAccessTokenUuid)).get();
    }

    @Override
    public void delete(String ssoAccessTokenUuid) {
        redisTemplate.opsForValue().getOperations().delete(getRedisKey(ssoAccessTokenUuid));
    }

    @Override
    public void mount(String ssoAccessTokenUuid, String ssoAccessToken, String refreshTokenUuid) {
        String key = getMountKey(ssoAccessTokenUuid, refreshTokenUuid);
        SsoAccessTokenMountValue ssoAccessTokenMountValue = new SsoAccessTokenMountValue(ssoAccessToken);
        redisTemplate.boundValueOps(key).set(ssoAccessTokenMountValue, SSO_ACCESS_TOKEN_NODE_EXPIRE_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public void unmount(String ssoAccessTokenUuid, String refreshTokenUuid) {
        String key = getMountKey(ssoAccessTokenUuid, refreshTokenUuid);
        redisTemplate.boundValueOps(key).expire(0, TimeUnit.SECONDS);
    }

    @Override
    public SsoAccessTokenMountValue getTokenMountValue(String mountKey) {
        return (SsoAccessTokenMountValue) redisTemplate.boundValueOps(mountKey).get();
    }

    private String getRedisKey(String uuid) {
        return String.format(SSO_ACCESS_TOKEN_KEY_TEMPLATE, uuid);
    }

    private String getMountKey(String ssoAccessTokenUuid, String refreshTokenUuid) {
        return String.format(SSO_ACCESS_TOKEN_NODE_KEY_TEMPLATE, refreshTokenUuid, ssoAccessTokenUuid);
    }
}
