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

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzixi.framework.webapps.common.model.enums.JobTriggerState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "定时任务触发器")
@TableName("qrtz_triggers")
public class JobTrigger {

    @ApiModelProperty(value = "调度器名称")
    private String schedName;

    @ApiModelProperty(value = "触发器名称")
    private String triggerName;

    @ApiModelProperty(value = "触发器组")
    private String triggerGroup;

    @ApiModelProperty(value = "下次触发时间")
    private Long nextFireTime;

    @ApiModelProperty(value = "上次触发时间")
    private Long prevFireTime;

    @ApiModelProperty(value = "触发器状态")
    private JobTriggerState triggerState;

    public JobTrigger(String schedName, String triggerName, String triggerGroup) {
        this.schedName = schedName;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
    }
}
