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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * RedisLimitService
 * 固定窗口限流
 *
 * @author xuelingkang
 * @version 1.0
 * @date 2021年07月27日
 */
public class RedisCounterLimiterImpl implements RedisLimiter {

    private static final DefaultRedisScript<Integer> COUNTER_SCRIPT;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Limit.Strategy strategy() {
        return Limit.Strategy.COUNTER;
    }

    @Override
    public boolean check(RedisLimit limit) {
        Integer count = stringRedisTemplate.execute(COUNTER_SCRIPT, ImmutableList.of(limit.getKey()),
                limit.getPeriod(), limit.getRate(), limit.getCount());
        return count != null && count <= limit.getRate();
    }

    static {
        COUNTER_SCRIPT = new DefaultRedisScript<>();
        COUNTER_SCRIPT.setLocation(new ClassPathResource("/limiter/counter.lua"));
        COUNTER_SCRIPT.setResultType(Integer.class);
    }
}
