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

package com.xzixi.framework.boot.redis.service.impl;

import com.google.common.collect.ImmutableList;
import com.xzixi.framework.boot.redis.annotation.Limit;
import com.xzixi.framework.boot.redis.model.RedisLimit;
import com.xzixi.framework.boot.redis.service.RedisLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * RedisTokenLimiterImpl
 * 令牌桶限流
 *
 * @author xuelingkang
 * @version 1.0
 * @date 2021年07月28日
 */
@Slf4j
public class RedisTokenLimiterImpl implements RedisLimiter {

    private static final DefaultRedisScript<Boolean> TOKEN_SCRIPT;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Limit.Strategy strategy() {
        return Limit.Strategy.TOKEN;
    }

    @Override
    public boolean check(RedisLimit limit) {
        if (limit.getCount().compareTo(0) == 0) {
            // 消耗令牌数量等于0，返回成功
            return true;
        }
        if (limit.getCount().compareTo(limit.getCapacity()) > 0) {
            // 消耗令牌数量大于令牌桶容量，返回失败
            return false;
        }
        Boolean allow = stringRedisTemplate.execute(TOKEN_SCRIPT, ImmutableList.of(limit.getKey()),
                limit.getPeriod(), limit.getRate(), limit.getCapacity(), limit.getCount(), System.currentTimeMillis());
        return allow != null && allow;
    }

    static {
        TOKEN_SCRIPT = new DefaultRedisScript<>();
        TOKEN_SCRIPT.setLocation(new ClassPathResource("/limiter/token.lua"));
        TOKEN_SCRIPT.setResultType(Boolean.class);
    }
}
