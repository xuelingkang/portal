package com.xzixi.self.portal.framework.config.cache.genetator;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

import static com.xzixi.self.portal.framework.constant.RedisConstant.*;

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
                params[0];
    }
}
