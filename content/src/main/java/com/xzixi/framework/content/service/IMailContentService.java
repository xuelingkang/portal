package com.xzixi.framework.content.service;

import com.xzixi.framework.common.model.po.MailContent;
import com.xzixi.framework.boot.webmvc.service.IBaseService;

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
