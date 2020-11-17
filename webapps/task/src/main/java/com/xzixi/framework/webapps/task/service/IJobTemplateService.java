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

package com.xzixi.framework.webapps.task.service;

import com.xzixi.framework.boot.persistent.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.JobTemplate;
import com.xzixi.framework.webapps.common.model.po.JobTemplateParameter;
import com.xzixi.framework.webapps.common.model.vo.JobTemplateVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IJobTemplateService extends IBaseService<JobTemplate>,
        IVoService<JobTemplate, JobTemplateVO, JobTemplateVO.BuildOption> {

    /**
     * 保存任务模板和参数
     *
     * @param jobTemplate 任务模板对象
     * @param parameters 任务模板参数
     */
    void saveJobTemplate(JobTemplate jobTemplate, Collection<JobTemplateParameter> parameters);

    /**
     * 更新任务模板和参数
     *
     * @param jobTemplate 任务模板对象
     * @param parameters 任务模板参数
     */
    void updateJobTemplateById(JobTemplate jobTemplate, Collection<JobTemplateParameter> parameters);

    /**
     * 根据id集合删除任务模板
     *
     * @param ids 模板id集合
     */
    void removeJobTemplatesByIds(Collection<Integer> ids);
}
