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
import com.xzixi.framework.boot.core.exception.ProjectException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * RedisLimitService
 * 限流检查
 *
 * @author xuelingkang
 * @version 1.0.0
 * @date 2021年07月27日
 */
public class RedisLimitService {

    private static final RedisScript<Integer> LIMIT_SCRIPT;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 限流检查
     *
     * @param limitKey 请求标识
     * @param limitPeriod 限流时间段
     * @param limitCount 时间段内最多s请求个数
     * @return {@code true}-检查通过 {@code false}-检查不通过
     */
    public boolean check(String limitKey, int limitPeriod, int limitCount) {
        Integer count = stringRedisTemplate.execute(LIMIT_SCRIPT, ImmutableList.of(limitKey), limitPeriod, limitCount);
        return count != null && count <= limitCount;
    }

    static {
        try {
            ClassPathResource resource = new ClassPathResource("/limit.lua");
            InputStream in = resource.getInputStream();
            LIMIT_SCRIPT = new DefaultRedisScript<>(IOUtils.toString(in, StandardCharsets.UTF_8), Integer.class);
        } catch (IOException e) {
            throw new ProjectException("read redis limit script failure!");
        }
    }
}
