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

package com.xzixi.framework.webapps.sso.server;

import com.alibaba.fastjson.JSON;
import com.xzixi.framework.boot.core.exception.LockAcquireException;
import com.xzixi.framework.boot.core.exception.LockReleaseException;
import com.xzixi.framework.boot.core.model.ILock;
import com.xzixi.framework.boot.core.util.Utils;
import com.xzixi.framework.boot.redis.service.impl.RedisLockService;
import com.xzixi.framework.webapps.sso.server.model.SsoAccessTokenValue;
import com.xzixi.framework.webapps.sso.server.model.TokenInfo;
import com.xzixi.framework.webapps.sso.server.service.ISsoAccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xuelingkang
 * @date 2020-10-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SsoApplicationTests {

    @Autowired
    private ISsoAccessTokenService ssoAccessTokenService;
    @Autowired
    private RedisLockService redisLockService;

    @Test
    public void testSave() {
        TokenInfo info = ssoAccessTokenService.createAndSave(1, "123");
        log.info(JSON.toJSONString(info));
    }

    @Test
    public void testGet() {
        SsoAccessTokenValue ssoAccessTokenValue = ssoAccessTokenService.getTokenValue("7a70be74-1d79-43dc-a783-4cbe248aae8e");
        log.info(JSON.toJSONString(ssoAccessTokenValue));
    }

    @Test
    public void testAcquireRedisLock() throws LockAcquireException {
        ILock lock = redisLockService.getLock("lock::test", 10000, 30000);
        log.info(lock.toString());
        lock.acquire();
    }

    @Test
    public void testReleaseRedisLock() throws LockReleaseException, LockAcquireException {
        ILock lock = redisLockService.getLock("lock::test", "123", 40000, 100000);
        log.info(lock.toString());
        lock.acquire();
        Utils.safeSleep(10000);
        lock.release();
    }
}
