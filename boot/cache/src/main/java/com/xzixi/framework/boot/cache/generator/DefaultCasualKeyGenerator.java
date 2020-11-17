/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.cache.generator;

import com.alibaba.fastjson.JSON;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.boot.core.util.TypeUtils;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 使用其他非正式参数生成key
 *
 * @author 薛凌康
 */
@Data
public class DefaultCasualKeyGenerator implements KeyGenerator {

    private static final String SPACE_REG = "\\s+";

    private String keySeparator;
    private String paramSeparator;
    private String emptyParam;
    private String nullParam;;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() +
                keySeparator +
                method.getName() +
                keySeparator +
                writeParams(params);
    }

    private String writeParams(Object... params) {
        if (params.length > 0) {
            return StringUtils.join(Arrays.stream(params).map(param -> {
                if (param == null) {
                    return nullParam;
                }
                if (TypeUtils.isSimpleValueType(param.getClass())) {
                    return param;
                }
                // Pagination类型的参数按照current_size_orders的格式生成key
                if (Pagination.class.isAssignableFrom(param.getClass())) {
                    Pagination<?> page = (Pagination<?>) param;
                    long current = page.getCurrent();
                    long size = page.getSize();
                    String[] orders = page.getOrders();
                    String orderInfo;
                    if (orders != null && ArrayUtils.isNotEmpty(orders)) {
                        orderInfo = StringUtils.join(
                            Arrays.stream(orders).map(order -> order.replaceAll(SPACE_REG, paramSeparator))
                                .collect(Collectors.toList()),
                                paramSeparator);
                    } else {
                        orderInfo = "";
                    }
                    return String.format("%s" + paramSeparator + "%s" + paramSeparator + "%s", current, size, orderInfo);
                }
                return DigestUtils.md5DigestAsHex(toJson(param).getBytes(StandardCharsets.UTF_8));
            }).collect(Collectors.toList()), paramSeparator);
        }
        return emptyParam;
    }

    private String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
