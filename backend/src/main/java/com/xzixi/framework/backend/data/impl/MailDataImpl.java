package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.common.model.po.Mail;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IMailData;
import com.xzixi.framework.backend.mapper.MailMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "mail:base", casualCacheName = "mail:casual")
public class MailDataImpl extends MybatisPlusDataImpl<MailMapper, Mail> implements IMailData {
}
