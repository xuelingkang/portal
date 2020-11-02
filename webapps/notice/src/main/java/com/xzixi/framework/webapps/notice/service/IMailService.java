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

package com.xzixi.framework.webapps.notice.service;

import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.Mail;
import com.xzixi.framework.webapps.common.model.po.MailContent;
import com.xzixi.framework.webapps.common.model.vo.MailVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IMailService extends IBaseService<Mail>, IVoService<Mail, MailVO, MailVO.BuildOption> {

    /**
     * 保存邮件
     *
     * @param mail 邮件
     * @param content 邮件内容
     */
    void saveMail(Mail mail, MailContent content);

    /**
     * 发送邮件
     *
     * @param mail 邮件
     * @param content 邮件内容
     */
    void send(Mail mail, MailContent content);

    /**
     * 根据邮件id删除邮件
     *
     * @param ids 邮件id集合
     */
    void removeMailsByIds(Collection<Integer> ids);
}
