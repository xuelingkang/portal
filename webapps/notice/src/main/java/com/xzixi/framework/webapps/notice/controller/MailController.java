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

package com.xzixi.framework.webapps.notice.controller;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.boot.core.model.search.Pagination;
import com.xzixi.framework.webapps.common.model.enums.MailType;
import com.xzixi.framework.webapps.common.model.params.MailSearchParams;
import com.xzixi.framework.webapps.common.model.po.Mail;
import com.xzixi.framework.webapps.common.model.valid.MailSave;
import com.xzixi.framework.webapps.common.model.vo.MailVO;
import com.xzixi.framework.webapps.notice.service.IMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.xzixi.framework.webapps.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;

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
//        mailVO.setSendUserId(SecurityUtils.getCurrentUserId());
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
