package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IAttachmentData;
import com.xzixi.self.portal.webapp.mapper.AttachmentMapper;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "attachment:base", casualCacheName = "attachment:casual")
public class AttachmentDataImpl extends MybatisPlusDataImpl<AttachmentMapper, Attachment> implements IAttachmentData {
}
