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

package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.webapps.common.model.valid.JobSave;
import com.xzixi.framework.webapps.common.model.valid.JobUpdate;
import com.xzixi.framework.boot.swagger2.annotations.IgnoreSwagger2Parameter;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "定时任务")
public class Job extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    @Null(groups = {JobSave.class}, message = "任务id必须为空！")
    @NotNull(groups = {JobUpdate.class}, message = "任务id不能为空！")
    private Integer id;

    @ApiModelProperty(value = "任务模板id")
    @NotNull(groups = {JobSave.class, JobUpdate.class}, message = "任务模板id不能为空！")
    private Integer jobTemplateId;

    @ApiModelProperty(value = "开始时间")
    @NotNull(groups = {JobSave.class, JobUpdate.class}, message = "开始时间不能为空！")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "cron表达式")
    @NotNull(groups = {JobSave.class, JobUpdate.class}, message = "cron表达式不能为空！")
    @Length(groups = {JobSave.class, JobUpdate.class}, max = 20, message = "cron表达式不能超过20字！")
    private String cronExpression;

    @ApiModelProperty(value = "任务描述")
    @NotNull(groups = {JobSave.class, JobUpdate.class}, message = "任务描述不能为空！")
    @Length(groups = {JobSave.class, JobUpdate.class}, max = 200, message = "任务描述不能超过200字！")
    private String description;

    @ApiModelProperty(value = "调度器名称")
    @IgnoreSwagger2Parameter
    private String schedName;

    @ApiModelProperty(value = "触发器名称")
    @IgnoreSwagger2Parameter
    private String triggerName;

    @ApiModelProperty(value = "触发器组")
    @IgnoreSwagger2Parameter
    private String triggerGroup;
}
