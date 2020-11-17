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

package com.xzixi.framework.webapps.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzixi.framework.webapps.common.model.po.Job;
import com.xzixi.framework.webapps.common.model.po.JobTrigger;
import com.xzixi.framework.webapps.task.mapper.JobTriggerMapper;
import com.xzixi.framework.webapps.task.service.IJobTriggerService;
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
