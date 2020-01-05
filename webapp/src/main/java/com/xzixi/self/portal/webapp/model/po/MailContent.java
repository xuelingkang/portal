package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "邮件内容")
public class MailContent extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件内容id")
    private Integer id;

    @ApiModelProperty(value = "邮件id")
    private Integer mailId;

    @ApiModelProperty(value = "邮件内容")
    private String content;
}
