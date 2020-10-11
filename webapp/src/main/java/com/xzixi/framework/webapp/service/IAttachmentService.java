package com.xzixi.framework.webapp.service;

import com.xzixi.framework.webapp.model.po.Attachment;
import com.xzixi.framework.webapp.model.vo.AttachmentVO;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IAttachmentService extends IBaseService<Attachment>,
        IVoService<Attachment, AttachmentVO, AttachmentVO.BuildOption> {

    /**
     * 根据id删除附件
     *
     * @param id 附件id
     */
    void removeAttachmentById(Integer id);

    /**
     * 根据id集合删除附件
     *
     * @param ids 附件id集合
     */
    void removeAttachmentsByIds(Collection<Integer> ids);
}
