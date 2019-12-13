package com.xzixi.self.portal.webapp.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzixi.self.portal.webapp.model.BaseModel;
import com.xzixi.self.portal.webapp.redis.FuzzyEvictRedisCacheManager;
import com.xzixi.self.portal.webapp.util.SerializeUtil;
import com.xzixi.self.portal.webapp.util.TypeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.webapp.constant.RedisConstant.KEYS_SEPARATOR;
import static com.xzixi.self.portal.webapp.constant.RedisConstant.REGEX_KEY_PREFIX;

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
        return new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object obj) throws SerializationException {
                return SerializeUtil.serialize(obj);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return SerializeUtil.deserialize(bytes);
            }
        };
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

    private static final String KEY_SEPARATOR = ":";
    private static final String GET_BY_ID_METHOD_NAME = "getById";
    private static final String LIST_BY_IDS_METHOD_NAME = "listByIds";

    /**
     * 根据id删除缓存
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByIdKeyGenerator() {
        return (target, method, params) ->
                target.getClass().getName() +
                        KEY_SEPARATOR +
                        GET_BY_ID_METHOD_NAME +
                        KEY_SEPARATOR +
                        params[0] +

                        KEYS_SEPARATOR +

                        REGEX_KEY_PREFIX +
                        target.getClass().getName() +
                        KEY_SEPARATOR +
                        LIST_BY_IDS_METHOD_NAME +
                        KEY_SEPARATOR +
                        "*" + params[0] + "*";
    }

    /**
     * 根据id集合删除缓存
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByIdsKeyGenerator() {
        return (target, method, params) ->
                StringUtils.join(((Collection<?>) params[0]).stream().map(id ->
                                target.getClass().getName() +
                                        KEY_SEPARATOR +
                                        GET_BY_ID_METHOD_NAME +
                                        KEY_SEPARATOR +
                                        id +

                                        KEYS_SEPARATOR +

                                        REGEX_KEY_PREFIX +
                                        target.getClass().getName() +
                                        KEY_SEPARATOR +
                                        LIST_BY_IDS_METHOD_NAME +
                                        KEY_SEPARATOR +
                                        "*" + id + "*").collect(Collectors.toList()),
                        KEYS_SEPARATOR);
    }

    /**
     * 根据entity的id删除缓存
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultEvictByEntityKeyGenerator() {
        return (target, method, params) -> {
            Integer id = ((BaseModel) params[0]).getId();
            if (id == null) {
                return "";
            }
            return target.getClass().getName() +
                    KEY_SEPARATOR +
                    GET_BY_ID_METHOD_NAME +
                    KEY_SEPARATOR +
                    ((BaseModel) params[0]).getId() +

                    KEYS_SEPARATOR +

                    REGEX_KEY_PREFIX +
                    target.getClass().getName() +
                    KEY_SEPARATOR +
                    LIST_BY_IDS_METHOD_NAME +
                    KEY_SEPARATOR +
                    "*" + ((BaseModel) params[0]).getId() + "*";
        };
    }

    /**
     * 根据entity集合的元素id删除缓存
     *
     * @return KeyGenerator
     */
    @Bean
    @SuppressWarnings("unchecked")
    public KeyGenerator defaultEvictByEntitiesKeyGenerator() {
        return (target, method, params) -> {
            Collection<? extends BaseModel> collection = ((Collection<? extends BaseModel>) params[0])
                    .stream().filter(entity -> entity.getId() != null).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collection)) {
                return "";
            }
            return StringUtils.join(collection.stream()
                            .map(entity ->
                            target.getClass().getName() +
                                    KEY_SEPARATOR +
                                    GET_BY_ID_METHOD_NAME +
                                    KEY_SEPARATOR +
                                    entity.getId() +

                                    KEYS_SEPARATOR +

                                    REGEX_KEY_PREFIX +
                                    target.getClass().getName() +
                                    KEY_SEPARATOR +
                                    LIST_BY_IDS_METHOD_NAME +
                                    KEY_SEPARATOR +
                                    "*" + entity.getId() + "*").collect(Collectors.toList()),
                    KEYS_SEPARATOR);
        };
    }

    private static final String PARAM_SEPARATOR = "_";
    private static final String EMPTY_PARAM = "emptyParam";

    private String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据id或id集合数组创建缓存
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultBaseKeyGenerator() {
        return new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getName() +
                        KEY_SEPARATOR +
                        method.getName() +
                        KEY_SEPARATOR +
                        writeParams(params);
            }

            private List<Object> toList(Object... params) {
                List<Object> list = new ArrayList<>();
                if (params.length > 0) {
                    list.addAll(Arrays.stream(params).map(param -> {
                        if (param == null) {
                            return "null";
                        }
                        Class<?> clazz = param.getClass();
                        if (TypeUtil.isArrayType(clazz)) {
                            return toList(param);
                        }
                        if (TypeUtil.isCollectionType(clazz)) {
                            return toList(((Collection<?>) param).toArray());
                        }
                        if (TypeUtil.isSimpleValueType(clazz)) {
                            return String.valueOf(param);
                        }
                        return String.valueOf(toJson(param).hashCode());
                    }).collect(Collectors.toList()));
                }
                return list;
            }

            private String writeParams(Object... params) {
                List<Object> objects = toList(params);
                StringBuilder builder = new StringBuilder();
                if (objects.size() > 0) {
                    builder.append(StringUtils.join(objects, PARAM_SEPARATOR));
                } else {
                    builder.append(EMPTY_PARAM);
                }
                return builder.toString();
            }
        };
    }

    /**
     * 使用其他非正式参数创建缓存
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator defaultCasualKeyGenerator() {
        return new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getName() +
                        KEY_SEPARATOR +
                        method.getName() +
                        KEY_SEPARATOR +
                        writeParams(params);
            }

            private String writeParams(Object... params) {
                StringBuilder builder = new StringBuilder();
                if (params.length > 0) {
                    // 参数值
                    Arrays.stream(params).forEach(param -> {
                        if (TypeUtil.isSimpleValueType(param.getClass())) {
                            builder.append(param);
                        } else {
                            builder.append(toJson(param).hashCode());
                        }
                    });
                } else {
                    builder.append(EMPTY_PARAM);
                }
                return builder.toString();
            }
        };
    }
}
