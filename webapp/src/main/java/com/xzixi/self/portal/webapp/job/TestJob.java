package com.xzixi.self.portal.webapp.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author 薛凌康
 */
@Slf4j
public class TestJob extends QuartzJobBean {

    @Autowired
    private IUserService userService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("----------logUserJob开始了----------");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String username = jobDataMap.getString("username");
        log.info("查询username="+username+"的用户信息，并打印");
        User user = userService.getOne(new QueryWrapper<>(new User().setUsername(username)));
        log.info(user.toString());
        log.info("----------logUserJob结束了----------");
    }
}