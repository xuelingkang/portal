package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.MailContent;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IMailContentService extends IBaseService<MailContent> {

    /**
     * 根据邮件id查询
     *
     * @param mailIds 邮件id集合
     * @return List&lt;MailContent>
     */
    List<MailContent> listByMailIds(Collection<Integer> mailIds);
}
