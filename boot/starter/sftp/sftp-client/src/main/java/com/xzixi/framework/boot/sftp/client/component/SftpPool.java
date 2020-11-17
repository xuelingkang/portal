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

package com.xzixi.framework.boot.sftp.client.component;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * sftp连接池
 *
 * @author 薛凌康
 */
public class SftpPool extends GenericObjectPool<Sftp> {

    /**
     * Creates a new <code>GenericObjectPool</code> that tracks and destroys
     * objects that are checked out, but never returned to the pool.
     * 创建一个{@link GenericObjectPool}对象池，跟踪使用后未返回给对象池的对象，防止对象泄漏。
     *
     * @param factory         The object factory to be used to create object instances
     *                        used by this pool
     *                        对象工厂
     * @param config          The base pool configuration to use for this pool instance.
     *                        The configuration is used by value. Subsequent changes to
     *                        the configuration object will not be reflected in the
     *                        pool.
     *                        对象池配置
     * @param abandonedConfig Configuration for abandoned object identification
     *                        废弃对象跟踪配置
     */
    public SftpPool(SftpFactory factory, SftpPoolConfig config, SftpAbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
