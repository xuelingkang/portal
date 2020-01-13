package com.xzixi.self.portal.webapp.controller;

import com.xzixi.self.portal.framework.model.Result;
import com.xzixi.self.portal.framework.model.search.Pagination;
import com.xzixi.self.portal.webapp.model.enums.MailType;
import com.xzixi.self.portal.webapp.model.params.MailSearchParams;
import com.xzixi.self.portal.webapp.model.po.Mail;
import com.xzixi.self.portal.webapp.model.valid.MailSave;
import com.xzixi.self.portal.webapp.model.vo.MailVO;
import com.xzixi.self.portal.webapp.service.IMailService;
import com.xzixi.self.portal.webapp.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.xzixi.self.portal.webapp.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

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
        searchParams.setDefaultOrders("create_time desc");
        searchParams.getEntity().setType(MailType.PUBLIC);
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
        mailVO.setSendUserId(SecurityUtil.getCurrentUserId());
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
