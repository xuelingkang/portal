package com.xzixi.self.portal.webapp.config.redis;

import com.xzixi.self.portal.webapp.framework.exception.ProjectException;
import com.xzixi.self.portal.webapp.framework.util.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xzixi.self.portal.webapp.framework.constant.RedisConstant.*;

/**
 * 根据id或id集合数组生成key
 *
 * @author 薛凌康
 */
public class DefaultBaseKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getName() +
                KEY_SEPARATOR +
                method.getName() +
                KEY_SEPARATOR +
                writeParams(params);
    }

    private String writeParams(Object... params) {
        List<Object> objects = toList(params);
        if (objects.size() > 0) {
            return StringUtils.join(objects, PARAM_SEPARATOR);
        }
        return EMPTY_PARAM;
    }

    private List<Object> toList(Object... params) {
        List<Object> list = new ArrayList<>();
        for (Object param : params) {
            if (param == null) {
                continue;
            }
            Class<?> clazz = param.getClass();
            if (TypeUtil.isArrayType(clazz)) {
                list.addAll(toList(param));
            } else if (TypeUtil.isCollectionType(clazz)) {
                list.addAll(toList(((Collection<?>) param).toArray()));
            } else if (TypeUtil.isSimpleValueType(clazz)) {
                list.add(param);
            } else {
                throw new ProjectException("无法处理数组、集合、基本类型以外的参数");
            }
        }
        return list;
    }
}
