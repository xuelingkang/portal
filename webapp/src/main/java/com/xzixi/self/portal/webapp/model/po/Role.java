package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.webapp.framework.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "角色")
public class Role extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Integer id;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色顺序")
    private Integer seq;
    @ApiModelProperty(value = "是否初始角色", allowableValues = "true,false")
    private Boolean initial;
    @ApiModelProperty(value = "角色描述")
    private String description;
}
