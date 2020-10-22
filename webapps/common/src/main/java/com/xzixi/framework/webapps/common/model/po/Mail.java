package com.xzixi.framework.webapps.common.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzixi.framework.webapps.common.model.enums.MailStatus;
import com.xzixi.framework.webapps.common.model.enums.MailType;
import com.xzixi.framework.webapps.common.model.valid.MailSave;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.webapps.common.typehandler.IntegerListTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(groups = {MailSave.class}, max = 200, message = "邮件标题不能超过200字！")
    private String subject;

    @ApiModelProperty(value = "邮件类型")
    private MailType type;

    @ApiModelProperty(value = "邮件状态")
    private MailStatus status;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "发送用户id")
    private Integer sendUserId;

    @ApiModelProperty(value = "接收用户id，优先级大于接收邮箱")
    @TableField(typeHandler = IntegerListTypeHandler.class)
    @NotEmpty(groups = {MailSave.class}, message = "接收用户id不能为空！")
    @Size(groups = {MailSave.class}, max = 100, message = "接受用户个数不能超过100个！")
    private Collection<Integer> toUserIds;

    @ApiModelProperty(value = "接收邮箱")
    @Email(groups = {MailSave.class}, message = "接收邮箱格式不正确！")
    @Length(groups = {MailSave.class}, max = 50, message = "接收邮箱不能大于50字！")
    private String toEmail;

    @ApiModelProperty(value = "附件id")
    @TableField(typeHandler = IntegerListTypeHandler.class)
    @Size(groups = {MailSave.class}, max = 100, message = "附件个数不能超过100个！")
    private Collection<Integer> attachmentIds;
}
