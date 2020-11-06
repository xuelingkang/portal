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

package com.xzixi.framework.boot.cache.extension;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.Arrays;

/**
 * evict支持删除模糊匹配key
 *
 * @author 薛凌康
 */
public class FuzzyEvictRedisCache extends RedisCache {

    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final ConversionService conversionService;
    private final String keysSeparator;
    private final String regexKeyPrefix;

    protected FuzzyEvictRedisCache(String name,
                                   RedisCacheWriter cacheWriter,
                                   RedisCacheConfiguration cacheConfig,
                                   String keysSeparator,
                                   String regexKeyPrefix) {
        super(name, cacheWriter, cacheConfig);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.conversionService = cacheConfig.getConversionService();
        this.keysSeparator = keysSeparator;
        this.regexKeyPrefix = regexKeyPrefix;
    }

    /**
     * 重写evict，支持模糊匹配
     *
     * @param key the key whose mapping is to be removed from the cache
     * @see RedisCacheWriter#clean(String, byte[])
     */
    @Override
    public void evict(Object key) {
        if (key instanceof String) {
            String[] keys = ((String) key).split(keysSeparator);
            Arrays.stream(keys).forEach(keyItem -> {
                // 根据前缀验证是否模糊key
                if (keyItem.startsWith(regexKeyPrefix)) {
                    // 调用cacheWriter的clean方法清除匹配的缓存
                    String shortKey = keyItem.substring(regexKeyPrefix.length());
                    byte[] pattern = conversionService.convert(createCacheKey(shortKey), byte[].class);
                    if (pattern != null) {
                        cacheWriter.clean(name, pattern);
                    }
                } else {
                    super.evict(keyItem);
                }
            });
        } else {
            super.evict(key);
        }
    }
}
