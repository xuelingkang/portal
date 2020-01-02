package com.xzixi.self.portal.webapp.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.xzixi.self.portal.webapp.config.redis.*;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.LinkedHashMap;

/**
 * redis缓存配置
 *
 * @author 薛凌康
 */
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisCacheConfig {

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
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            CacheProperties cacheProperties,
            RedisSerializer<String> stringRedisSerializer,
            RedisSerializer<Object> objectRedisSerializer,
            RedisTemplate<String, Object> redisTemplate) {
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

        return new FuzzyEvictRedisCacheManager(redisCacheWriter, config,
                new LinkedHashMap<>(), true, redisTemplate);
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
