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
 * @version 1.0
 * @date 2021年07月27日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {

    /**
     * 限流策略
     */
    Strategy strategy() default Strategy.TOKEN;

    /**
     * 限流类型
     */
    Type type() default Type.METHOD;

    /**
     * 当type={@code KEY}时，必须指定key
     */
    String key() default "";

    /**
     * 是否对每个客户端ip分别限流
     */
    boolean client() default false;

    /**
     * 指定时间范围，单位秒
     */
    int period() default 1;

    /**
     * 时间周期内流量限制
     * <p>计数器，时间周期内允许数据包个数
     * <p>令牌桶，时间周期内产生令牌个数
     * <p>漏桶，时间周期内流出数据包个数
     */
    int rate();

    /**
     * 接口数据包个数
     */
    int count() default 1;

    /**
     * 令牌桶或漏桶策略，表示桶容量，必须设置
     * <p>计数器忽略
     */
    int capacity() default 0;

    /**
     * 等待超时时间，单位毫秒，0表示不等待
     * <p>令牌桶策略支持
     */
    int timeout() default 0;

    /**
     * 限流类型
     */
    enum Type {

        /**
         * 指定key
         */
        KEY,
        /**
         * 方法名
         */
        METHOD
    }

    /**
     * 限流策略
     */
    enum Strategy {

        /**
         * 固定窗口计数器，有临界问题
         */
        COUNTER,
        /**
         * 令牌桶，取令牌的速度不受限制，所以可以应对短暂的突发流量，但是不一定安全
         */
        TOKEN,
        /**
         * 漏桶，桶容量和流出速度是恒定的，不能很好地应对突发流量
         */
        LEAKY
    }
}
