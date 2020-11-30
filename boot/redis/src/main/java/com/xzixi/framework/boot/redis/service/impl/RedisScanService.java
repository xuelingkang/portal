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

package com.xzixi.framework.boot.redis.service.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author xuelingkang
 * @date 2020-11-30
 */
@Slf4j
public class RedisScanService {

    @Getter
    @Setter
    private int defaultBatchSize;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisSerializer<String> stringRedisSerializer;

    /**
     * 按照pattern扫描key，使用默认size
     *
     * @param pattern pattern
     * @return 匹配的key
     */
    public List<String> scan(String pattern) {
        return scan(pattern, defaultBatchSize);
    }

    /**
     * 按照pattern扫描key
     *
     * @param pattern pattern
     * @param size 每次扫描多少个
     * @return 匹配的key
     */
    public List<String> scan(String pattern, int size) {
        List<String> keys = new ArrayList<>();
        scan(pattern, keys::add, size);
        return keys;
    }

    /**
     * 扫描匹配的key并触发回调，使用默认size
     *
     * @param pattern pattern
     * @param action 回调
     */
    public void scan(String pattern, Consumer<String> action) {
        scan(pattern, action, defaultBatchSize);
    }

    /**
     * 扫描匹配的key并触发回调
     *
     * @param pattern pattern
     * @param action 回调
     * @param size 每次扫描多少个
     */
    public void scan(String pattern, Consumer<String> action, int size) {
        ScanOptions options = ScanOptions.scanOptions().count(size).match(pattern).build();
        Cursor<String> cursor = redisTemplate.executeWithStickyConnection(redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(options), stringRedisSerializer::deserialize));

        if (cursor == null) {
            return;
        }

        while (cursor.hasNext()) {
            action.accept(cursor.next());
        }

        try {
            cursor.close();
        } catch (IOException e) {
            log.error(String.format("exception occurs when close the cursor: %s", e.getMessage()), e);
        }
    }
}
