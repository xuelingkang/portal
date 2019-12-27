package com.xzixi.self.portal.webapp.controller;

import com.xzixi.self.portal.sftp.pool.component.SftpClient;
import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.framework.util.FileUtil;
import com.xzixi.self.portal.webapp.model.enums.AttachmentType;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.vo.AttachmentVO;
import com.xzixi.self.portal.webapp.service.IAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.xzixi.self.portal.webapp.framework.constant.AttachmentConstant.*;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/attachment", produces = "application/json; charset=UTF-8")
@Api(tags = "附件")
@Validated
public class AttachmentController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(String.format("yyyy%sMM", SEPARATOR));
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private SftpClient sftpClient;

    @PostMapping("/{type}")
    @ApiOperation(value = "上传附件")
    public Result<AttachmentVO> upload(
            @ApiParam(value = "文件", required = true) @NotNull(message = "文件不能为空！") @RequestParam MultipartFile file,
            @ApiParam(value = "文件类型", required = true) @NotNull(message = "文件类型不能为空！") @PathVariable AttachmentType type,
            @ApiParam(value = "是否重命名", required = true) @NotNull(message = "是否重命名不能为空！") @RequestParam boolean rename) {
        String originalFilename = file.getOriginalFilename();
        // 处理文件名
        String name;
        if (!rename && StringUtils.isNotBlank(originalFilename)) {
            name = originalFilename;
        } else {
            String exp = FileUtil.getExp(originalFilename);
            if (StringUtils.isNotBlank(exp)) {
                name = FileUtil.getRandomName() + exp;
            } else {
                name = FileUtil.getRandomName();
            }
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
        AttachmentVO attachmentVO = attachmentService.buildAttachmentVO(attachment);
        return new Result<>(attachmentVO);
    }
}