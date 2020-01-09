package com.xzixi.self.portal.webapp.config.redis;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
                if (param == null) {
                    return NULL_PARAM;
                }
                if (TypeUtil.isSimpleValueType(param.getClass())) {
                    return param;
                }
                /*
                 * QueryWrapper类型参数如果调用了in等链式方法，
                 * 如queryWrapper.in("id", ids)
                 * 虽然每次设置相同的参数，最终生成的缓存key也相同，但是无法命中缓存，
                 * 暂时使用这种方式可以解决，以后再调查真相
                 */
                if (QueryWrapper.class.isAssignableFrom(param.getClass())) {
                    QueryWrapper<?> queryWrapper = (QueryWrapper<?>) param;
                    String info = String.format("entity=%s,select=%s,sqlSegment=%s,nameValue=%s",
                            toJson(queryWrapper.getEntity()), queryWrapper.getSqlSelect(),
                            queryWrapper.getSqlSegment(), queryWrapper.getParamNameValuePairs());
                    return DigestUtils.md5DigestAsHex(info.getBytes(StandardCharsets.UTF_8));
                }
                return DigestUtils.md5DigestAsHex(toJson(param).getBytes(StandardCharsets.UTF_8));
            }).toArray(), PARAM_SEPARATOR);
        }
        return EMPTY_PARAM;
    }

    private String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
