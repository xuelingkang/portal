package com.xzixi.framework.backend.controller;

import com.xzixi.framework.common.model.enums.MailType;
import com.xzixi.framework.common.model.params.MailSearchParams;
import com.xzixi.framework.common.model.po.Mail;
import com.xzixi.framework.common.model.valid.MailSave;
import com.xzixi.framework.common.model.vo.MailVO;
import com.xzixi.framework.backend.util.SecurityUtils;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.backend.service.IMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.xzixi.framework.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/mail", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "邮件")
@Validated
public class MailController {

    @Autowired
    private IMailService mailService;

    @GetMapping
    @ApiOperation(value = "分页查询邮件")
    public Result<Pagination<MailVO>> page(MailSearchParams searchParams) {
        searchParams.setDefaultOrders("createTime desc");
        searchParams.getModel().setType(MailType.PUBLIC);
        Pagination<Mail> mailPage = mailService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        Pagination<MailVO> page = mailService.buildVO(mailPage, new MailVO.BuildOption(true, true, false, false));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询邮件")
    public Result<MailVO> getById(
        @ApiParam(value = "邮件id", required = true) @NotNull(message = "邮件id不能为空！") @PathVariable Integer id) {
        MailVO mailVO = mailService.buildVO(id, new MailVO.BuildOption(true, true, true, true));
        return new Result<>(mailVO);
    }

    @PostMapping
    @ApiOperation(value = "发送邮件")
    public Result<?> send(@Validated({MailSave.class}) @RequestBody MailVO mailVO) {
        mailVO.setSendUserId(SecurityUtils.getCurrentUserId());
        mailVO.setType(MailType.PUBLIC);
        mailVO.setCreateTime(System.currentTimeMillis());
        mailService.saveMail(mailVO, mailVO.getContent());
        mailService.send(mailVO, mailVO.getContent());
        return new Result<>();
    }

    @DeleteMapping
    @ApiOperation(value = "删除邮件")
    public Result<?> remove(
        @ApiParam(value = "邮件id", required = true) @NotEmpty(message = "邮件id不能为空！") @RequestParam List<Integer> ids) {
        mailService.removeMailsByIds(ids);
        return new Result<>();
    }
}
