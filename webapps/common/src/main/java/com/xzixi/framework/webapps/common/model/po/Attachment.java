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

import com.xzixi.framework.webapps.common.model.enums.AttachmentType;
import com.xzixi.framework.boot.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "附件")
public class Attachment extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件id")
    private Integer id;

    @ApiModelProperty(value = "附件类型")
    private AttachmentType type;

    @ApiModelProperty(value = "附件名称")
    private String name;

    @ApiModelProperty(value = "访问路径")
    private String url;

    @ApiModelProperty(value = "磁盘路径")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}
