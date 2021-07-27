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

import com.xzixi.framework.boot.redis.annotation.Limit;
import com.xzixi.framework.boot.redis.model.RedisLimit;
import com.xzixi.framework.boot.redis.service.RedisLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisLeakyLimiterImpl
 * description
 *
 * @author xuelingkang
 * @version 1.0
 * @date 2021年07月28日
 */
public class RedisLeakyLimiterImpl implements RedisLimiter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Limit.Strategy strategy() {
        return Limit.Strategy.LEAKY;
    }

    @Override
    public boolean check(RedisLimit limit) {
        return false;
    }
}
