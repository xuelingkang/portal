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

package com.xzixi.framework.boot.cache.generator;

import com.xzixi.framework.boot.core.model.BaseModel;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 根据entity集合的元素id生成删除缓存key
 *
 * @author 薛凌康
 */
@Data
public class DefaultEvictByEntitiesKeyGenerator implements KeyGenerator {

    private String keySeparator;
    private String keysSeparator;
    private String getByIdMethodName;

    @Override
    @SuppressWarnings("unchecked")
    public Object generate(Object target, Method method, Object... params) {
        Collection<? extends BaseModel> collection = ((Collection<? extends BaseModel>) params[0])
                .stream().filter(entity -> entity.getId() != null).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        return StringUtils.join(
                collection.stream().map(entity ->
                        target.getClass().getSimpleName() +
                                keySeparator +
                                getByIdMethodName +
                                keySeparator +
                                entity.getId())
                        .collect(Collectors.toList()),
                keysSeparator);
    }
}
