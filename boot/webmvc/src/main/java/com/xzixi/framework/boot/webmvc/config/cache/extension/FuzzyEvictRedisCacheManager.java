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
