package com.xzixi.self.portal.webapp.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzixi.self.portal.webapp.util.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.xzixi.self.portal.webapp.constant.RedisConstant.*;

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
                    return DigestUtils.md5DigestAsHex(toJson(param).getBytes(StandardCharsets.UTF_8));
                }
            }).toArray(), PARAM_SEPARATOR);
        }
        return EMPTY_PARAM;
    }

    private String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }
}
