package com.xzixi.self.portal.webapp.config.redis;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Set;

import static com.xzixi.self.portal.webapp.base.constant.RedisConstant.KEYS_SEPARATOR;
import static com.xzixi.self.portal.webapp.base.constant.RedisConstant.REGEX_KEY_PREFIX;

/**
 * evict支持模糊匹配key
 *
 * @author 薛凌康
 */
public class FuzzyEvictRedisCache extends RedisCache {

    private RedisTemplate<String, Object> redisTemplate;

    protected FuzzyEvictRedisCache(String name, RedisCacheWriter cacheWriter,
                                   RedisCacheConfiguration cacheConfig,
                                   RedisTemplate<String, Object> redisTemplate) {
        super(name, cacheWriter, cacheConfig);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 重写evict，支持模糊匹配
     *
     * @param key the key whose mapping is to be removed from the cache
     */
    @Override
    public void evict(Object key) {
        if (key instanceof String) {
            String[] keys = ((String) key).split(KEYS_SEPARATOR);
            Arrays.stream(keys).forEach(keyStr -> {
                // 根据前缀验证是否模糊key
                if (keyStr.startsWith(REGEX_KEY_PREFIX)) {
                    keyStr = keyStr.substring(REGEX_KEY_PREFIX.length());
                    // 查询出匹配的key
                    Set<String> keysSet = redisTemplate.keys(keyStr);
                    if (CollectionUtils.isNotEmpty(keysSet)) {
                        // 精确删除
                        keysSet.forEach(super::evict);
                    }
                } else {
                    super.evict(keyStr);
                }
            });
        } else {
            super.evict(key);
        }
    }
}
