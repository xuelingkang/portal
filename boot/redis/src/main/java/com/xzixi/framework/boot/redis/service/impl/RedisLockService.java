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

package com.xzixi.framework.boot.redis.service.impl;

import com.xzixi.framework.boot.core.model.ILock;
import com.xzixi.framework.boot.core.service.ILockService;
import com.xzixi.framework.boot.redis.model.RedisLock;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author xuelingkang
 * @date 2020-11-07
 */
public class RedisLockService implements ILockService {

    @Getter
    @Setter
    private long defaultWaitTimeout;

    @Getter
    @Setter
    private long defaultLeaseTimeout;

    @Getter
    @Setter
    private DefaultValueGenerator defaultValueGenerator;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ILock getLock(String name, String value, long waitTimeout, long leaseTimeout) {
        return new RedisLock(name, value, waitTimeout, leaseTimeout, stringRedisTemplate);
    }
}
