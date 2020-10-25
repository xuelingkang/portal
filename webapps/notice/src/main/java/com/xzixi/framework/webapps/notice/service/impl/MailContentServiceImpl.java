package com.xzixi.framework.webapps.notice.service.impl;

import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.common.model.po.MailContent;
import com.xzixi.framework.webapps.notice.data.IMailContentData;
import com.xzixi.framework.webapps.notice.service.IMailContentService;
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
