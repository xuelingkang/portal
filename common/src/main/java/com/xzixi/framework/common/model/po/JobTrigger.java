package com.xzixi.framework.common.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzixi.framework.common.model.enums.JobTriggerState;
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
