package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IMailContentData;
import com.xzixi.self.portal.webapp.mapper.MailContentMapper;
import com.xzixi.self.portal.webapp.model.po.MailContent;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "mailContent:base", casualCacheName = "mailContent:casual")
public class MailContentDataImpl extends MybatisPlusDataImpl<MailContentMapper, MailContent> implements IMailContentData {
}
