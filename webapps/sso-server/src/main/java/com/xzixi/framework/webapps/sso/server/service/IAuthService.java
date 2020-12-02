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

import com.xzixi.framework.webapps.common.model.vo.sso.AppCheckTokenResponse;
import com.xzixi.framework.webapps.common.model.vo.sso.RefreshAccessTokenResponse;
import com.xzixi.framework.webapps.common.model.vo.sso.LoginSuccessResponse;

/**
 * @author xuelingkang
 * @date 2020-11-05
 */
public interface IAuthService {

    /**
     * 首次登录
     *
     * @param userId 用户id
     * @param appUid 应用uid
     * @return sso登录响应
     */
    LoginSuccessResponse login(int userId, String appUid);

    /**
     * 使用ssoAccessToken登录
     *
     * @param ssoAccessToken jwtToken
     * @param appUid 应用uid
     * @return sso登录响应
     */
    LoginSuccessResponse login(String ssoAccessToken, String appUid);

    /**
     * 刷新ssoAccessToken
     *
     * @param refreshToken jwtToken
     * @return 刷新响应
     */
    RefreshAccessTokenResponse refreshSsoAccessToken(String refreshToken);

    /**
     * 刷新appAccessToken
     *
     * @param refreshToken jwtToken
     * @param appUid 应用uid
     * @return 刷新响应
     */
    RefreshAccessTokenResponse refreshAppAccessToken(String refreshToken, String appUid);

    /**
     * 验证应用收到的token是否真实
     *
     * @param refreshToken jwtToken
     * @param appAccessToken jwtToken
     * @param appUid 应用uid
     */
    AppCheckTokenResponse check(String refreshToken, String appAccessToken, String appUid);

    /**
     * 单点登出
     *
     * @param refreshToken jwtToken
     */
    void logout(String refreshToken);
}
