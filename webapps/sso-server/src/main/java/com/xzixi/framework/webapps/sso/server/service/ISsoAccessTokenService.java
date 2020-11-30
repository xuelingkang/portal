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

package com.xzixi.framework.webapps.sso.server.service;

import com.xzixi.framework.webapps.sso.server.model.SsoAccessTokenMountValue;
import com.xzixi.framework.webapps.sso.server.model.SsoAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;

/**
 * @author xuelingkang
 * @date 2020-11-03
 */
public interface ISsoAccessTokenService extends ITokenService {

    /**
     * 创建并保存jwtToken
     *
     * @param userId 用户id
     * @param refreshToken jwtToken
     * @return TokenInfo
     */
    TokenInfo createAndSave(int userId, String refreshToken);

    /**
     * 获取ssoAccessToken保存的信息
     *
     * @param ssoAccessTokenUuid uuid
     * @return SsoAccessTokenValue
     */
    SsoAccessTokenValue getTokenValue(String ssoAccessTokenUuid);

    /**
     * 删除ssoAccessToken
     *
     * @param ssoAccessTokenUuid uuid
     */
    void delete(String ssoAccessTokenUuid);

    /**
     * 挂载ssoAccessToken
     *
     * @param ssoAccessTokenUuid uuid
     * @param ssoAccessToken jwtToken
     * @param refreshTokenUuid uuid
     */
    void mount(String ssoAccessTokenUuid, String ssoAccessToken, String refreshTokenUuid);

    /**
     * 卸载ssoAccessToken
     *
     * @param ssoAccessTokenUuid uuid
     * @param refreshTokenUuid uuid
     */
    void unmount(String ssoAccessTokenUuid, String refreshTokenUuid);

    /**
     * 获取挂载值
     *
     * @param mountKey redisKey
     * @return SsoAccessTokenMountValue
     */
    SsoAccessTokenMountValue getTokenMountValue(String mountKey);
}
