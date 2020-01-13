package com.xzixi.self.portal.webapp.config.redis;

import com.alibaba.fastjson.JSON;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.webapp.util.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.webapp.constant.RedisConstant.*;

/**
 * 使用其他非正式参数生成key
 *
 * @author 薛凌康
 */
public class DefaultCasualKeyGenerator implements KeyGenerator {

    private static final String PAGE_PARAM_TEMPLATE = "%s" + PARAM_SEPARATOR + "%s" + PARAM_SEPARATOR + "%s";
    private static final String SPACE_REG = "\\s+";

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
                 * IPage类型的参数按照current_size_orderItem的格式生成key
                 */
                if (Pagination.class.isAssignableFrom(param.getClass())) {
                    Pagination<?> page = (Pagination<?>) param;
                    long current = page.getCurrent();
                    long size = page.getSize();
                    String[] orders = page.getOrders();
                    String orderInfo = StringUtils.join(
                            Arrays.stream(orders).map(order -> order.replaceAll(SPACE_REG, PARAM_SEPARATOR)),
                            PARAM_SEPARATOR);
                    return String.format(PAGE_PARAM_TEMPLATE, current, size, orderInfo);
                }
                return DigestUtils.md5DigestAsHex(toJson(param).getBytes(StandardCharsets.UTF_8));
            }).collect(Collectors.toList()), PARAM_SEPARATOR);
        }
        return EMPTY_PARAM;
    }

    private String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
