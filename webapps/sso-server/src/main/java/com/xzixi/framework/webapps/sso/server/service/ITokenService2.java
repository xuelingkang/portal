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

import com.xzixi.framework.webapps.common.model.po.Token;

/**
 * TODO 重构
 *  支持accessToken和refreshToken
 *
 * @author 薛凌康
 */
public interface ITokenService2 {

    /**
     * 生成并保存token
     *
     * @param userId 用户id
     * @return Token
     */
    Token saveToken(Integer userId);

    /**
     * 获取token对象
     *
     * @param signature token签名
     * @return Token
     */
    Token getToken(String signature);

    /**
     * 更新token过期时间
     *
     * @param signature token签名
     * @return Token
     */
    Token refreshToken(String signature);

    /**
     * 删除token签名对应的token对象
     *
     * @param signature token签名
     */
    void deleteToken(String signature);
}
