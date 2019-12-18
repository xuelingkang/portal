package com.xzixi.self.portal.webapp.config.redis;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

import static com.xzixi.self.portal.webapp.framework.constant.RedisConstant.*;

/**
 * 根据id生成删除缓存key
 *
 * @author 薛凌康
 */
public class DefaultEvictByIdKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getName() +
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
}
