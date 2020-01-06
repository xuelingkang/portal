package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.framework.service.IVoService;
import com.xzixi.self.portal.webapp.model.po.Mail;
import com.xzixi.self.portal.webapp.model.po.MailContent;
import com.xzixi.self.portal.webapp.model.vo.MailVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IMailService extends IBaseService<Mail>, IVoService<Mail, MailVO, MailVO.BuildOption> {

    /**
     * 保存并发送邮件
     *
     * @param mail 邮件
     * @param content 邮件内容
     */
    void saveAndSend(Mail mail, MailContent content);

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
