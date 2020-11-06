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

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.webapps.sso.server.service.ITokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xuelingkang
 * @date 2020-11-03
 */
public abstract class AbstractTokenService implements ITokenService {

    @Autowired
    private Key key;
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    abstract String getClaimsKey();

    /**
     * 将uuid保存到map中，加密并返回一个jwtToken
     *
     * @param uuid uuid
     * @return jwtToken
     */
    protected String getJwtToken(String uuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(getClaimsKey(), uuid);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    /**
     * 解密jwtToken
     *
     * @param jwtToken jwtToken
     * @return uuid
     */
    public String decodeJwtToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken) || Objects.equals("null", jwtToken)) {
            throw new ClientException(401, "无效的jwtToken！");
        }
        Map<String, Object> jwtClaims;
        try {
            jwtClaims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            throw new ClientException(401, "无效的jwtToken！");
        }
        if (jwtClaims != null) {
            Object uuid = jwtClaims.get(getClaimsKey());
            if (uuid != null) {
                return uuid.toString();
            }
        }
        throw new ClientException(401, "无效的jwtToken！");
    }
}
