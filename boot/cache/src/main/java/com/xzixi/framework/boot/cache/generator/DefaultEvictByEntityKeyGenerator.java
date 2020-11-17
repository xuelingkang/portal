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

import com.xzixi.framework.boot.core.model.BaseModel;
import lombok.Data;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @author 薛凌康
 */
@Data
public class DefaultEvictByEntityKeyGenerator implements KeyGenerator {

    private String keySeparator;
    private String getByIdMethodName;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Integer id = ((BaseModel) params[0]).getId();
        if (id == null) {
            return "";
        }
        return target.getClass().getSimpleName() +
                keySeparator +
                getByIdMethodName +
                keySeparator +
                id;
    }
}
