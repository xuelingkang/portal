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

/**
 * @author xuelingkang
 * @date 2020-11-04
 */
public interface IAppAccessTokenService extends ITokenService {

    String APP_ACCESS_TOKEN = "APP_ACCESS_TOKEN";

    String APP_UID = "APP_UID";

    /**
     * 创建并保存jwtToken
     *
     * @param userId 用户id
     * @param appUid 应用uid
     * @return jwtToken
     */
    String createAndSave(int userId, String appUid);

    /**
     * 验证appAccessToken，通过后删除
     *
     * @param appAccessToken jwtToken
     * @param appUid 应用uid
     */
    void check(String appAccessToken, String appUid);

    /**
     * 挂载appAccessToken，单点登出时使用
     *
     * @param appAccessToken jwtToken
     * @param appUid 应用uid
     * @param refreshToken jwtToken
     */
    void mount(String appAccessToken, String appUid, String refreshToken);

    /**
     * 删除挂载的节点，回调应用登出接口
     *
     * @param redisKey redisKey
     */
    void logout(String redisKey);
}
