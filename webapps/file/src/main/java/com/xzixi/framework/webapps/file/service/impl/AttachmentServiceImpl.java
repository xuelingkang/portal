/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.file.service.impl;

import com.xzixi.framework.boot.sftp.client.component.ISftpClient;
import com.xzixi.framework.boot.core.exception.ServerException;
import com.xzixi.framework.boot.persistent.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.file.constant.AttachmentConstant;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import com.xzixi.framework.webapps.common.model.vo.AttachmentVO;
import com.xzixi.framework.webapps.file.data.IAttachmentData;
import com.xzixi.framework.webapps.file.service.IAttachmentService;
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
