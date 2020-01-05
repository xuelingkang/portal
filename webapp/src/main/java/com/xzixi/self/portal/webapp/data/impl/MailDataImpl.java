package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.data.IMailData;
import com.xzixi.self.portal.webapp.mapper.MailMapper;
import com.xzixi.self.portal.webapp.model.po.Mail;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "mail:base", casualCacheName = "mail:casual")
public class MailDataImpl extends BaseDataImpl<MailMapper, Mail> implements IMailData {
}
