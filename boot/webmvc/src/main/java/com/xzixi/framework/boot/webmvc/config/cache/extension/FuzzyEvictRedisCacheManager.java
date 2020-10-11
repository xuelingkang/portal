package com.xzixi.framework.boot.webmvc.config.cache.extension;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * evict支持模糊匹配key
 *
 * @author 薛凌康
 */
public class FuzzyEvictRedisCacheManager extends RedisCacheManager {

    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfig;
    private final String keysSeparator;
    private final String regexKeyPrefix;

    /**
     * 重写createRedisCache，将RedisCache替换为FuzzyEvictRedisCache
     *
     * @param name must not be {@literal null}.
     * @param cacheConfig can be {@literal null}.
     * @return never {@literal null}.
     */
    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new FuzzyEvictRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfig, keysSeparator, regexKeyPrefix);
    }

    public FuzzyEvictRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       Map<String, RedisCacheConfiguration> initialCacheConfigurations,
                                       boolean allowInFlightCacheCreation,
                                       String keysSeparator,
                                       String regexKeyPrefix) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
        this.keysSeparator = keysSeparator;
        this.regexKeyPrefix = regexKeyPrefix;
    }
}
