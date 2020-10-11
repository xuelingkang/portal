package com.xzixi.self.portal.framework.webmvc.config.cache.genetator;

import com.xzixi.self.portal.framework.webmvc.model.BaseModel;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

import static com.xzixi.self.portal.framework.webmvc.config.cache.RedisConstant.*;

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
                id;
    }
}
