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

package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.webapps.common.model.valid.JobSave;
import com.xzixi.framework.webapps.common.model.valid.JobUpdate;
import com.xzixi.framework.boot.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "定时任务参数")
public class JobParameter extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数id")
    private Integer id;

    @ApiModelProperty(value = "定时任务id")
    private Integer jobId;

    @ApiModelProperty(value = "参数名称")
    @Length(groups = {JobSave.class, JobUpdate.class}, max = 200, message = "参数名称不能超过200字！")
    private String name;

    @ApiModelProperty(value = "参数值")
    @Length(groups = {JobSave.class, JobUpdate.class}, max = 200, message = "参数值不能超过200字！")
    private String value;
}
