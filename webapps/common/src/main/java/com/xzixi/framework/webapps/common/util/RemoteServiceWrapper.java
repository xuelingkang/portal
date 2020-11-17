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

package com.xzixi.framework.webapps.common.util;

import com.xzixi.framework.boot.core.exception.ClientException;
import com.xzixi.framework.boot.core.exception.RemoteException;
import com.xzixi.framework.boot.core.model.Result;
import org.springframework.util.Assert;

/**
 * @author xuelingkang
 * @date 2020-11-05
 */
public class RemoteServiceWrapper {

    public static void checkResult(Result<?> result) {
        Assert.notNull(result, "result不能为空！");
        int code = result.getCode();
        if (code == 200) {
            return;
        }
        if (code >= 400 && code < 500) {
            throw new ClientException(code, result.getMessage());
        }
        throw new RemoteException(code, result.getMessage());
    }

    public static <T> T getData(Result<T> result) {
        checkResult(result);
        return result.getData();
    }
}
