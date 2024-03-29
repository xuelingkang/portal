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

package com.xzixi.framework.webapps.system;

import com.alibaba.fastjson.JSON;
import com.xzixi.framework.webapps.common.model.po.App;
import com.xzixi.framework.webapps.system.service.IAppService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuelingkang
 * @date 2020-10-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SystemApplicationTests {

    @Autowired
    private IAppService appService;

    @Test
    public void testListByIds() {
        List<App> apps = appService.listByIds(Arrays.asList(1, 2, 3));
        log.info(JSON.toJSONString(apps));
    }
}
