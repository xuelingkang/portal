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

package com.xzixi.framework.boot.redis.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.xzixi.framework.boot.redis.service.impl.RedisLockService;
import com.xzixi.framework.boot.redis.service.impl.RedisPipelineService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.UUID;

/**
 * @author xuelingkang
 * @date 2020-11-03
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer<String> stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisSerializer<Object> objectRedisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory,
                                                       RedisSerializer<String> stringRedisSerializer,
                                                       RedisSerializer<Object> objectRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // key序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value序列化
        redisTemplate.setValueSerializer(objectRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(RedisPipelineService.class)
    public RedisPipelineService redisPipelineService() {
        RedisPipelineService redisPipelineService = new RedisPipelineService();
        redisPipelineService.setDefaultBatchSize(100);
        return redisPipelineService;
    }

    @Bean
    @ConditionalOnMissingBean(RedisLockService.class)
    public RedisLockService redisLockService() {
        RedisLockService redisLockService = new RedisLockService();
        redisLockService.setDefaultWaitTimeout(30000L);
        redisLockService.setDefaultLeaseTimeout(10000L);
        redisLockService.setDefaultValueGenerator(() -> UUID.randomUUID().toString());
        return new RedisLockService();
    }
}
