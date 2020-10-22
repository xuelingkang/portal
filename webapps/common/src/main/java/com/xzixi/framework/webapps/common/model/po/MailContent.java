package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.webapps.common.model.valid.MailSave;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(groups = {MailSave.class}, max = 10000, message = "邮件内容不能超过10000字！")
    private String content;
}
