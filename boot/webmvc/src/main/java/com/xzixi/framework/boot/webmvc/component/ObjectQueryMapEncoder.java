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

package com.xzixi.framework.boot.webmvc.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import feign.QueryMapEncoder;
import org.apache.commons.collections.CollectionUtils;
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
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(object), Map.class);

        List<Pair<String, Object>> simplePairs = new ArrayList<>();
        List<Pair<String, JSONObject>> objectPairs = new ArrayList<>();
        List<Pair<String, JSONArray>> arrayPairs = new ArrayList<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                ImmutablePair<String, JSONObject> pair = ImmutablePair.of(prefix + key, (JSONObject) value);
                objectPairs.add(pair);
                continue;
            }
            if (value instanceof JSONArray) {
                ImmutablePair<String, JSONArray> pair = ImmutablePair.of(prefix + key, (JSONArray) value);
                arrayPairs.add(pair);
                continue;
            }
            ImmutablePair<String, Object> pair = ImmutablePair.of(prefix + key, value);
            simplePairs.add(pair);
        }

        if (CollectionUtils.isNotEmpty(objectPairs)) {
            for (Pair<String, JSONObject> pair : objectPairs) {
                simplePairs.addAll(parsePairs(pair.getRight(), pair.getKey() + DOT));
            }
        }

        if (CollectionUtils.isNotEmpty(arrayPairs)) {
            for (int i = 0; i < arrayPairs.size(); i++) {
                Pair<String, JSONArray> pair = arrayPairs.get(i);
                String prefixI = pair.getLeft() + LEFT + i + RIGHT + DOT;
                for (Object obj : pair.getRight()) {
                    simplePairs.addAll(parsePairs(obj, prefixI));
                }
            }
        }

        return simplePairs;
    }
}
