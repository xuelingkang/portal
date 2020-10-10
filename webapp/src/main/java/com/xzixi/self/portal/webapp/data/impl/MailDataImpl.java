package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IMailData;
import com.xzixi.self.portal.webapp.mapper.MailMapper;
import com.xzixi.self.portal.webapp.model.po.Mail;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "mail:base", casualCacheName = "mail:casual")
public class MailDataImpl extends MybatisPlusDataImpl<MailMapper, Mail> implements IMailData {
}
