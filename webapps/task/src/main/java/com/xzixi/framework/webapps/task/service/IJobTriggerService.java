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

package com.xzixi.framework.webapps.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzixi.framework.webapps.common.model.po.Job;
import com.xzixi.framework.webapps.common.model.po.JobTrigger;

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
