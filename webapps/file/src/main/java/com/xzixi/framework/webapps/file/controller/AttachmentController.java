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

package com.xzixi.framework.webapps.file.controller;

import com.xzixi.framework.boot.sftp.client.component.ISftpClient;
import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.webapps.common.model.enums.AttachmentType;
import com.xzixi.framework.webapps.common.model.params.AttachmentSearchParams;
import com.xzixi.framework.webapps.common.model.po.Attachment;
import com.xzixi.framework.webapps.common.model.vo.AttachmentVO;
import com.xzixi.framework.webapps.file.service.IAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.xzixi.framework.webapps.common.constant.AttachmentConstant.*;
import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/attachment", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "附件")
@Validated
public class AttachmentController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(String.format("yyyy%sMM", SEPARATOR));
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private ISftpClient sftpClient;

    @PostMapping("/{type}")
    @ApiOperation(value = "上传附件")
    public Result<AttachmentVO> upload(
            @ApiParam(value = "文件", required = true) @NotNull(message = "文件不能为空！") @RequestParam MultipartFile file,
            @ApiParam(value = "文件类型", required = true) @NotNull(message = "文件类型不能为空！") @PathVariable AttachmentType type) {
        String originalFilename = file.getOriginalFilename();
        // 处理文件名
        String name;
        String ext = FilenameUtils.getExtension(originalFilename);
        if (StringUtils.isNotBlank(ext)) {
            name = genRandomName() + "." + ext;
        } else {
            name = genRandomName();
        }
        // type小写
        String lowerCaseType = type.name().toLowerCase();
        // 相对路径
        String relativePath = lowerCaseType + SEPARATOR + FORMATTER.format(LocalDate.now());
        // 目录绝对路径
        String absoluteAddress = BASE_ADDRESS + SEPARATOR + relativePath;
        // 使用sftp上传到文件服务器
        sftpClient.open(sftp -> sftp.upload(absoluteAddress, name, file.getInputStream()));
        // 将附件信息保存到数据库
        Attachment attachment = new Attachment();
        attachment.setType(type);
        attachment.setName(originalFilename != null? originalFilename: DEFAULT_NAME);
        attachment.setUrl(BASE_URL + SEPARATOR + relativePath + SEPARATOR + name);
        attachment.setAddress(absoluteAddress + SEPARATOR + name);
        attachment.setCreateTime(System.currentTimeMillis());
        attachmentService.save(attachment);
        AttachmentVO attachmentVO = attachmentService.buildVO(attachment, new AttachmentVO.BuildOption());
        return new Result<>(attachmentVO);
    }

    @GetMapping
    @ApiOperation(value = "分页查询附件")
    public Result<Pagination<AttachmentVO>> page(AttachmentSearchParams searchParams) {
        searchParams.setDefaultOrders("createTime desc");
        Pagination<Attachment> attachmentPage = attachmentService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        Pagination<AttachmentVO> page = attachmentService.buildVO(attachmentPage, new AttachmentVO.BuildOption());
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询附件")
    public Result<AttachmentVO> getById(
        @ApiParam(value = "附件id", required = true) @NotNull(message = "附件id不能为空！") @PathVariable Integer id) {
        AttachmentVO attachmentVO = attachmentService.buildVO(id, new AttachmentVO.BuildOption());
        return new Result<>(attachmentVO);
    }

    @DeleteMapping
    @ApiOperation(value = "删除附件")
    public Result<?> remove(
        @ApiParam(value = "附件id", required = true) @NotEmpty(message = "附件id不能为空！") @RequestParam List<Integer> ids) {
        attachmentService.removeAttachmentsByIds(ids);
        return new Result<>();
    }

    private String genRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
