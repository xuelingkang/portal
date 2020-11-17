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

package com.xzixi.framework.boot.core.service;

import com.xzixi.framework.boot.core.model.ILock;

/**
 * @author xuelingkang
 * @date 2020-11-07
 */
public interface ILockService {

    /**
     * 默认等待时间，毫秒
     * @return long
     */
    long getDefaultWaitTimeout();

    /**
     * 默认租借时间，毫秒
     *
     * @return long
     */
    long getDefaultLeaseTimeout();

    /**
     * 默认值生成器
     *
     * @return DefaultValueGenerator
     */
    DefaultValueGenerator getDefaultValueGenerator();

    /**
     * 使用默认超时时间和值获取分布式锁
     *
     * @param name 锁名称，代表要竞争的资源
     * @return ILock
     */
    default ILock getLock(String name) {
        return getLock(name, getDefaultValueGenerator().generate(), getDefaultWaitTimeout(), getDefaultLeaseTimeout());
    }

    /**
     * 获取分布式锁
     *
     * @param name 锁名称，代表要竞争的资源
     * @param value 锁的唯一标识
     * @return ILock
     */
    default ILock getLock(String name, String value) {
        return getLock(name, value, getDefaultWaitTimeout(), getDefaultLeaseTimeout());
    }

    /**
     * 使用默认值获取分布式锁
     *
     * @param name 锁名称，代表要竞争的资源
     * @param waitTimeout 等待时间，毫秒
     * @param leaseTimeout 锁超时时间，超时后会自动释放，毫秒
     * @return ILock
     */
    default ILock getLock(String name, long waitTimeout, long leaseTimeout) {
        return getLock(name, getDefaultValueGenerator().generate(), waitTimeout, leaseTimeout);
    }

    /**
     * 获取分布式锁
     *
     * @param name 锁名称，代表要竞争的资源
     * @param value 锁的唯一标识
     * @param waitTimeout 等待时间，毫秒
     * @param leaseTimeout 锁超时时间，超时后会自动释放，毫秒
     * @return ILock
     */
    ILock getLock(String name, String value, long waitTimeout, long leaseTimeout);

    interface DefaultValueGenerator {
        String generate();
    }
}
