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

import com.xzixi.framework.webapps.common.model.valid.JobTemplateSave;
import com.xzixi.framework.webapps.common.model.valid.JobTemplateUpdate;
import com.xzixi.framework.boot.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "任务模板")
public class JobTemplate extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务模板id")
    @Null(groups = {JobTemplateSave.class}, message = "任务模板id必须为空！")
    @NotNull(groups = {JobTemplateUpdate.class}, message = "任务模板id不能为空！")
    private Integer id;

    @ApiModelProperty(value = "任务模板名称")
    @NotBlank(groups = {JobTemplateSave.class}, message = "任务模板名称不能为空！")
    @Length(groups = {JobTemplateSave.class, JobTemplateUpdate.class}, max = 50, message = "任务模板名称不能超过50字！")
    private String name;

    @ApiModelProperty(value = "任务模板类名")
    @NotBlank(groups = {JobTemplateSave.class}, message = "任务模板类名不能为空！")
    @Length(groups = {JobTemplateSave.class, JobTemplateUpdate.class}, max = 200, message = "任务模板类名不能超过200字！")
    private String className;

    @ApiModelProperty(value = "任务模板描述")
    @NotBlank(groups = {JobTemplateSave.class}, message = "任务模板描述不能为空！")
    @Length(groups = {JobTemplateSave.class, JobTemplateUpdate.class}, max = 200, message = "任务模板描述不能超过200字！")
    private String description;
}
