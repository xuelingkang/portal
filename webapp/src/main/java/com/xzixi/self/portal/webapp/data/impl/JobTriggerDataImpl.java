package com.xzixi.self.portal.webapp.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.self.portal.webapp.data.IJobTriggerData;
import com.xzixi.self.portal.webapp.mapper.JobTriggerMapper;
import com.xzixi.self.portal.webapp.model.po.Job;
import com.xzixi.self.portal.webapp.model.po.JobTrigger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class JobTriggerDataImpl extends ServiceImpl<JobTriggerMapper, JobTrigger> implements IJobTriggerData {

    @Override
    public JobTrigger getByJob(Job job) {
        if (job == null) {
            return null;
        }
        return getOne(new QueryWrapper<>(new JobTrigger(job.getSchedName(), job.getTriggerName(), job.getTriggerGroup())));
    }

    @Override
    public List<JobTrigger> getByJobs(Collection<Job> jobs) {
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
