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
 * @date 2020-11-03
 */
public interface ISsoAccessTokenService extends ITokenService {

    /**
     * 创建并保存jwtToken
     *
     * @param userId 用户id
     * @return jwtToken
     */
    String createAndSave(int userId);

    /**
     * 挂载ssoAccessToken，单点登出时使用
     *
     * @param ssoAccessToken jwtToken
     * @param refreshToken jwtToken
     */
    void mount(String ssoAccessToken, String refreshToken);

    /**
     * 删除ssoAccessToken
     *
     * @param ssoAccessToken jwtToken
     */
    void delete(String ssoAccessToken);

    /**
     * 删除挂载的节点和ssoAccessToken
     *
     * @param redisKey redisKey
     */
    void logout(String redisKey);
}
