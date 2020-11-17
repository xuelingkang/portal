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

package com.xzixi.framework.boot.redis.service.impl;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xuelingkang
 * @date 2020-11-08
 */
public class RedisPipelineService {

    @Getter
    @Setter
    private int defaultBatchSize;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisSerializer<String> stringRedisSerializer;
    @Autowired
    private RedisSerializer<Object> objectRedisSerializer;

    /**
     * 使用pipeline批量执行redis查询
     * <p>每100个执行一次
     *
     * @param keys key有序集合
     * @return values，和入ids顺序和长度，查询不到的用null占位
     */
    public <T> List<T> get(Collection<String> keys) {
        return get(keys, defaultBatchSize);
    }

    /**
     * 使用pipeline批量执行redis查询
     *
     * @param keys key有序集合
     * @param batchSize 每批次执行多少个
     * @return values，和入ids顺序和长度，查询不到的用null占位
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> get(Collection<String> keys, int batchSize) {
        return Lists.partition(new ArrayList<>(keys), batchSize).stream()
                .map(partition -> (List<T>) redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                    for (String key : partition) {
                        byte[] keyBytes = stringRedisSerializer.serialize(key);
                        assert keyBytes != null;
                        connection.get(keyBytes);
                    }
                    return null;
                }))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * 使用pipeline批量执行redis保存
     * <p>每100个执行一次
     *
     * @param pairs 键值对集合
     * @param expire 过期时间
     * @param timeUnit 时间单位
     * @param setOption {@link RedisStringCommands.SetOption}
     * @param <P> Pair&lt;String, ?>
     */
    public <P extends Pair<String, ?>> void set(Collection<P> pairs, long expire, TimeUnit timeUnit, RedisStringCommands.SetOption setOption) {
        set(pairs, expire, timeUnit, setOption, defaultBatchSize);
    }

    /**
     * 使用pipeline批量执行redis保存
     *
     * @param pairs 键值对集合
     * @param expire 过期时间
     * @param timeUnit 时间单位
     * @param setOption {@link RedisStringCommands.SetOption}
     * @param batchSize 每批次执行多少个
     * @param <P> Pair&lt;String, ?>
     */
    public <P extends Pair<String, ?>> void set(Collection<P> pairs, long expire, TimeUnit timeUnit, RedisStringCommands.SetOption setOption, int batchSize) {
        Lists.partition(new ArrayList<>(pairs), batchSize)
                .forEach(partition -> redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                    for (P pair : partition) {
                        byte[] keyBytes = stringRedisSerializer.serialize(pair.getKey());
                        byte[] valueBytes = objectRedisSerializer.serialize(pair.getValue());
                        Expiration expiration = Expiration.from(expire, timeUnit);
                        assert keyBytes != null;
                        assert valueBytes != null;
                        connection.set(keyBytes, valueBytes, expiration, setOption);
                    }
                    return null;
                }));
    }

    /**
     * 使用pipeline批量执行redis删除
     * <p>每100个执行一次
     *
     * @param keys key集合
     */
    public void del(Collection<String> keys) {
        del(keys, defaultBatchSize);
    }

    /**
     * 使用pipeline批量执行redis删除
     *
     * @param keys key集合
     * @param batchSize 每批次执行多少个
     */
    public void del(Collection<String> keys, int batchSize) {
        Lists.partition(new ArrayList<>(keys), batchSize)
                .forEach(partition -> redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                    for (String key : partition) {
                        byte[] keyBytes = stringRedisSerializer.serialize(key);
                        assert keyBytes != null;
                        connection.del(keyBytes);
                    }
                    return null;
                }));
    }
}
