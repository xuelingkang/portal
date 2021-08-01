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
 * RedisLeakyLimiterImpl
 * 漏桶限流
 *
 * @author xuelingkang
 * @version 1.0
 * @date 2021年07月28日
 */
@Slf4j
public class RedisLeakyLimiterImpl implements RedisLimiter {

    private static final DefaultRedisScript<Long> LEAKY_SCRIPT;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Limit.Strategy strategy() {
        return Limit.Strategy.LEAKY;
    }

    @Override
    public boolean check(RedisLimit limit) {
        if (limit.getCount().compareTo(0) == 0) {
            // 数据包数量等于0，返回成功
            return true;
        }
        if (limit.getCount().compareTo(limit.getCapacity()) > 0) {
            // 数据包数量大于漏桶容量，返回失败
            return false;
        }
        Long waitTime = stringRedisTemplate.execute(LEAKY_SCRIPT, ImmutableList.of(limit.getKey()),
                limit.getPeriod(), limit.getRate(), limit.getCapacity(), limit.getCount(),
                limit.getTimeout(), System.currentTimeMillis());
        if (waitTime == null) {
            log.error("leaky limiter error, params: {}", limit);
            return false;
        }
        if (waitTime.compareTo(0L) < 0 || waitTime.compareTo(limit.getTimeout()) > 0) {
            return false;
        }
        if (waitTime.compareTo(0L) > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ignore) {
            }
        }
        return true;
    }

    static {
        LEAKY_SCRIPT = new DefaultRedisScript<>();
        LEAKY_SCRIPT.setLocation(new ClassPathResource("/limiter/leaky.lua"));
        LEAKY_SCRIPT.setResultType(Long.class);
    }
}
