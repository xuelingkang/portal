package com.xzixi.self.portal.webapp.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.enums.MailStatus;
import com.xzixi.self.portal.webapp.model.enums.MailType;
import com.xzixi.self.portal.webapp.model.valid.MailSave;
import com.xzixi.self.portal.webapp.typehandler.IntegerListTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "邮件")
public class Mail extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件id")
    @Null(groups = {MailSave.class}, message = "邮件id必须为空！")
    private Integer id;

    @ApiModelProperty(value = "邮件标题")
    @NotBlank(groups = {MailSave.class}, message = "邮件标题不能为空！")
    private String subject;

    @ApiModelProperty(value = "邮件类型")
    private MailType type;

    @ApiModelProperty(value = "邮件状态")
    private MailStatus status;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "发送用户id")
    private Integer sendUserId;

    @ApiModelProperty(value = "接收用户id")
    @TableField(typeHandler = IntegerListTypeHandler.class)
    @NotEmpty(groups = {MailSave.class}, message = "接收用户id不能为空！")
    private Collection<Integer> toUserIds;

    @ApiModelProperty(value = "附件id")
    @TableField(typeHandler = IntegerListTypeHandler.class)
    private Collection<Integer> attachmentIds;
}
