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
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.model.valid.UserSave;
import com.xzixi.self.portal.webapp.model.valid.UserUpdate;
import com.xzixi.self.portal.webapp.model.vo.UserVO;
import com.xzixi.self.portal.webapp.service.IUserRoleLinkService;
import com.xzixi.self.portal.webapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.webapp.framework.constant.SecurityConstant.RESET_PASSWORD_KEY_EXPIRE_SECOND;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/user", produces="application/json; charset=UTF-8")
@Api(tags="用户")
@Validated
public class UserController {

    @Value("${reset-password-url}")
    private String resetPasswordUrl;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBelongingService belongingService;
    @Autowired
    private IUserRoleLinkService userRoleLinkService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping
    @ApiOperation(value = "分页查询用户")
    public Result<IPage<User>> page(UserSearchParams searchParams) {
        searchParams.setDefaultOrderItems(new String[]{"create_time desc"});
        IPage<User> page = userService.page(searchParams.buildPageParams(), searchParams.buildQueryWrapper());
        page.getRecords().forEach(user -> user.setPassword(null));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询用户")
    public Result<UserVO> getById(
            @ApiParam(value = "用户id", required = true) @NotNull(message = "用户id不能为空！") @PathVariable Integer id) {
        UserVO userVO = userService.buildUserVO(id);
        userVO.setPassword(null);
        return new Result<>(userVO);
    }

    @PostMapping
    @ApiOperation(value = "添加用户")
    public Result<?> save(@Validated({UserSave.class}) User user) {
        user.setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        if (!userService.saveUser(user)) {
            throw new ServerException();
        }
        return new Result<>();
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

    @PatchMapping("/lock")
    @ApiOperation(value = "锁定用户账户")
    public Result<?> lock(
            @ApiParam(value = "用户id", required = true) @NotEmpty(message = "用户id不能为空！") @RequestParam List<Integer> ids) {
        Collection<User> users = userService.listByIds(ids);
        if (CollectionUtils.isEmpty(users)) {
            return new Result<>();
        }
        // 锁定
        users.forEach(user -> user.setLocked(true));
        if (userService.updateBatchById(users)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @PatchMapping("/unlock")
    @ApiOperation(value = "解锁用户账户")
    public Result<?> unlock(
            @ApiParam(value = "用户id", required = true) @NotEmpty(message = "用户id不能为空！") @RequestParam List<Integer> ids) {
        Collection<User> users = userService.listByIds(ids);
        if (CollectionUtils.isEmpty(users)) {
            return new Result<>();
        }
        // 解锁
        users.forEach(user -> user.setLocked(false));
        if (userService.updateBatchById(users)) {
            return new Result<>();
        }
        throw new ServerException();
    }

    @DeleteMapping
    @ApiOperation(value = "删除用户账户")
    public Result<?> remove(
            @ApiParam(value = "用户id", required = true) @NotEmpty(message = "用户id不能为空！") @RequestParam List<Integer> ids) {
        if (userService.removeUsersByIds(ids)) {
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

    @GetMapping("/reset-password")
    @ApiOperation(value = "获取重置密码链接")
    public Result<?> generateResultPasswordUrl(
            @ApiParam(value = "用户名", required = true) @NotBlank(message = "用户名不能为空！") @RequestParam String username) {
        User user = userService.getOne(new QueryWrapper<>(new User().setUsername(username)));
        String key = UUID.randomUUID().toString();
        String url = String.format("%s?key=%s", resetPasswordUrl, key);
        // TODO 发送邮件
        // 将key保存到redis
        redisTemplate.boundValueOps(key).set(user.getId(), RESET_PASSWORD_KEY_EXPIRE_SECOND, TimeUnit.SECONDS);
        return new Result<>();
    }

    @PatchMapping("/reset-password")
    @ApiOperation(value = "重置账户密码")
    public Result<?> resetPassword(
            @ApiParam(value = "重置密码key", required = true) @NotBlank(message = "key不能为空！") @RequestParam String key,
            @ApiParam(value = "密码", required = true) @NotBlank(message = "密码不能为空！") @RequestParam String password) {
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

    @PostMapping("/{id}/role")
    @ApiOperation(value = "更新用户角色")
    public Result<?> updateUserRole(
            @ApiParam(value = "用户id", required = true) @NotNull(message = "用户id不能为空！") @PathVariable Integer id,
            @ApiParam(value = "角色id", required = true) @NotEmpty(message = "角色id不能为空！") @RequestParam List<Integer> roleIds) {
        List<UserRoleLink> newLinks = roleIds.stream().map(roleId -> new UserRoleLink(id, roleId)).collect(Collectors.toList());
        List<UserRoleLink> oldLinks = userRoleLinkService.list(new QueryWrapper<>(new UserRoleLink().setUserId(id)));
        boolean result = userRoleLinkService.merge(newLinks, oldLinks, (sources, target) -> sources.stream()
                .filter(source -> source.getRoleId() != null && source.getRoleId().equals(target.getRoleId()))
                .findFirst().orElse(null));
        if (result) {
            return new Result<>();
        }
        throw new ServerException();
    }
}
