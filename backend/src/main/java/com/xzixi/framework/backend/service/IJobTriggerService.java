package com.xzixi.framework.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzixi.framework.backend.model.po.Job;
import com.xzixi.framework.backend.model.po.JobTrigger;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobTriggerService extends IService<JobTrigger> {

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
    List<JobTrigger> listByJobs(Collection<Job> jobs);
}