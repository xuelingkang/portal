/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.file.service;

import com.xzixi.framework.boot.persistent.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import com.xzixi.framework.webapps.common.model.vo.AttachmentVO;

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
