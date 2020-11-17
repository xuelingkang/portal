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

package com.xzixi.framework.webapps.common.feign;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.webapps.common.model.enums.AttachmentType;
import com.xzixi.framework.webapps.common.model.params.AttachmentSearchParams;
import com.xzixi.framework.webapps.common.model.vo.AttachmentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@FeignClient(value = "portal-file", path = "/attachment", contextId = "attachment")
public interface RemoteAttachmentService {

    @PostMapping("/{type}")
    Result<AttachmentVO> upload(MultipartFile file, @PathVariable("type") AttachmentType type);

    @GetMapping
    Result<Pagination<AttachmentVO>> page(AttachmentSearchParams searchParams);

    @GetMapping("/{id}")
    Result<AttachmentVO> getById(@PathVariable("id") Integer id);

    @DeleteMapping
    Result<?> remove(List<Integer> ids);
}
