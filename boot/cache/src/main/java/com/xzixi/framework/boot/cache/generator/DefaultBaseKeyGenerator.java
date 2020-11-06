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

import com.xzixi.framework.boot.core.exception.ProjectException;
import com.xzixi.framework.boot.core.util.TypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xzixi.framework.boot.cache.RedisCacheConstant.*;

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
            if (TypeUtils.isArrayType(clazz)) {
                list.addAll(toList(param));
            } else if (TypeUtils.isCollectionType(clazz)) {
                list.addAll(toList(((Collection<?>) param).toArray()));
            } else if (TypeUtils.isSimpleValueType(clazz)) {
                list.add(param);
            } else {
                throw new ProjectException("无法处理数组、集合、基本类型以外的参数");
            }
        }
        return list;
    }
}
