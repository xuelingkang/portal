package com.xzixi.self.portal.webapp.data;

import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.model.po.Attachment;

/**
 * @author 薛凌康
 */
public interface IAttachmentData extends IBaseData<Attachment> {

    String BASE_CACHE_NAME = "attachment:base";
    String CASUAL_CACHE_NAME = "attachment:casual";
}