package com.xzixi.framework.webapps.notice.data.impl;

import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.common.model.po.Mail;
import com.xzixi.framework.webapps.notice.data.IMailData;
import com.xzixi.framework.webapps.notice.mapper.MailMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "mail:base", casualCacheName = "mail:casual")
public class MailDataImpl extends MybatisPlusDataImpl<MailMapper, Mail> implements IMailData {
}
