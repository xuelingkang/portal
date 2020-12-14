/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.feign.component;

import com.alibaba.fastjson.JSON;
import com.xzixi.framework.boot.core.exception.ProjectException;
import com.xzixi.framework.boot.core.util.TypeUtils;
import feign.QueryMapEncoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * 自定义@SpringQueryMap参数的转换逻辑
 *
 * @author xuelingkang
 * @date 2020-11-19
 */
public class ObjectQueryMapEncoder implements QueryMapEncoder {

    private static final String LEFT = "[";
    private static final String RIGHT = "]";
    private static final String DOT = ".";
    private static final String EMPTY = "";

    @Override
    public Map<String, Object> encode(Object object) {
        List<Pair<String, Object>> pairs = parsePairs(object, EMPTY);
        Map<String, Object> map = new TreeMap<>();
        if (CollectionUtils.isNotEmpty(pairs)) {
            for (Pair<String, Object> pair : pairs) {
                map.put(pair.getLeft(), pair.getRight());
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private List<Pair<String, Object>> parsePairs(Object object, String prefix) {
        if (object == null) {
            return new ArrayList<>();
        }

        Class<?> clazz = object.getClass();

        if (TypeUtils.isSimpleValueType(clazz)) {
            if (StringUtils.isBlank(prefix)) {
                throw new ProjectException("缺少参数名称！");
            }
            return Collections.singletonList(ImmutablePair.of(prefix, object));
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            if (StringUtils.isBlank(prefix)) {
                throw new ProjectException("缺少参数名称！");
            }
            Collection<?> collection = (Collection<?>) object;
            if (CollectionUtils.isEmpty(collection)) {
                return new ArrayList<>();
            }
            List<Pair<String, Object>> result = new ArrayList<>();
            List<Object> list = new ArrayList<>(collection);
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                String prefixI = prefix + LEFT + i + RIGHT;
                result.addAll(parsePairs(obj, prefixI));
            }
            return result;
        }

        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(object), Map.class);
        List<Pair<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String prefixKey;
            if (StringUtils.isBlank(prefix)) {
                prefixKey = key;
            } else {
                prefixKey = prefix + DOT + key;
            }
            result.addAll(parsePairs(value, prefixKey));
        }
        return result;
    }
}
