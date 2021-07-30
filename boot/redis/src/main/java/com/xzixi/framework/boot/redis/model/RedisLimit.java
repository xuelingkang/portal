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

package com.xzixi.framework.boot.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RedisLimit
 * 限流器参数
 *
 * @author xuelingkang
 * @version 1.0
 * @date 2021年07月28日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisLimit {

    /**
     * 限流器key
     */
    private String key;
    /**
     * 时间周期，秒
     */
    private Integer period;
    /**
     * 时间周期内流量限制
     */
    private Integer rate;
    /**
     * 令牌桶容量
     */
    private Integer capacity;
    /**
     * 需要令牌个数
     */
    private Integer count;
    /**
     * 等待超时时间，毫秒
     */
    private Integer timeout;
}
