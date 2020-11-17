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

package com.xzixi.framework.webapps.common.model.params;

import com.xzixi.framework.boot.core.model.search.BaseSearchParams;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "附件查询参数")
public class AttachmentSearchParams extends BaseSearchParams<Attachment> {
}
