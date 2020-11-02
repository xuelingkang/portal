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

import com.xzixi.framework.webapps.common.model.enums.JobParameterType;
import com.xzixi.framework.webapps.common.model.valid.JobTemplateSave;
import com.xzixi.framework.webapps.common.model.valid.JobTemplateUpdate;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "任务模板参数")
public class JobTemplateParameter extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数id")
    private Integer id;

    @ApiModelProperty(value = "任务模板id")
    private Integer jobTemplateId;

    @ApiModelProperty(value = "参数名称")
    @Length(groups = {JobTemplateSave.class, JobTemplateUpdate.class}, max = 50, message = "参数名称不能超过50字！")
    private String name;

    @ApiModelProperty(value = "参数类型")
    private JobParameterType type;

    @ApiModelProperty(value = "参数描述")
    @Length(groups = {JobTemplateSave.class, JobTemplateUpdate.class}, max = 200, message = "参数描述不能超过200字！")
    private String description;
}
