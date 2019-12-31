package com.xzixi.self.portal.webapp.data;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobTrigger;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobTriggerData extends IService<JobTrigger> {

    /**
     * 根据定时任务查询触发器
     *
     * @param job 定时任务
     * @return JobTrigger
     */
    JobTrigger getByJob(Job job);

    /**
     * 根据定时任务集合查询触发器
     *
     * @param jobs 定时任务集合
     * @return Collection&lt;JobTrigger>
     */
    List<JobTrigger> getByJobs(Collection<Job> jobs);
}
