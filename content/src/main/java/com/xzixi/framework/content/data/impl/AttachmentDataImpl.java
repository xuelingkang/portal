package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.common.model.po.Attachment;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.content.data.IAttachmentData;
import com.xzixi.framework.content.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "attachment:base", casualCacheName = "attachment:casual")
public class AttachmentDataImpl extends MybatisPlusDataImpl<AttachmentMapper, Attachment> implements IAttachmentData {
}
