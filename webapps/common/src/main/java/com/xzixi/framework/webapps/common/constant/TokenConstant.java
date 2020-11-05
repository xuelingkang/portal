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

package com.xzixi.framework.webapps.common.constant;

/**
 * @author xuelingkang
 * @date 2020-11-04
 */
public interface TokenConstant {

    /**
     * refreshTokenKey模板
     * <p>value userId
     * <p>占位符 refreshToken加密保存的uuid
     * <p>sso维护
     */
    String REFRESH_TOKEN_KEY_TEMPLATE = "token::refresh::%s";

    /**
     * ssoAccessTokenKey模板
     * <p>value userId
     * <p>占位符 ssoAccessToken加密保存的uuid
     * <p>sso维护
     */
    String SSO_ACCESS_TOKEN_KEY_TEMPLATE = "token::sso-access::%s";

    /**
     * appAccessTokenForCheckKey模板
     * <p>value userId
     * <p>占位符1 appUid
     * <p>占位符2 appAccessToken加密保存的uuid
     * <p>sso维护
     */
    String APP_ACCESS_TOKEN_FOR_CHECK_KEY_TEMPLATE = "token::%s-access::%s";

    /**
     * ssoAccessTokenNodeKey模板
     * <p>value ssoAccessToken
     * <p>占位符1 refreshToken加密保存的uuid
     * <p>占位符2 ssoAccessToken加密保存的uuid
     * <p>sso维护
     */
    String SSO_ACCESS_TOKEN_NODE_KEY_TEMPLATE = "token::refresh::%s::sso::%s";

    /**
     * appAccessTokenNodeKey模板
     * <p>value appAccessToken, appUid
     * <p>占位符1 refreshToken加密保存的uuid
     * <p>占位符2 appUid
     * <p>占位符3 appAccessToken加密保存的uuid
     * <p>sso维护
     */
    String APP_ACCESS_TOKEN_NODE_KEY_TEMPLATE = "token::refresh::%s::%s::%s";

    /**
     * appAccessTokenKey模板
     * <p>value userId
     * <p>占位符 appAccessToken
     * <p>app维护
     */
    String APP_ACCESS_TOKEN_KEY_TEMPLATE = "token::%s";

    /**
     * appAccessTokenRefreshKey模板
     * <p>value refreshToken
     * <p>占位符 appAccessToken
     * <p>app维护
     */
    String APP_ACCESS_TOKEN_REFRESH_KEY_TEMPLATE = "token::%s::refresh";

    /**
     * refreshTokenKey过期时间，7天
     * <p>sso维护
     */
    long REFRESH_TOKEN_EXPIRE_MINUTE = 7 * 24 * 60L;

    /**
     * ssoAccessTokenKey过期时间，30分钟
     * <p>sso维护
     */
    long SSO_ACCESS_TOKEN_EXPIRE_MINUTE = 30L;

    /**
     * appAccessTokenForCheckKey过期时间，1分钟
     * <p>sso维护
     * <p>安全考虑，app验证后删除，然后app自行维护，不设置太长的过期时间
     */
    long APP_ACCESS_TOKEN_FOR_CHECK_EXPIRE_MINUTE = 1L;

    /**
     * ssoAccessTokenNodeKey过期时间
     * <p>sso维护
     */
    long SSO_ACCESS_TOKEN_NODE_EXPIRE_MINUTE = SSO_ACCESS_TOKEN_EXPIRE_MINUTE;

    /**
     * appAccessTokenNodeKey过期时间
     * <p>sso维护
     */
    long APP_ACCESS_TOKEN_NODE_EXPIRE_MINUTE = SSO_ACCESS_TOKEN_EXPIRE_MINUTE;

    /**
     * appAccessTokenKey过期时间
     * <p>app维护
     */
    long APP_ACCESS_TOKEN_EXPIRE_MINUTE = SSO_ACCESS_TOKEN_EXPIRE_MINUTE;

    /**
     * appAccessTokenRefreshKey过期时间
     * <p>app维护
     */
    long APP_ACCESS_TOKEN_REFRESH_EXPIRE_MINUTE = APP_ACCESS_TOKEN_EXPIRE_MINUTE;
}
