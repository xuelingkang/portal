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

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "附件")
public class AttachmentVO extends Attachment {

    private static final long serialVersionUID = 1L;

    public AttachmentVO(Attachment attachment, String... ignoreProperties) {
        BeanUtils.copyProperties(attachment, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
    }
}
