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

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.boot.core.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.po.Job;
import com.xzixi.framework.webapps.common.model.po.JobParameter;
import com.xzixi.framework.webapps.common.model.po.JobTrigger;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "定时任务")
public class JobVO extends Job {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "触发器")
    private JobTrigger jobTrigger;

    @ApiModelProperty(value = "定时任务参数列表")
    private Collection<JobParameter> parameters;

    public JobVO(Job job, String... ignoreProperties) {
        BeanUtils.copyProperties(job, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
        private boolean trigger;
        private boolean parameters;
    }
}
