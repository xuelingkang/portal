package com.xzixi.framework.backend.service;

import com.xzixi.framework.common.model.po.Mail;
import com.xzixi.framework.common.model.po.MailContent;
import com.xzixi.framework.common.model.vo.MailVO;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;

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
