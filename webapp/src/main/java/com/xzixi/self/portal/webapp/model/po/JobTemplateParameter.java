package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.webmvc.model.BaseModel;
import com.xzixi.self.portal.webapp.model.enums.JobParameterType;
import com.xzixi.self.portal.webapp.model.valid.JobTemplateSave;
import com.xzixi.self.portal.webapp.model.valid.JobTemplateUpdate;
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
