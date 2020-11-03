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

package com.xzixi.framework.boot.webmvc.config.cache.generator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.xzixi.framework.boot.webmvc.config.cache.RedisCacheConstant.*;

/**
 * 根据id集合生成删除缓存key
 *
 * @author 薛凌康
 */
public class DefaultEvictByIdsKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0) {
            return WILDCARD;
        }
        return StringUtils.join(
                ((Collection<?>) params[0]).stream().map(id ->
                        target.getClass().getName() +
                                KEY_SEPARATOR +
                                GET_BY_ID_METHOD_NAME +
                                KEY_SEPARATOR +
                                id)
                        .collect(Collectors.toList()),
                KEYS_SEPARATOR);
    }
}
