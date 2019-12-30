package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.webapp.data.IAttachmentData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.vo.AttachmentVO;
import com.xzixi.self.portal.webapp.service.IAttachmentService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class AttachmentServiceImpl extends BaseServiceImpl<IAttachmentData, Attachment> implements IAttachmentService {

    @Override
    public AttachmentVO buildAttachmentVO(Integer id) {
        Attachment attachment = getById(id);
        return buildAttachmentVO(attachment);
    }

    @Override
    public AttachmentVO buildAttachmentVO(Attachment attachment) {
        return new AttachmentVO(attachment);
    }
}
