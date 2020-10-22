package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.webapps.common.model.valid.JobTemplateSave;
import com.xzixi.framework.webapps.common.model.valid.JobTemplateUpdate;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
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
