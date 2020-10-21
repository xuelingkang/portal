package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.common.model.po.MailContent;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IMailContentData;
import com.xzixi.framework.backend.mapper.MailContentMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "mailContent:base", casualCacheName = "mailContent:casual")
public class MailContentDataImpl extends MybatisPlusDataImpl<MailContentMapper, MailContent> implements IMailContentData {
}
