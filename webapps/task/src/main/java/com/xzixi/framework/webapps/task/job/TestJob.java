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

package com.xzixi.framework.webapps.task.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author 薛凌康
 */
@Slf4j
public class TestJob extends QuartzJobBean {

//    @Autowired
//    private IUserService userService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("----------logUserJob开始了----------");
//        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
//        String username = jobDataMap.getString("username");
//        log.info("查询username="+username+"的用户信息，并打印");
//        User user = userService.getOne(new QueryParams<>(new User().setUsername(username)));
//        log.info(user.toString());
        log.info("----------logUserJob结束了----------");
    }
}
