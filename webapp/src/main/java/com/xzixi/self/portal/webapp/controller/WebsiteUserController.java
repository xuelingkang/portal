package com.xzixi.self.portal.webapp.controller;

import com.xzixi.self.portal.framework.webmvc.model.Result;
import com.xzixi.self.portal.webapp.model.enums.UserType;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.valid.WebsiteUserSave;
import com.xzixi.self.portal.webapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xzixi.self.portal.webapp.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;
import static com.xzixi.self.portal.webapp.constant.UserConstant.USER_ACTIVATE_EXPIRE_DAY;
import static com.xzixi.self.portal.webapp.constant.UserConstant.USER_ACTIVATE_MESSAGE_TEMPLATE;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/website/user", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "网站用户")
public class WebsiteUserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "注册")
    public Result<?> save(@Validated({WebsiteUserSave.class}) User user) {
        user.setType(UserType.WEBSITE).setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setActivated(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        userService.saveUser(user);
        // 发送激活链接邮件
        userService.sendActivateUserMail(user);
        return new Result<>().setMessage(String.format(USER_ACTIVATE_MESSAGE_TEMPLATE, user.getEmail(), USER_ACTIVATE_EXPIRE_DAY));
    }
}
