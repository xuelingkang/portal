package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "任务模板")
public class JobTemplate extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务模板id")
    private Integer id;

    @ApiModelProperty(value = "任务模板名称")
    private String name;

    @ApiModelProperty(value = "任务模板类名")
    private String className;

    @ApiModelProperty(value = "任务模板描述")
    private String description;
}
