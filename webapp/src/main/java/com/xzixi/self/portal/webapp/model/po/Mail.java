package com.xzixi.self.portal.webapp.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.enums.MailStatus;
import com.xzixi.self.portal.webapp.model.enums.MailType;
import com.xzixi.self.portal.webapp.model.valid.MailSave;
import com.xzixi.self.portal.webapp.typehandler.IntegerListTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Collection;

/**
 * {@code @TableName(autoResultMap = true)}查询时使用typehandler
 *
 * @author 薛凌康
 */
@Data
@ApiModel(description = "邮件")
@TableName(autoResultMap = true)
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
    @Size(groups = {MailSave.class}, max = 100, message = "接受用户个数必须大于等于1且小于等于100！")
    private Collection<Integer> toUserIds;

    @ApiModelProperty(value = "附件id")
    @TableField(typeHandler = IntegerListTypeHandler.class)
    @Size(groups = {MailSave.class}, max = 100, message = "附件个数必须小于等于100！")
    private Collection<Integer> attachmentIds;
}
