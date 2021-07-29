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
import com.xzixi.framework.boot.core.exception.ServerException;
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

    private static final DefaultRedisScript<Long> TOKEN_SCRIPT;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Limit.Strategy strategy() {
        return Limit.Strategy.TOKEN;
    }

    @Override
    public boolean check(RedisLimit limit) {
        Long waitTime = stringRedisTemplate.execute(TOKEN_SCRIPT, ImmutableList.of(limit.getKey()),
                limit.getPeriod(), limit.getRate(), limit.getCapacity(), limit.getCount(), limit.getTimeout());
        if (waitTime != null && waitTime <= limit.getTimeout()) {
            if (log.isInfoEnabled()) {
                log.info("try wait for {}ms, thread: {}", waitTime, Thread.currentThread().getName());
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                throw new ServerException("exception occurred while wait for limiter token", e);
            }
            return true;
        }
        return false;
    }

    static {
        TOKEN_SCRIPT = new DefaultRedisScript<>();
        TOKEN_SCRIPT.setLocation(new ClassPathResource("/limiter/token.lua"));
        TOKEN_SCRIPT.setResultType(Long.class);
    }
}
