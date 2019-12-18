package com.xzixi.self.portal.webapp.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzixi.self.portal.webapp.framework.util.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.xzixi.self.portal.webapp.framework.constant.RedisConstant.*;

/**
 * 使用其他非正式参数生成key
 *
 * @author 薛凌康
 */
public class DefaultCasualKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getName() +
                KEY_SEPARATOR +
                method.getName() +
                KEY_SEPARATOR +
                writeParams(params);
    }

    private String writeParams(Object... params) {
        if (params.length > 0) {
            return StringUtils.join(Arrays.stream(params).map(param -> {
                if (TypeUtil.isSimpleValueType(param.getClass())) {
                    return param;
                } else {
                    return toJson(param).hashCode();
                }
            }).toArray(), PARAM_SEPARATOR);
        }
        return EMPTY_PARAM;
    }

    private String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }
}
