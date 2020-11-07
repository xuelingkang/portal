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

package com.xzixi.framework.boot.core.model;

import com.xzixi.framework.boot.core.exception.LockAcquireException;
import com.xzixi.framework.boot.core.exception.LockReleaseException;

/**
 * 分布式锁
 *
 * @author xuelingkang
 * @date 2020-11-07
 */
public interface ILock {

    /**
     * 获取锁
     *
     * @throws LockAcquireException 获取锁失败
     */
    void acquire() throws LockAcquireException;

    /**
     * 释放锁
     *
     * @throws LockReleaseException 释放锁失败
     */
    void release() throws LockReleaseException;

    /**
     * 释放锁，忽略异常
     */
    void safeRelease();
}
