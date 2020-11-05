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

package com.xzixi.framework.webapps.sso.server.service.impl;

import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.webapps.common.constant.SecurityConstant;
import com.xzixi.framework.webapps.common.model.po.Token;
import com.xzixi.framework.webapps.sso.server.service.ITokenService2;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 薛凌康
 */
@Service
public class TokenService2Impl implements ITokenService2 {

    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
    private static Key keySingleTon = null;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Token saveToken(Integer userId) {
        String uuid = UUID.randomUUID().toString();
        String signature = createSignature(uuid);
        long loginTime = System.currentTimeMillis();
        long expireTime = loginTime + SecurityConstant.AUTHENTICATION_EXPIRE_SECOND * 1000;
        Token token = new Token();
        token.setSignature(signature).setUserId(userId).setLoginTime(loginTime).setExpireTime(expireTime);
        redisTemplate.boundValueOps(getTokenKey(uuid)).set(token, SecurityConstant.AUTHENTICATION_EXPIRE_SECOND, TimeUnit.SECONDS);
        return token;
    }

    @Override
    public Token getToken(String signature) {
        String uuid = getUuidFromSignature(signature);
        return (Token) redisTemplate.opsForValue().get(getTokenKey(uuid));
    }

    @Override
    public Token refreshToken(String signature) {
        String uuid = getUuidFromSignature(signature);
        Token token = (Token) redisTemplate.opsForValue().get(getTokenKey(uuid));
        if (token != null) {
            token.setExpireTime(System.currentTimeMillis() + SecurityConstant.AUTHENTICATION_EXPIRE_SECOND * 1000);
            redisTemplate.boundValueOps(uuid).set(token, SecurityConstant.AUTHENTICATION_EXPIRE_SECOND, TimeUnit.SECONDS);
        }
        return token;
    }

    @Override
    public void deleteToken(String signature) {
        String uuid = getUuidFromSignature(signature);
        Token token = (Token) redisTemplate.opsForValue().get(getTokenKey(uuid));
        if (token != null) {
            redisTemplate.delete(getTokenKey(uuid));
        }
    }

    private String getTokenKey(String uuid) {
        return String.format(SecurityConstant.AUTHENTICATION_REDIS_KEY_TEMPLATE, uuid);
    }

    /**
     * 使用私钥生成key，双重检查锁定
     *
     * @return Key
     */
    private Key getKeyInstance() {
        if (keySingleTon == null) {
            synchronized (TokenService2Impl.class) {
                if (keySingleTon == null) {
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstant.AUTHENTICATION_JWT_SECRET);
                    keySingleTon = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }
        return keySingleTon;
    }

    /**
     * 将uuid保存到map中，加密并返回一个token字符串
     *
     * @param uuid uuid
     * @return token字符串
     */
    private String createSignature(String uuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, uuid);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    /**
     * 根据token字符串解密出uuid
     *
     * @param signature token字符串
     * @return uuid，用来获取redis中的token对象
     */
    private String getUuidFromSignature(String signature) {
        if (StringUtils.isEmpty(signature) || Objects.equals(SecurityConstant.NULL_TOKEN, signature)) {
            throw new ClientException(401, "认证信息无效！");
        }
        Map<String, Object> jwtClaims;
        try {
            jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(signature).getBody();
        } catch (Exception e) {
            throw new ClientException(401, "认证信息无效！");
        }
        if (jwtClaims != null) {
            Object uuid = jwtClaims.get(LOGIN_USER_KEY);
            if (uuid != null) {
                return uuid.toString();
            }
        }
        throw new ClientException(401, "认证信息无效！");
    }
}
