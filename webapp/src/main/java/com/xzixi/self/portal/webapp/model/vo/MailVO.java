package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.po.Mail;
import com.xzixi.self.portal.webapp.model.po.MailContent;
import com.xzixi.self.portal.webapp.model.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "邮件")
public class MailVO extends Mail {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发送用户")
    private User sendUser;

    @ApiModelProperty(value = "接收用户")
    private Collection<User> toUsers;

    @ApiModelProperty(value = "邮件附件")
    private Collection<Attachment> attachments;

    @ApiModelProperty(value = "邮件内容")
    private MailContent content;

    public MailVO(Mail mail, String... ignoreProperties) {
        BeanUtils.copyProperties(mail, this, ignoreProperties);
    }

    @Data
    @AllArgsConstructor
    public static class BuildOption {
        private boolean sendUser;
        private boolean toUsers;
        private boolean attachments;
        private boolean content;
    }
}
