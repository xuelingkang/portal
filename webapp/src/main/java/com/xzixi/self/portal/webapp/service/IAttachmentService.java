package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.framework.service.IVoService;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.vo.AttachmentVO;

/**
 * @author 薛凌康
 */
public interface IAttachmentService extends IBaseService<Attachment>,
        IVoService<Attachment, AttachmentVO, AttachmentVO.BuildOption> {
}
