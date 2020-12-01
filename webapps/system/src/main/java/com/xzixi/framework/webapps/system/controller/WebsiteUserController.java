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

package com.xzixi.framework.webapps.system.controller;

import com.xzixi.framework.boot.core.model.Result;
import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.webapps.common.model.valid.WebsiteUserSave;
import com.xzixi.framework.webapps.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xzixi.framework.webapps.common.constant.ProjectConstant.RESPONSE_MEDIA_TYPE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/website/user", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "网站用户")
public class WebsiteUserController {

    @Autowired
    private IUserService userService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "注册")
    public Result<?> save(@Validated({WebsiteUserSave.class}) User user) {
//        user.setType(UserType.WEBSITE).setCreateTime(System.currentTimeMillis())
//                .setLoginTime(null).setLocked(false).setActivated(false).setDeleted(false);
//        // 加密密码
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        // 保存用户
//        userService.saveUser(user);
//        // 发送激活链接邮件
//        userService.sendActivateUserMail(user);
//        return new Result<>().setMessage(String.format(USER_ACTIVATE_MESSAGE_TEMPLATE, user.getEmail(), USER_ACTIVATE_EXPIRE_DAY));
        return null;
    }
}
