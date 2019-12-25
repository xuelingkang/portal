package com.xzixi.self.portal.webapp.config.redis;

import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

import static com.xzixi.self.portal.webapp.framework.constant.RedisConstant.*;

/**
 * @author 薛凌康
 */
public class DefaultEvictByEntityKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Integer id = ((BaseModel) params[0]).getId();
        if (id == null) {
            return "";
        }
        return target.getClass().getName() +
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
                WILDCARD + id + WILDCARD;
    }
}