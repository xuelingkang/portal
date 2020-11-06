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

import com.xzixi.framework.boot.persistent.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.Job;
import com.xzixi.framework.webapps.common.model.po.JobParameter;
import com.xzixi.framework.webapps.common.model.vo.JobVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IJobService extends IBaseService<Job>, IVoService<Job, JobVO, JobVO.BuildOption> {

    /**
     * 保存定时任务
     *
     * @param job 定时任务
     * @param parameters 定时任务参数
     */
    void saveJob(Job job, Collection<JobParameter> parameters);

    /**
     * 更新定时任务
     *
     * @param job 定时任务
     * @param parameters 定时任务参数
     */
    void updateJob(Job job, Collection<JobParameter> parameters);

    /**
     * 根据id删除定时任务，同时删除关联的参数
     *
     * @param ids 定时任务id
     */
    void removeJobsByIds(Collection<Integer> ids);

    /**
     * 根据id暂停定时任务
     *
     * @param ids 定时任务id集合
     */
    void pauseByIds(Collection<Integer> ids);

    /**
     * 根据id恢复定时任务
     *
     * @param ids 定时任务id集合
     */
    void resumeByIds(Collection<Integer> ids);
}
