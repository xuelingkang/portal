package com.xzixi.self.portal.webapp.config.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.webapp.framework.constant.RedisConstant.*;

/**
 * 根据id集合生成删除缓存key
 *
 * @author 薛凌康
 */
public class DefaultEvictByIdsKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0) {
            return WILDCARD;
        }
        return StringUtils.join(
                ((Collection<?>) params[0]).stream().map(id ->
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
                                WILDCARD + id + WILDCARD)
                        .collect(Collectors.toList()),
                KEYS_SEPARATOR);
    }
}
