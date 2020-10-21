package com.xzixi.framework.content.service.impl;

import com.xzixi.framework.common.constant.AttachmentConstant;
import com.xzixi.framework.common.model.po.Attachment;
import com.xzixi.framework.common.model.vo.AttachmentVO;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.boot.sftp.client.component.ISftpClient;
import com.xzixi.framework.content.data.IAttachmentData;
import com.xzixi.framework.content.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class AttachmentServiceImpl extends BaseServiceImpl<IAttachmentData, Attachment> implements IAttachmentService {

    @Autowired
    private ISftpClient sftpClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAttachmentById(Integer id) {
        Attachment attachment = getById(id);
        if (!removeById(id)) {
            throw new ServerException(id, "删除附件失败！");
        }
        sftpClient.open(sftp -> {
            String address = attachment.getAddress();
            int lastSeparatorIndex = address.lastIndexOf(AttachmentConstant.SEPARATOR);
            String dir = address.substring(0, lastSeparatorIndex);
            String name = address.substring(lastSeparatorIndex + 1);
            sftp.delete(dir, name);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAttachmentsByIds(Collection<Integer> idList) {
        Collection<Attachment> attachments = listByIds(idList);
        if (!removeByIds(idList)) {
            throw new ServerException(idList, "删除附件失败！");
        }
        sftpClient.open(sftp -> attachments.forEach(attachment -> {
            String address = attachment.getAddress();
            int lastSeparatorIndex = address.lastIndexOf(AttachmentConstant.SEPARATOR);
            String dir = address.substring(0, lastSeparatorIndex);
            String name = address.substring(lastSeparatorIndex + 1);
            sftp.delete(dir, name);
        }));
    }

    @Override
    public AttachmentVO buildVO(Attachment attachment, AttachmentVO.BuildOption option) {
        return new AttachmentVO(attachment);
    }

    @Override
    public List<AttachmentVO> buildVO(Collection<Attachment> attachments, AttachmentVO.BuildOption option) {
        return attachments.stream().map(AttachmentVO::new).collect(Collectors.toList());
    }
}
