package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.valid.JobSave;
import com.xzixi.self.portal.webapp.model.valid.JobUpdate;
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
