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

import com.xzixi.framework.webapps.sso.server.model.RefreshTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;
import com.xzixi.framework.webapps.sso.server.service.IRefreshTokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xzixi.framework.webapps.common.constant.TokenConstant.REFRESH_TOKEN_EXPIRE_MINUTE;
import static com.xzixi.framework.webapps.common.constant.TokenConstant.REFRESH_TOKEN_KEY_TEMPLATE;

/**
 * @author xuelingkang
 * @date 2020-11-03
 */
@Service
public class RefreshTokenServiceImpl extends AbstractTokenService implements IRefreshTokenService {

    @Override
    public String getClaimsKey() {
        return "SSO_REFRESH_LOGIN_USER_KEY";
    }

    @Override
    public TokenInfo createAndSave(int userId) {
        String uuid = UUID.randomUUID().toString();
        String jwtToken = getJwtToken(uuid);
        RefreshTokenValue refreshTokenValue = new RefreshTokenValue(userId);
        redisTemplate.boundValueOps(getRedisKey(uuid)).set(refreshTokenValue, REFRESH_TOKEN_EXPIRE_MINUTE, TimeUnit.MINUTES);
        return new TokenInfo(uuid, jwtToken);
    }

    @Override
    public RefreshTokenValue getTokenValue(String refreshTokenUuid) {
        return (RefreshTokenValue) redisTemplate.boundValueOps(getRedisKey(refreshTokenUuid)).get();
    }

    @Override
    public void delete(String refreshTokenUuid) {
        redisTemplate.opsForValue().getOperations().delete(getRedisKey(refreshTokenUuid));
    }

    @Override
    public long getExpire(String refreshTokenUuid) {
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(getRedisKey(refreshTokenUuid), TimeUnit.MILLISECONDS);
        if (expire == null) {
            return 0;
        }
        return expire;
    }

    private String getRedisKey(String uuid) {
        return String.format(REFRESH_TOKEN_KEY_TEMPLATE, uuid);
    }
}
