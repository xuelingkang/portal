package com.xzixi.self.portal.webapp.controller;

import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.model.enums.UserType;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.valid.WebsiteUserSave;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/website/user", produces="application/json; charset=UTF-8")
@Api(tags="网站用户")
public class WebsiteUserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "注册")
    public Result<UserVO> save(@Validated({WebsiteUserSave.class}) User user) {
        user.setType(UserType.WEBSITE).setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        userService.saveUser(user);
        // 构建UserVO
        UserVO userVO = userService.buildUserVO(user);
        userVO.setPassword(null);
        return new Result<UserVO>().setData(userVO);
    }
}
