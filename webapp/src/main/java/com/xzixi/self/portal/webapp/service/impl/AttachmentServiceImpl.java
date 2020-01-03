package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IAttachmentData;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.vo.AttachmentVO;
import com.xzixi.self.portal.webapp.service.IAttachmentService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class AttachmentServiceImpl extends BaseServiceImpl<IAttachmentData, Attachment> implements IAttachmentService {

    @Override
    public AttachmentVO buildVO(Attachment attachment, AttachmentVO.BuildOption option) {
        return new AttachmentVO(attachment);
    }

    @Override
    public List<AttachmentVO> buildVO(Collection<Attachment> attachments, AttachmentVO.BuildOption option) {
        return attachments.stream().map(AttachmentVO::new).collect(Collectors.toList());
    }
}
