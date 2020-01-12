package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.framework.model.Result;
import com.xzixi.self.portal.sftp.client.component.ISftpClient;
import com.xzixi.self.portal.webapp.model.enums.AttachmentType;
import com.xzixi.self.portal.webapp.model.params.AttachmentSearchParams;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.vo.AttachmentVO;
import com.xzixi.self.portal.webapp.service.IAttachmentService;
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

import static com.xzixi.self.portal.webapp.constant.AttachmentConstant.*;
import static com.xzixi.self.portal.webapp.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

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
            @ApiParam(value = "文件类型", required = true) @NotNull(message = "文件类型不能为空！") @PathVariable AttachmentType type,
            @ApiParam(value = "是否重命名", required = true) @NotNull(message = "是否重命名不能为空！") @RequestParam boolean rename) {
        String originalFilename = file.getOriginalFilename();
        // 处理文件名
        String name;
        if (!rename && StringUtils.isNotBlank(originalFilename)) {
            name = originalFilename;
        } else {
            String exp = FilenameUtils.getExtension(originalFilename);
            if (StringUtils.isNotBlank(exp)) {
                name = genRandomName() + "." + exp;
            } else {
                name = genRandomName();
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
        AttachmentVO attachmentVO = attachmentService.buildVO(attachment, new AttachmentVO.BuildOption());
        return new Result<>(attachmentVO);
    }

    @GetMapping
    @ApiOperation(value = "分页查询附件")
    public Result<IPage<AttachmentVO>> page(AttachmentSearchParams searchParams) {
        searchParams.setDefaultOrderItems("create_time desc");
        IPage<Attachment> attachmentPage = attachmentService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        IPage<AttachmentVO> page = attachmentService.buildVO(attachmentPage, new AttachmentVO.BuildOption());
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
