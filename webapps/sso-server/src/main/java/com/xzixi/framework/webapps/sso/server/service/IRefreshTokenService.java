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

package com.xzixi.framework.webapps.sso.server.service;

import com.xzixi.framework.webapps.sso.server.model.RefreshTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;

/**
 * @author xuelingkang
 * @date 2020-11-03
 */
public interface IRefreshTokenService extends ITokenService {

    /**
     * 创建并保存jwtToken
     *
     * @param userId 用户id
     * @return TokenInfo
     */
    TokenInfo createAndSave(int userId);

    /**
     * 获取refreshToken保存的信息
     *
     * @param refreshTokenUuid uuid
     * @return RefreshTokenValue
     */
    RefreshTokenValue getTokenValue(String refreshTokenUuid);

    /**
     * 删除refreshToken
     *
     * @param refreshTokenUuid uuid
     */
    void delete(String refreshTokenUuid);

    /**
     * 获取refreshToken的过期时间
     *
     * @param refreshTokenUuid uuid
     * @return 过期时间，毫秒
     */
    long getExpire(String refreshTokenUuid);
}
