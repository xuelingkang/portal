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

import com.xzixi.framework.webapps.sso.server.model.TokenInfo;

/**
 * @author xuelingkang
 * @date 2020-11-05
 */
public interface ITokenService {

    /**
     * jwtToken中保存了一个map
     *
     * @return map key
     */
    String getClaimsKey();

    /**
     * 加密uuid
     *
     * @param uuid uuid
     * @return jwtToken
     */
    String getJwtToken(String uuid);

    /**
     * 创建token
     *
     * @return TokenInfo
     */
    TokenInfo createToken();

    /**
     * 解密jwtToken
     *
     * @param jwtToken jwtToken
     * @return uuid
     */
    String decodeJwtToken(String jwtToken);
}
