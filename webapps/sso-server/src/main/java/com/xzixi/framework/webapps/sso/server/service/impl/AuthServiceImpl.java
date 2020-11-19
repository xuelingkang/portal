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

package com.xzixi.framework.webapps.sso.server.service.impl;

import com.xzixi.framework.boot.core.exception.LockAcquireException;
import com.xzixi.framework.boot.core.model.ILock;
import com.xzixi.framework.boot.redis.service.impl.RedisLockService;
import com.xzixi.framework.webapps.remote.service.RemoteAppService;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.common.model.vo.sso.AppCheckTokenResponse;
import com.xzixi.framework.webapps.common.model.vo.sso.RefreshAccessTokenResponse;
import com.xzixi.framework.webapps.common.model.vo.sso.SsoServerLoginResponse;
import com.xzixi.framework.webapps.remote.util.RemoteServiceWrapper;
import com.xzixi.framework.webapps.sso.server.exception.AccessTokenExpireException;
import com.xzixi.framework.webapps.sso.server.exception.AuthException;
import com.xzixi.framework.webapps.sso.server.exception.RefreshTokenExpireException;
import com.xzixi.framework.webapps.sso.server.model.AppAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.RefreshTokenValue;
import com.xzixi.framework.webapps.sso.server.model.SsoAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;
import com.xzixi.framework.webapps.sso.server.service.IAppAccessTokenService;
import com.xzixi.framework.webapps.sso.server.service.IAuthService;
import com.xzixi.framework.webapps.sso.server.service.IRefreshTokenService;
import com.xzixi.framework.webapps.sso.server.service.ISsoAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.xzixi.framework.webapps.common.constant.TokenConstant.*;

/**
 * @author xuelingkang
 * @date 2020-11-05
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private static final String LOCK_PREFIX = "lock::authentication::";

    @Autowired
    private IRefreshTokenService refreshTokenService;
    @Autowired
    private ISsoAccessTokenService ssoAccessTokenService;
    @Autowired
    private IAppAccessTokenService appAccessTokenService;
    @Autowired
    private RemoteAppService remoteAppService;
    @Autowired
    private RedisLockService redisLockService;

    @Override
    public SsoServerLoginResponse login(int userId, String appUid) {
        // 查询应用信息
        App app = RemoteServiceWrapper.getData(remoteAppService.getByUid(appUid));

        long now = System.currentTimeMillis();
        // 生成并保存token
        TokenInfo refreshTokenInfo = refreshTokenService.createAndSave(userId);
        String refreshTokenUuid = refreshTokenInfo.getUuid();
        String refreshToken = refreshTokenInfo.getJwtToken();

        TokenInfo ssoAccessTokenInfo = ssoAccessTokenService.createAndSave(userId, refreshToken);
        String ssoAccessTokenUuid = ssoAccessTokenInfo.getUuid();
        String ssoAccessToken = ssoAccessTokenInfo.getJwtToken();

        TokenInfo appAccessTokenInfo = appAccessTokenService.createAndSave(userId, appUid, refreshToken);
        String appAccessTokenUuid = appAccessTokenInfo.getUuid();
        String appAccessToken = appAccessTokenInfo.getJwtToken();

        // 挂载ssoAccessToken和appAccessToken，单点登出时会用到
        ssoAccessTokenService.mount(ssoAccessTokenUuid, ssoAccessToken, refreshTokenUuid);
        appAccessTokenService.mount(appAccessTokenUuid, appAccessToken, appUid, refreshTokenUuid);

        SsoServerLoginResponse response = new SsoServerLoginResponse();
        response.setSsoAccessToken(ssoAccessToken);
        response.setAppAccessToken(appAccessToken);
        response.setRefreshToken(refreshToken);
        response.setLoginTime(now);
        response.setAccessExpireTime(now + SSO_ACCESS_TOKEN_EXPIRE_MINUTE * 60 * 1000);
        response.setRefreshExpireTime(now + REFRESH_TOKEN_EXPIRE_MINUTE * 60 * 1000);
        String redirectUrl = String.format("%s?accessToken=%s&refreshToken=%s",
                app.getLoginCallbackUrl(), appAccessToken, refreshToken);
        response.setRedirectUrl(redirectUrl);

        return response;
    }

    @Override
    public SsoServerLoginResponse login(String ssoAccessToken, String appUid) {
        // 获取ssoAccessToken保存的信息
        String ssoAccessTokenUuid = ssoAccessTokenService.decodeJwtToken(ssoAccessToken);
        SsoAccessTokenValue ssoAccessTokenValue = ssoAccessTokenService.getTokenValue(ssoAccessTokenUuid);
        if (ssoAccessTokenValue == null) {
            throw new AccessTokenExpireException();
        }
        int userId = ssoAccessTokenValue.getUserId();
        String refreshToken = ssoAccessTokenValue.getRefreshToken();

        // 解密refreshToken获取uuid
        String refreshTokenUuid = refreshTokenService.decodeJwtToken(refreshToken);

        ILock lock = redisLockService.getLock(LOCK_PREFIX + refreshTokenUuid);
        try {
            lock.acquire();
            long refreshTokenExpire = refreshTokenService.getExpire(refreshTokenUuid);
            if (refreshTokenExpire == 0) {
                throw new RefreshTokenExpireException();
            }

            TokenInfo appAccessTokenInfo = appAccessTokenService.createAndSave(userId, appUid, refreshToken);
            String appAccessTokenUuid = appAccessTokenInfo.getUuid();
            String appAccessToken = appAccessTokenInfo.getJwtToken();

            // 挂载appAccessToken
            appAccessTokenService.mount(appAccessTokenUuid, appAccessToken, appUid, refreshTokenUuid);

            // 查询应用信息
            App app = RemoteServiceWrapper.getData(remoteAppService.getByUid(appUid));

            SsoServerLoginResponse response = new SsoServerLoginResponse();
            response.setSsoAccessToken(ssoAccessToken);
            response.setAppAccessToken(appAccessToken);
            response.setRefreshToken(refreshToken);
            String redirectUrl = String.format("%s?accessToken=%s&refreshToken=%s",
                    app.getLoginCallbackUrl(), appAccessToken, refreshToken);
            response.setRedirectUrl(redirectUrl);

            return response;
        } catch (LockAcquireException e) {
            throw new AuthException();
        } finally {
            lock.safeRelease();
        }
    }

    @Override
    public AppCheckTokenResponse check(String refreshToken, String appAccessToken, String appUid) {
        long now = System.currentTimeMillis();

        // 解密refreshToken获取uuid
        String refreshTokenUuid = refreshTokenService.decodeJwtToken(refreshToken);

        ILock lock = redisLockService.getLock(LOCK_PREFIX + refreshTokenUuid);
        try {
            lock.acquire();
            long refreshTokenExpire = refreshTokenService.getExpire(refreshTokenUuid);
            if (refreshTokenExpire == 0) {
                throw new RefreshTokenExpireException();
            }
            RefreshTokenValue refreshTokenValue = refreshTokenService.getTokenValue(refreshTokenUuid);
            if (refreshTokenValue == null) {
                throw new RefreshTokenExpireException();
            }

            int userIdInRefreshToken = refreshTokenValue.getUserId();

            // 解密appAccessToken获取uuid
            String appAccessTokenUuid = appAccessTokenService.decodeJwtToken(appAccessToken);
            AppAccessTokenValue appAccessTokenValue = appAccessTokenService.getTokenValue(appAccessTokenUuid, appUid);
            if (appAccessTokenValue == null) {
                throw new AccessTokenExpireException();
            }

            int userIdInAccessToken = appAccessTokenValue.getUserId();
            if (userIdInAccessToken <= 0) {
                throw new AuthException();
            }

            String refreshTokenInAccessToken = appAccessTokenValue.getRefreshToken();
            if (!Objects.equals(refreshToken, refreshTokenInAccessToken)) {
                throw new AuthException();
            }

            if (userIdInAccessToken != userIdInRefreshToken) {
                throw new AuthException();
            }

            // 删除appAccessToken
            appAccessTokenService.delete(appAccessTokenUuid, appUid);

            // 获取挂载过期时间
            long appAccessTokenMountExpire = appAccessTokenService.getMountExpire(appAccessTokenUuid, appUid, refreshTokenUuid);

            AppCheckTokenResponse appCheckTokenResponse = new AppCheckTokenResponse();
            appCheckTokenResponse.setUserId(userIdInAccessToken);
            appCheckTokenResponse.setAccessExpireTime(now + appAccessTokenMountExpire);
            appCheckTokenResponse.setRefreshExpireTime(now + refreshTokenExpire);

            return appCheckTokenResponse;
        } catch (LockAcquireException e) {
            throw new AuthException();
        } finally {
            lock.safeRelease();
        }
    }

    @Override
    public RefreshAccessTokenResponse refreshSsoAccessToken(String refreshToken) {
        // 解密refreshToken获取uuid
        String refreshTokenUuid = refreshTokenService.decodeJwtToken(refreshToken);

        ILock lock = redisLockService.getLock(LOCK_PREFIX + refreshTokenUuid);
        try {
            lock.acquire();
            RefreshTokenValue refreshTokenValue = refreshTokenService.getTokenValue(refreshTokenUuid);
            if (refreshTokenValue == null) {
                throw new RefreshTokenExpireException();
            }

            int userId = refreshTokenValue.getUserId();
            long now = System.currentTimeMillis();

            TokenInfo ssoAccessTokenInfo = ssoAccessTokenService.createAndSave(userId, refreshToken);
            String ssoAccessTokenUuid = ssoAccessTokenInfo.getUuid();
            String ssoAccessToken = ssoAccessTokenInfo.getJwtToken();

            // 挂载ssoAccessToken
            ssoAccessTokenService.mount(ssoAccessTokenUuid, ssoAccessToken, refreshTokenUuid);

            RefreshAccessTokenResponse refreshAccessTokenResponse = new RefreshAccessTokenResponse();
            refreshAccessTokenResponse.setAccessToken(ssoAccessToken);
            refreshAccessTokenResponse.setUserId(userId);
            refreshAccessTokenResponse.setExpireTime(now + SSO_ACCESS_TOKEN_EXPIRE_MINUTE * 60 * 1000);

            return refreshAccessTokenResponse;
        } catch (LockAcquireException e) {
            throw new AuthException();
        } finally {
            lock.safeRelease();
        }
    }

    @Override
    public RefreshAccessTokenResponse refreshAppAccessToken(String refreshToken, String appUid) {
        // 解密refreshToken获取uuid
        String refreshTokenUuid = refreshTokenService.decodeJwtToken(refreshToken);

        ILock lock = redisLockService.getLock(LOCK_PREFIX + refreshTokenUuid);
        try {
            lock.acquire();
            RefreshTokenValue refreshTokenValue = refreshTokenService.getTokenValue(refreshTokenUuid);
            if (refreshTokenValue == null) {
                throw new RefreshTokenExpireException();
            }

            int userId = refreshTokenValue.getUserId();
            long now = System.currentTimeMillis();

            // 创建但不保存token
            TokenInfo appAccessTokenInfo = appAccessTokenService.createToken();
            String appAccessTokenUuid = appAccessTokenInfo.getUuid();
            String appAccessToken = appAccessTokenInfo.getJwtToken();

            // 挂载appAccessToken
            appAccessTokenService.mount(appAccessTokenUuid, appAccessToken, appUid, refreshTokenUuid);

            RefreshAccessTokenResponse refreshAccessTokenResponse = new RefreshAccessTokenResponse();
            refreshAccessTokenResponse.setAccessToken(appAccessToken);
            refreshAccessTokenResponse.setUserId(userId);
            refreshAccessTokenResponse.setExpireTime(now + APP_ACCESS_TOKEN_EXPIRE_MINUTE * 60 * 1000);

            return refreshAccessTokenResponse;
        } catch (LockAcquireException e) {
            throw new AuthException();
        } finally {
            lock.safeRelease();
        }
    }

    @Override
    public void logout(String refreshToken) {
        // TODO 加分布式锁
    }
}
