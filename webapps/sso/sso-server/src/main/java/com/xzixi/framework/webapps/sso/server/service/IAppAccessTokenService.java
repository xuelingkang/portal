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

import com.xzixi.framework.webapps.sso.server.model.AppAccessTokenMountValue;
import com.xzixi.framework.webapps.sso.common.model.AppAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;

/**
 * @author xuelingkang
 * @date 2020-11-04
 */
public interface IAppAccessTokenService extends ITokenService {

    /**
     * 创建并保存jwtToken
     *
     * @param userId 用户id
     * @param appUid 应用uid
     * @param refreshToken jwtToken
     * @return TokenInfo
     */
    TokenInfo createAndSave(int userId, String appUid, String refreshToken);

    /**
     * 获取appAccessToken中保存的信息
     *
     * @param appAccessTokenUUid uuid
     * @param appUid 应用uid
     * @return AppAccessTokenValue
     */
    AppAccessTokenValue getTokenValue(String appAccessTokenUUid, String appUid);

    /**
     * 删除appAccessToken
     *
     * @param appAccessTokenUUid uuid
     * @param appUid 应用uid
     */
    void delete(String appAccessTokenUUid, String appUid);

    /**
     * 挂载appAccessToken
     *
     * @param appAccessTokenUuid uuid
     * @param appAccessToken jwtToken
     * @param appUid 应用uid
     * @param refreshTokenUuid uuid
     */
    void mount(String appAccessTokenUuid, String appAccessToken, String appUid, String refreshTokenUuid);

    /**
     * 卸载appAccessToken
     *
     * @param appAccessTokenUuid uuid
     * @param appUid 应用uid
     * @param refreshTokenUuid uuid
     */
    void unmount(String appAccessTokenUuid, String appUid, String refreshTokenUuid);

    /**
     * 获取挂载值
     *
     * @param mountKey redisKey
     * @return AppAccessTokenMountValue
     */
    AppAccessTokenMountValue getMountTokenValue(String mountKey);

    /**
     * 获取挂载过期时间
     *
     * @param appAccessTokenUuid uuid
     * @param appUid 应用uid
     * @param refreshTokenUuid uuid
     * @return 过期时间，毫秒
     */
    long getMountExpire(String appAccessTokenUuid, String appUid, String refreshTokenUuid);
}
