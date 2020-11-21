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

package com.xzixi.framework.boot.cache.config;

import com.xzixi.framework.boot.cache.extension.FuzzyEvictRedisCacheManager;
import com.xzixi.framework.boot.cache.generator.*;
import com.xzixi.framework.boot.redis.config.RedisConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
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

/**
 * redis缓存配置
 *
 * @author 薛凌康
 */
@EnableCaching(mode = AdviceMode.ASPECTJ)
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@AutoConfigureAfter(RedisConfig.class)
public class RedisCacheConfig {

    /**
     * 正则key前缀
     */
    private static final String REGEX_KEY_PREFIX = "regex=";
    /**
     * 多个key之间的分隔符
     */
    private static final String KEYS_SEPARATOR = ",";
    /**
     * key层次分隔符
     */
    private static final String KEY_SEPARATOR = ":";
    /**
     * getById方法名
     */
    private static final String GET_BY_ID_METHOD_NAME = "getById";
    /**
     * 值为null的参数
     */
    private static final String NULL_PARAM = "null";
    /**
     * 无参方法key
     */
    private static final String EMPTY_PARAM = "emptyParam";
    /**
     * 参数分隔符
     */
    private static final String PARAM_SEPARATOR = "_";
    /**
     * 正则key通配符
     */
    private static final String WILDCARD = "*";

    @Bean
    @ConditionalOnMissingBean
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
    @ConditionalOnMissingBean(name = "defaultEvictByIdKeyGenerator")
    public KeyGenerator defaultEvictByIdKeyGenerator() {
        DefaultEvictByIdKeyGenerator keyGenerator = new DefaultEvictByIdKeyGenerator();
        keyGenerator.setKeySeparator(KEY_SEPARATOR);
        keyGenerator.setGetByIdMethodName(GET_BY_ID_METHOD_NAME);
        return keyGenerator;
    }

    /**
     * 根据id集合生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultEvictByIdsKeyGenerator")
    public KeyGenerator defaultEvictByIdsKeyGenerator() {
        DefaultEvictByIdsKeyGenerator keyGenerator = new DefaultEvictByIdsKeyGenerator();
        keyGenerator.setKeySeparator(KEY_SEPARATOR);
        keyGenerator.setKeysSeparator(KEYS_SEPARATOR);
        keyGenerator.setGetByIdMethodName(GET_BY_ID_METHOD_NAME);
        keyGenerator.setWildcard(WILDCARD);
        return keyGenerator;
    }

    /**
     * 根据entity的id生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultEvictByEntityKeyGenerator")
    public KeyGenerator defaultEvictByEntityKeyGenerator() {
        DefaultEvictByEntityKeyGenerator keyGenerator = new DefaultEvictByEntityKeyGenerator();
        keyGenerator.setKeySeparator(KEY_SEPARATOR);
        keyGenerator.setGetByIdMethodName(GET_BY_ID_METHOD_NAME);
        return keyGenerator;
    }

    /**
     * 根据entity集合的元素id生成删除缓存key
     *
     * @return KeyGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultEvictByEntitiesKeyGenerator")
    public KeyGenerator defaultEvictByEntitiesKeyGenerator() {
        DefaultEvictByEntitiesKeyGenerator keyGenerator = new DefaultEvictByEntitiesKeyGenerator();
        keyGenerator.setKeySeparator(KEY_SEPARATOR);
        keyGenerator.setKeysSeparator(KEYS_SEPARATOR);
        keyGenerator.setGetByIdMethodName(GET_BY_ID_METHOD_NAME);
        return keyGenerator;
    }

    /**
     * 根据id或id集合数组生成key
     *
     * @return KeyGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultBaseKeyGenerator")
    public KeyGenerator defaultBaseKeyGenerator() {
        DefaultBaseKeyGenerator keyGenerator = new DefaultBaseKeyGenerator();
        keyGenerator.setKeySeparator(KEY_SEPARATOR);
        keyGenerator.setParamSeparator(PARAM_SEPARATOR);
        keyGenerator.setEmptyParam(EMPTY_PARAM);
        return keyGenerator;
    }

    /**
     * 使用其他非正式参数生成key
     *
     * @return KeyGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultCasualKeyGenerator")
    public KeyGenerator defaultCasualKeyGenerator() {
        DefaultCasualKeyGenerator keyGenerator = new DefaultCasualKeyGenerator();
        keyGenerator.setKeySeparator(KEY_SEPARATOR);
        keyGenerator.setEmptyParam(EMPTY_PARAM);
        keyGenerator.setNullParam(NULL_PARAM);
        keyGenerator.setParamSeparator(PARAM_SEPARATOR);
        return keyGenerator;
    }
}
