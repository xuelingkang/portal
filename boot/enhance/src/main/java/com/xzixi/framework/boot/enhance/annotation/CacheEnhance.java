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

package com.xzixi.framework.boot.enhance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主要用于自动维护数据库和缓存的一致性，直接用在数据层的实现类上
 *
 * @author 薛凌康
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface CacheEnhance {

    boolean getById() default true;
    boolean listByIds() default true;
    boolean getOne() default true;
    boolean list() default true;
    boolean page() default true;
    boolean count() default true;
    boolean updateById() default true;
    boolean updateBatchById() default true;
    boolean save() default true;
    boolean saveBatch() default true;
    boolean saveOrUpdate() default true;
    boolean saveOrUpdateBatch() default true;
    boolean removeById() default true;
    boolean removeByIds() default true;
}
