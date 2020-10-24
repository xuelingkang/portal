package com.xzixi.framework.webapps.file.data.impl;

import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import com.xzixi.framework.webapps.file.data.IAttachmentData;
import com.xzixi.framework.webapps.file.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "attachment:base", casualCacheName = "attachment:casual")
public class AttachmentDataImpl extends MybatisPlusDataImpl<AttachmentMapper, Attachment> implements IAttachmentData {
}
