package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.vo.AttachmentVO;

/**
 * @author 薛凌康
 */
public interface IAttachmentService extends IBaseService<Attachment> {

    /**
     * 构建AttachmentVO
     *
     * @param id 附件id
     * @return AttachmentVO
     */
    AttachmentVO buildAttachmentVO(Integer id);

    /**
     * 构建AttachmentVO
     *
     * @param attachment Attachment
     * @return AttachmentVO
     */
    AttachmentVO buildAttachmentVO(Attachment attachment);
}
