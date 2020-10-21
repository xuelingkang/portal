package com.xzixi.framework.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.framework.common.model.po.Job;
import com.xzixi.framework.common.model.po.JobTrigger;
import com.xzixi.framework.content.mapper.JobTriggerMapper;
import com.xzixi.framework.content.service.IJobTriggerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 这个service特殊，直接调用mapper
 *
 * @author 薛凌康
 */
@Service
public class JobTriggerServiceImpl extends ServiceImpl<JobTriggerMapper, JobTrigger> implements IJobTriggerService {

    @Override
    public JobTrigger getByJob(Job job) {
        if (job == null) {
            return null;
        }
        return getOne(new QueryWrapper<>(new JobTrigger(job.getSchedName(), job.getTriggerName(), job.getTriggerGroup())));
    }

    @Override
    public List<JobTrigger> listByJobs(Collection<Job> jobs) {
        if (CollectionUtils.isEmpty(jobs)) {
            return new ArrayList<>();
        }
        QueryWrapper<JobTrigger> queryWrapper = new QueryWrapper<>();
        jobs.forEach(job ->
                queryWrapper.or(wrapper -> wrapper
                        .eq("sched_name", job.getSchedName())
                        .eq("trigger_name", job.getTriggerName())
                        .eq("trigger_group", job.getTriggerGroup())));
        return list(queryWrapper);
    }
}
