package com.xzixi.framework.boot.webmvc.config.cache.generator;

import com.xzixi.framework.boot.webmvc.model.BaseModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.xzixi.framework.boot.webmvc.config.cache.RedisConstant.*;

/**
 * 根据entity集合的元素id生成删除缓存key
 *
 * @author 薛凌康
 */
public class DefaultEvictByEntitiesKeyGenerator implements KeyGenerator {

    @Override
    @SuppressWarnings("unchecked")
    public Object generate(Object target, Method method, Object... params) {
        Collection<? extends BaseModel> collection = ((Collection<? extends BaseModel>) params[0])
                .stream().filter(entity -> entity.getId() != null).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        return StringUtils.join(
                collection.stream().map(entity ->
                        target.getClass().getName() +
                                KEY_SEPARATOR +
                                GET_BY_ID_METHOD_NAME +
                                KEY_SEPARATOR +
                                entity.getId())
                        .collect(Collectors.toList()),
                KEYS_SEPARATOR);
    }
}
