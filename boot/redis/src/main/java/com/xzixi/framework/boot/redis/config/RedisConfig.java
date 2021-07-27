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
import com.xzixi.framework.boot.redis.aspect.RedisLimitAspect;
import com.xzixi.framework.boot.redis.service.RedisLimiter;
import com.xzixi.framework.boot.redis.service.impl.*;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
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
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(name = "stringRedisSerializer")
    public RedisSerializer<String> stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(name = "objectRedisSerializer")
    public RedisSerializer<Object> objectRedisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Bean
    @ConditionalOnMissingBean
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
    @ConditionalOnMissingBean
    public RedisPipelineService redisPipelineService() {
        RedisPipelineService redisPipelineService = new RedisPipelineService();
        redisPipelineService.setDefaultBatchSize(100);
        return redisPipelineService;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLockService redisLockService() {
        RedisLockService redisLockService = new RedisLockService();
        redisLockService.setDefaultWaitTimeout(30000L);
        redisLockService.setDefaultLeaseTimeout(10000L);
        redisLockService.setDefaultValueGenerator(() -> UUID.randomUUID().toString());
        return new RedisLockService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisScanService redisScanService() {
        RedisScanService redisScanService = new RedisScanService();
        redisScanService.setDefaultBatchSize(100);
        return redisScanService;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLimiter redisCounterLimiter() {
        return new RedisCounterLimiterImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLimiter redisTokenLimiter() {
        return new RedisTokenLimiterImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLimiter redisLeakyLimiter() {
        return new RedisLeakyLimiterImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisLimitAspect redisLimitAspect() {
        return new RedisLimitAspect();
    }
}
