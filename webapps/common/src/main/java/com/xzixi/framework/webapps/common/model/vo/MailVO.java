/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import com.xzixi.framework.webapps.common.model.po.Mail;
import com.xzixi.framework.webapps.common.model.po.MailContent;
import com.xzixi.framework.webapps.common.model.po.User;
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
