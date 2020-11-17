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

package com.xzixi.framework.boot.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用远程接口异常
 *
 * @author xuelingkang
 * @date 2020-10-26
 */
@Getter
@Setter
public class RemoteException extends RuntimeException {

    private int status;

    public RemoteException(int status, String message) {
        super(message);
        this.status = status;
    }
}
