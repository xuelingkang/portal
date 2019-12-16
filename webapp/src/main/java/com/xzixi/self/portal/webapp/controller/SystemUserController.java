package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.exception.LogicException;
import com.xzixi.self.portal.webapp.group.SystemUserInsert;
import com.xzixi.self.portal.webapp.model.Result;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.business.IUserBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping(value = "/system/user", produces="application/json; charset=UTF-8")
@Api(tags="系统用户")
public class SystemUserController {

    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "添加用户")
    public Result<UserVO> save(@Validated({SystemUserInsert.class}) UserVO user) {
        List<User> usersByUsername = userBusiness.list(new QueryWrapper<>(new User().setUsername(user.getUsername())));
        if (CollectionUtils.isNotEmpty(usersByUsername)) {
            throw new LogicException(400, "用户名重复！");
        }
        List<User> usersByEmail = userBusiness.list(new QueryWrapper<>(new User().setEmail(user.getEmail())));
        if (CollectionUtils.isNotEmpty(usersByEmail)) {
            throw new LogicException(400, "邮箱重复！");
        }
        user.setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        userBusiness.save(user, user.getRoles());

        return new Result<UserVO>().setData(userBusiness.buildUserVO(user));
    }
}
