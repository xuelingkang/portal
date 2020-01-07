package com.xzixi.self.portal.webapp.config.redis;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.Arrays;

import static com.xzixi.self.portal.webapp.constant.RedisConstant.KEYS_SEPARATOR;
import static com.xzixi.self.portal.webapp.constant.RedisConstant.REGEX_KEY_PREFIX;

/**
 * evict支持删除模糊匹配key
 *
 * @author 薛凌康
 */
public class FuzzyEvictRedisCache extends RedisCache {

    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final ConversionService conversionService;

    protected FuzzyEvictRedisCache(String name,
                                   RedisCacheWriter cacheWriter,
                                   RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.conversionService = cacheConfig.getConversionService();
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
            String[] keys = ((String) key).split(KEYS_SEPARATOR);
            Arrays.stream(keys).forEach(keyItem -> {
                // 根据前缀验证是否模糊key
                if (keyItem.startsWith(REGEX_KEY_PREFIX)) {
                    // 调用cacheWriter的clean方法清除匹配的缓存
                    String shortKey = keyItem.substring(REGEX_KEY_PREFIX.length());
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
