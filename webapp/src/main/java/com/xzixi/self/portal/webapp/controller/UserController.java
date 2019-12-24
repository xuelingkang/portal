package com.xzixi.self.portal.webapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.framework.exception.ServerException;
import com.xzixi.self.portal.webapp.framework.model.Result;
import com.xzixi.self.portal.webapp.framework.service.IBelongingService;
import com.xzixi.self.portal.webapp.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.params.UserSearchParams;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.valid.UserSave;
import com.xzixi.self.portal.webapp.model.valid.UserUpdate;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xzixi.self.portal.webapp.framework.constant.SecurityConstant.RESET_PASSWORD_URL_EXPIRE_SECOND;
import static com.xzixi.self.portal.webapp.framework.constant.SecurityConstant.RESET_PASSWORD_URL_PREFIX;

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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private IBelongingService belongingService;

    @GetMapping
    @ApiOperation(value = "分页查询用户")
    public Result<IPage<User>> page(UserSearchParams searchParams) {
        searchParams.setDefaultOrderItems(new String[]{"create_time false"});
        IPage<User> page = userService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        page.getRecords().forEach(user -> user.setPassword(null));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询用户")
    public Result<User> getById(@PathVariable @NotNull(message = "用户id不能为空！") Integer id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return new Result<>(user);
    }

    @PostMapping
    @ApiOperation(value = "添加用户")
    public Result<UserVO> save(@Validated({UserSave.class}) User user) {
        user.setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        if (!userService.saveUser(user)) {
            throw new ServerException();
        }
        // 构建UserVO
        UserVO userVO = userService.buildUserVO(user);
        userVO.setPassword(null);
        return new Result<>(userVO);
    }

    @PutMapping
    @ApiOperation(value = "更新用户")
    public Result<?> update(@Validated({UserUpdate.class}) User user) {
        User userData = userService.getById(user.getId());
        // 清除不可更新的属性
        String[] ignoreProperties = {"createTime", "loginTime", "locked", "deleted", "password", "type"};
        BeanUtils.copyProperties(user, userData, ignoreProperties);
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @PutMapping("/personal")
    @ApiOperation(value = "更新个人信息")
    public Result<?> selfUpdate(@Validated({UserUpdate.class}) User user) {
        belongingService.checkOwner(user);
        User userData = userService.getById(user.getId());
        // 清除不可更新的属性
        String[] ignoreProperties = {"createTime", "loginTime", "locked", "deleted", "password", "type"};
        BeanUtils.copyProperties(user, userData, ignoreProperties);
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @PatchMapping("/{id}/lock")
    @ApiOperation(value = "锁定用户账户")
    public Result<?> lock(@PathVariable @NotNull(message = "用户id不能为空！") Integer id) {
        User user = userService.getById(id);
        // 锁定
        user.setLocked(true);
        if (userService.updateById(user)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @PatchMapping("/{id}/unlock")
    @ApiOperation(value = "解锁用户账户")
    public Result<?> unlock(@PathVariable @NotNull(message = "用户id不能为空！") Integer id) {
        User user = userService.getById(id);
        // 解锁
        user.setLocked(false);
        if (userService.updateById(user)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户账户")
    public Result<?> delete(@PathVariable @NotNull(message = "用户id不能为空！") Integer id) {
        userService.getById(id);
        if (userService.removeById(id)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @PatchMapping("/password")
    @ApiOperation(value = "修改用户账户密码")
    public Result<?> updatePassword(@Validated({UserUpdate.class}) User user) {
        User userData = userService.getById(user.getId());
        userData.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @PatchMapping("/personal/password")
    @ApiOperation(value = "修改个人账户密码")
    public Result<?> updatePersonalPassword(@Validated({UserUpdate.class}) User user) {
        belongingService.checkOwner(user);
        User userData = userService.getById(user.getId());
        userData.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @GetMapping("/reset-password-url")
    @ApiOperation(value = "获取重置密码链接")
    public Result<?> generateResultPasswordUrl(@NotBlank(message = "用户名不能为空！") String username) {
        User user = userService.getOne(new QueryWrapper<>(new User().setUsername(username)));
        String key = UUID.randomUUID().toString();
        String url = String.format("%s?key=%s", RESET_PASSWORD_URL_PREFIX, key);
        // TODO 发送邮件
        redisTemplate.boundValueOps(key).set(user.getId(), RESET_PASSWORD_URL_EXPIRE_SECOND, TimeUnit.SECONDS);
        return new Result<>();
    }

    @PatchMapping("/reset-password")
    @ApiOperation(value = "重置账户密码")
    public Result<?> resetPassword(@NotBlank(message = "key不能为空！") String key,
                                   @NotBlank(message = "密码不能为空！") String password) {
        Integer id = (Integer) redisTemplate.opsForValue().get(key);
        if (id == null) {
            throw new LogicException(404, "key已经失效！");
        }
        User userData = userService.getById(id);
        userData.setPassword(passwordEncoder.encode(password));
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException();
    }
}
