package com.xzixi.self.portal.webapp.controller;

import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.model.valid.SystemUserInsert;
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
@RequestMapping(value = "/system/user", produces="application/json; charset=UTF-8")
@Api(tags="系统用户")
public class SystemUserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "添加用户")
    public Result<UserVO> save(@Validated({SystemUserInsert.class}) UserVO user) {
        user.setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        userService.save(user, user.getRoles());
        // 构建UserVO
        UserVO userVO = userService.buildUserVO(user);
        userVO = userVO.ignoreProperties("password");
        return new Result<UserVO>().setData(userVO);
    }
}
