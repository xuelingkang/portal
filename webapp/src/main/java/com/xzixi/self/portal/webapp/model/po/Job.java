package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.extension.swagger2.annotation.IgnoreSwagger2Parameter;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.valid.JobSave;
import com.xzixi.self.portal.webapp.model.valid.JobUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private String cronExpression;

    @ApiModelProperty(value = "任务描述")
    @NotNull(groups = {JobSave.class, JobUpdate.class}, message = "任务描述不能为空！")
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
