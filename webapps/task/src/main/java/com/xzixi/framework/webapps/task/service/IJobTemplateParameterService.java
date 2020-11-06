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
import com.xzixi.framework.webapps.common.model.po.JobTemplateParameter;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IJobTemplateParameterService extends IBaseService<JobTemplateParameter> {

    /**
     * 根据任务模板id查询
     *
     * @param jobTemplateId 任务模板id
     * @return List&lt;JobTemplateParameter>
     */
    List<JobTemplateParameter> listByJobTemplateId(Integer jobTemplateId);

    /**
     * 根据任务模板id集合查询
     *
     * @param jobTemplateIds 任务模板id集合
     * @return List&lt;JobTemplateParameter>
     */
    List<JobTemplateParameter> listByJobTemplateIds(Collection<Integer> jobTemplateIds);
}
