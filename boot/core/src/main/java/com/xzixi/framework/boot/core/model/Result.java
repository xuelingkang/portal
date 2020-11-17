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

package com.xzixi.framework.boot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果
 *
 * @author 薛凌康
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private int code = 200;

    /**
     * 当状态码不等于200时，可能需要一个subCode来表示具体的错误
     */
    private int subCode = 0;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public Result(T data) {
        this(200, 0, null, data);
    }

    public Result(int code, int subCode) {
        this(code, subCode, null, null);
    }

    public Result(int code, int subCode, String message) {
        this(code, subCode, message, null);
    }

    public Result(int code, String message, T data) {
        this(code, 0, message, data);
    }
}
