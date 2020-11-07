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

package com.xzixi.framework.boot.redis.model;

import com.xzixi.framework.boot.core.exception.LockAcquireException;
import com.xzixi.framework.boot.core.exception.LockReleaseException;
import com.xzixi.framework.boot.core.exception.ProjectException;
import com.xzixi.framework.boot.core.model.ILock;
import com.xzixi.framework.boot.core.util.Utils;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 使用redis实现可重入的分布式锁
 *
 * @author xuelingkang
 * @date 2020-11-07
 */
@Data
public class RedisLock implements ILock {

    /**
     * 轮询等待时间，毫秒
     */
    private static final long SLEEP_TIME = 100;

    /**
     * 获取锁脚本
     */
    private static final RedisScript<Boolean> ACQUIRE_SCRIPT;

    /**
     * 释放锁脚本
     */
    private static final RedisScript<Long> RELEASE_SCRIPT;

    private final String name;
    private final String value;
    private final long waitTimeout;
    private final long leaseTimeout;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void acquire() throws LockAcquireException {
        // 等待时间
        long waitTime = 0;

        while (true) {
            boolean result = tryAcquire();

            if (result) {
                // 加锁成功
                return;
            }

            if (waitTime < this.waitTimeout) {
                // 加锁失败，等待时间 < 等待超时时间
                Utils.safeSleep(SLEEP_TIME);
                waitTime += SLEEP_TIME;
                continue;
            }

            // 加锁失败
            throw new LockAcquireException(String.format("attempt to acquire lock failure, name[%s] value[%s]", this.name, this.value));
        }
    }

    @Override
    public void release() throws LockReleaseException {
        Long result = stringRedisTemplate.execute(RELEASE_SCRIPT, Collections.singletonList(this.name), this.value);
        /*
         * (result == 1) 解锁成功
         * (result == 0) 未解锁，重入次数减1
         * (result == null) 锁已经不存在，超时或其他原因
         */
        if (result == null) {
            throw new LockReleaseException(String.format("attempt to release lock failure, name[%s] value[%s]", this.name, this.value));
        }
    }

    @Override
    public void safeRelease() {
        stringRedisTemplate.execute(RELEASE_SCRIPT, Collections.singletonList(this.name), this.value);
    }

    /**
     * 尝试获取锁
     *
     * @return Boolean
     */
    private Boolean tryAcquire() {
        return stringRedisTemplate.execute(ACQUIRE_SCRIPT, Collections.singletonList(this.name), String.valueOf(this.leaseTimeout), this.value);
    }

    static {
        try {
            ClassPathResource resource = new ClassPathResource("/acquire.lua");
            InputStream in = resource.getInputStream();
            ACQUIRE_SCRIPT = new DefaultRedisScript<>(IOUtils.toString(in, StandardCharsets.UTF_8), Boolean.class);
        } catch (IOException e) {
            throw new ProjectException("read acquire redis lock script failure!");
        }

        try {
            ClassPathResource resource = new ClassPathResource("/release.lua");
            InputStream in = resource.getInputStream();
            RELEASE_SCRIPT = new DefaultRedisScript<>(IOUtils.toString(in, StandardCharsets.UTF_8), Long.class);
        } catch (IOException e) {
            throw new ProjectException("read release redis lock script failure!");
        }
    }
}