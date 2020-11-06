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

package com.xzixi.framework.boot.cache.config;

import com.xzixi.framework.boot.cache.extension.FuzzyEvictRedisCacheManager;
import com.xzixi.framework.boot.cache.generator.*;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.LinkedHashMap;

import static com.xzixi.framework.boot.cache.RedisCacheConstant.KEYS_SEPARATOR;
import static com.xzixi.framework.boot.cache.RedisCacheConstant.REGEX_KEY_PREFIX;

/**
 * redis缓存配置
 *
 * @author 薛凌康
 */
@EnableCaching(mode = AdviceMode.ASPECTJ)
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            CacheProperties cacheProperties,
            RedisSerializer<String> stringRedisSerializer,
            RedisSerializer<Object> objectRedisSerializer) {
        // 配置序列化
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(objectRedisSerializer))
                .entryTtl(redisProperties.getTimeToLive());
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        return new FuzzyEvictRedisCacheManager(redisCacheWriter, config, new LinkedHashMap<>(),
            true, KEYS_SEPARATOR, REGEX_KEY_PREFIX);
    }

    /**
     * 根据id生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByIdKeyGenerator() {
        return new DefaultEvictByIdKeyGenerator();
    }

    /**
     * 根据id集合生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByIdsKeyGenerator() {
        return new DefaultEvictByIdsKeyGenerator();
    }

    /**
     * 根据entity的id生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByEntityKeyGenerator() {
        return new DefaultEvictByEntityKeyGenerator();
    }

    /**
     * 根据entity集合的元素id生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByEntitiesKeyGenerator() {
        return new DefaultEvictByEntitiesKeyGenerator();
    }

    /**
     * 根据id或id集合数组生成key
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultBaseKeyGenerator() {
        return new DefaultBaseKeyGenerator();
    }

    /**
     * 使用其他非正式参数生成key
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultCasualKeyGenerator() {
        return new DefaultCasualKeyGenerator();
    }
}
