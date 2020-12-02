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
import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.redis.service.impl.RedisLockService;
import com.xzixi.framework.boot.redis.service.impl.RedisScanService;
import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.common.model.vo.sso.AppCheckTokenResponse;
import com.xzixi.framework.webapps.common.model.vo.sso.RefreshAccessTokenResponse;
import com.xzixi.framework.webapps.common.model.vo.sso.LoginSuccessResponse;
import com.xzixi.framework.webapps.remote.service.RemoteAppService;
import com.xzixi.framework.webapps.remote.service.RemoteUserService;
import com.xzixi.framework.webapps.sso.server.constant.SecurityConstant;
import com.xzixi.framework.webapps.sso.server.constant.SsoServerConstant;
import com.xzixi.framework.webapps.sso.server.constant.TokenConstant;
import com.xzixi.framework.webapps.sso.server.exception.AccessTokenExpireException;
import com.xzixi.framework.webapps.sso.server.exception.AuthException;
import com.xzixi.framework.webapps.sso.server.exception.RefreshTokenExpireException;
import com.xzixi.framework.webapps.sso.server.model.*;
import com.xzixi.framework.webapps.sso.server.service.IAppAccessTokenService;
import com.xzixi.framework.webapps.sso.server.service.IAuthService;
import com.xzixi.framework.webapps.sso.server.service.IRefreshTokenService;
import com.xzixi.framework.webapps.sso.server.service.ISsoAccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xuelingkang
 * @date 2020-11-05
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {

    private static final String LOCK_PREFIX = "lock::authentication::";
    private static final String MOUNT_KEY_TEMPLATE = "token::refresh::%s::";
    private static final String MOUNT_KEY_SSO_TEMPLATE = MOUNT_KEY_TEMPLATE + "sso::";

    @Autowired
    private IRefreshTokenService refreshTokenService;
    @Autowired
    private ISsoAccessTokenService ssoAccessTokenService;
    @Autowired
    private IAppAccessTokenService appAccessTokenService;
    @Autowired
    private RemoteAppService remoteAppService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private RedisScanService redisScanService;
    @Autowired
    private ISignService signService;

    @Override
    public LoginSuccessResponse login(int userId, String appUid) {
        // 查询应用信息
        App app = remoteAppService.getByUid(appUid).getData();

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

        LoginSuccessResponse response = new LoginSuccessResponse();
        response.setSsoAccessToken(ssoAccessToken);
        response.setAppAccessToken(appAccessToken);
        response.setRefreshToken(refreshToken);
        response.setLoginTime(now);
        response.setAccessExpireTime(now + TokenConstant.SSO_ACCESS_TOKEN_EXPIRE_MINUTE * 60 * 1000);
        response.setRefreshExpireTime(now + TokenConstant.REFRESH_TOKEN_EXPIRE_MINUTE * 60 * 1000);
        String redirectUrl = String.format("%s?accessToken=%s&refreshToken=%s",
                app.getLoginCallbackUrl(), appAccessToken, refreshToken);
        response.setRedirectUrl(redirectUrl);

        // 刷新登录时间
        remoteUserService.updateLoginTime(userId, now);

        return response;
    }

    @Override
    public LoginSuccessResponse login(String ssoAccessToken, String appUid) {
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
            App app = remoteAppService.getByUid(appUid).getData();

            LoginSuccessResponse response = new LoginSuccessResponse();
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
            refreshAccessTokenResponse.setExpireTime(now + TokenConstant.SSO_ACCESS_TOKEN_EXPIRE_MINUTE * 60 * 1000);

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
            refreshAccessTokenResponse.setExpireTime(now + TokenConstant.APP_ACCESS_TOKEN_EXPIRE_MINUTE * 60 * 1000);

            return refreshAccessTokenResponse;
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
    public void logout(String refreshToken) {
        // 解密refreshToken获取uuid
        String refreshTokenUuid = refreshTokenService.decodeJwtToken(refreshToken);

        ILock lock = redisLockService.getLock(LOCK_PREFIX + refreshTokenUuid);
        try {
            lock.acquire();
            String pattern = String.format(MOUNT_KEY_TEMPLATE, refreshTokenUuid);
            while (true) {
                List<String> keys = redisScanService.scan(pattern);
                if (CollectionUtils.isEmpty(keys)) {
                    break;
                }
                logoutApps(keys, refreshTokenUuid);
            }
            refreshTokenService.delete(refreshTokenUuid);
        } catch (LockAcquireException e) {
            throw new AuthException();
        } finally {
            lock.safeRelease();
        }
    }

    /**
     * 登出所有应用包括sso
     *
     * @param keys redis key
     */
    private void logoutApps(List<String> keys, String refreshTokenUuid) {
        // 查询sso应用信息
        App ssoServer = remoteAppService.getByUid(SsoServerConstant.APP_UID).getData();

        for (String key : keys) {
            if (key.startsWith(String.format(MOUNT_KEY_SSO_TEMPLATE, refreshTokenUuid))) {
                // 登出sso
                SsoAccessTokenMountValue ssoAccessTokenMountValue = ssoAccessTokenService.getTokenMountValue(key);
                String ssoAccessToken = ssoAccessTokenMountValue.getSsoAccessToken();
                String ssoAccessTokenUuid = ssoAccessTokenService.decodeJwtToken(ssoAccessToken);
                ssoAccessTokenService.delete(ssoAccessTokenUuid);
                ssoAccessTokenService.unmount(ssoAccessTokenUuid, refreshTokenUuid);
                continue;
            }

            // 登出app
            AppAccessTokenMountValue appAccessTokenMountValue = appAccessTokenService.getMountTokenValue(key);
            String appUid = appAccessTokenMountValue.getAppUid();
            String appAccessToken = appAccessTokenMountValue.getAppAccessToken();
            String appAccessTokenUuid = appAccessTokenService.decodeJwtToken(appAccessToken);
            // 查询app信息
            App app = remoteAppService.getByUid(appUid).getData();

            // 构造签名和参数
            long now = System.currentTimeMillis();
            Map<String, Object> params = new HashMap<>();
            params.put(SecurityConstant.ACCESS_TOKEN_NAME, appAccessToken);
            params.put(ProjectConstant.APP_UID_NAME, SsoServerConstant.APP_UID);
            params.put(ISignService.TIMESTAMP_NAME, now);
            String sign = signService.genSign(params, ssoServer.getSecret());

            // 构造app登出请求
            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            params.forEach((name, value) -> queryParams.add(name, String.valueOf(value)));
            queryParams.add(ProjectConstant.SIGN_NAME, sign);
            String uri = UriComponentsBuilder.fromHttpUrl(app.getLogoutCallbackUrl()).queryParams(queryParams).toUriString();
            WebClient.create(uri)
                    .get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Result.class)
                    .subscribe();

            // 卸载app挂载的key
            appAccessTokenService.unmount(appAccessTokenUuid, appUid, refreshTokenUuid);
        }
    }
}
