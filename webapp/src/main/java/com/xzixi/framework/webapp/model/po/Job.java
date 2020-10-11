package com.xzixi.framework.webapp.model.po;

import com.xzixi.framework.webapp.model.valid.JobSave;
import com.xzixi.framework.webapp.model.valid.JobUpdate;
import com.xzixi.framework.boot.swagger2.extension.annotation.IgnoreSwagger2Parameter;
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
