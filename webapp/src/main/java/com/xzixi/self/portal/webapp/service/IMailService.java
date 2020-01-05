package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.framework.service.IVoService;
import com.xzixi.self.portal.webapp.model.po.Mail;
import com.xzixi.self.portal.webapp.model.vo.MailVO;

/**
 * @author 薛凌康
 */
public interface IMailService extends IBaseService<Mail>, IVoService<Mail, MailVO, MailVO.BuildOption> {
}
