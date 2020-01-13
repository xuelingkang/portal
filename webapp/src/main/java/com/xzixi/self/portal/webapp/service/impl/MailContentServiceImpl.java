package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.model.search.QueryParams;
import com.xzixi.self.portal.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IMailContentData;
import com.xzixi.self.portal.webapp.model.po.MailContent;
import com.xzixi.self.portal.webapp.service.IMailContentService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class MailContentServiceImpl extends BaseServiceImpl<IMailContentData, MailContent> implements IMailContentService {

    @Override
    public List<MailContent> listByMailIds(Collection<Integer> mailIds) {
        return list(new QueryParams<MailContent>().in("mailId", mailIds));
    }
}
