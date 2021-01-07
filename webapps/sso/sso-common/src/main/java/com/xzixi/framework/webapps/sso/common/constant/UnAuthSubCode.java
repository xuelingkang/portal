/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2021  xuelingkang@163.com.
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

package com.xzixi.framework.webapps.sso.common.constant;

/**
 * @author xuelingkang
 * @date 2021-01-07
 */
public interface UnAuthSubCode {

    /**
     * 认证失败或者refreshToken过期
     */
    int DEFAULT = 0;
    /**
     * accessToken过期
     */
    int ACCESS_EXPIRE = 1;
    /**
     * 未激活
     */
    int INACTIVATED = 2;
    /**
     * 被锁定
     */
    int LOCKED = 3;
}
