/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2021  xuelingkang@163.com.
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

package com.xzixi.framework.boot.redis.annotation;

import java.lang.annotation.*;

/**
 * Limit
 * redis限流
 *
 * @author xuelingkang
 * @version 1.0.0
 * @date 2021年07月27日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {

    /**
     * 限流类型
     */
    LimitType type() default LimitType.METHOD;

    /**
     * 当type={@code KEY}时，必须指定key
     */
    String key() default "";

    /**
     * 指定时间范围，单位秒
     */
    int period() default 1;

    /**
     * 指定时间范围内最多请求次数
     */
    int count();

    /**
     * 限流类型
     */
    enum LimitType {

        /**
         * 指定key
         */
        KEY,
        /**
         * 客户端ip
         */
        IP,
        /**
         * 方法名
         */
        METHOD
    }
}
