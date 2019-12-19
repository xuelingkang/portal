package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.model.enums.UserType;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.valid.UserSave;
import com.xzixi.self.portal.webapp.model.valid.UserLogon;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IRoleService;
import com.xzixi.self.portal.webapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/user", produces="application/json; charset=UTF-8")
@Api(tags="用户")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/website")
    @ApiOperation(value = "注册")
    public Result<UserVO> save(@Validated({UserLogon.class}) User user) {
        user.setType(UserType.WEBSITE).setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 初始角色
        List<Role> initialRoles = roleService.list(new QueryWrapper<>(new Role().setInitial(true)));
        // 保存用户
        userService.save(user, initialRoles);
        // 构建UserVO
        UserVO userVO = userService.buildUserVO(user);
        userVO = userVO.ignoreProperties("password");
        return new Result<UserVO>().setData(userVO);
    }

    @PostMapping
    @ApiOperation(value = "添加用户")
    public Result<UserVO> save(@Validated({UserSave.class}) UserVO user) {
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
