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
    public String createAndSave(int userId) {
        String uuid = UUID.randomUUID().toString();
        String jwtToken = getJwtToken(uuid);
        redisTemplate.boundValueOps(getRedisKey(uuid)).set(userId, REFRESH_TOKEN_EXPIRE_MINUTE, TimeUnit.MINUTES);
        return jwtToken;
    }

    @Override
    public void check(String refreshToken) {
        // TODO
    }

    @Override
    public void delete(String refreshToken) {
        // TODO
    }

    @Override
    public void logout(String redisKey) {
        // TODO
    }

    private String getRedisKey(String uuid) {
        return String.format(REFRESH_TOKEN_KEY_TEMPLATE, uuid);
    }
}
