package com.xzixi.framework.backend.controller;

import com.xzixi.framework.common.model.enums.MailType;
import com.xzixi.framework.common.model.params.UserSearchParams;
import com.xzixi.framework.common.model.po.Mail;
import com.xzixi.framework.common.model.po.MailContent;
import com.xzixi.framework.common.model.po.User;
import com.xzixi.framework.common.model.po.UserRoleLink;
import com.xzixi.framework.common.model.valid.UserSave;
import com.xzixi.framework.common.model.valid.UserUpdate;
import com.xzixi.framework.common.model.vo.UserVO;
import com.xzixi.framework.backend.util.SecurityUtils;
import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.Result;
import com.xzixi.framework.boot.webmvc.model.search.Pagination;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.IBelongingService;
import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.backend.service.IMailService;
import com.xzixi.framework.backend.service.IUserRoleLinkService;
import com.xzixi.framework.backend.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.xzixi.framework.common.constant.ControllerConstant.RESPONSE_MEDIA_TYPE;
import static com.xzixi.framework.common.constant.SecurityConstant.*;
import static com.xzixi.framework.common.constant.UserConstant.*;

/**
 * @author 薛凌康
 */
@RestController
@RequestMapping(value = "/user", produces = RESPONSE_MEDIA_TYPE)
@Api(tags = "用户")
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
    private IMailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping
    @ApiOperation(value = "分页查询用户")
    public Result<Pagination<UserVO>> page(UserSearchParams searchParams) {
        searchParams.setDefaultOrders("createTime desc");
        Pagination<User> userPage = userService.page(searchParams.buildPagination(), searchParams.buildQueryParams());
        userPage.getRecords().forEach(user -> user.setPassword(null));
        Pagination<UserVO> page = userService.buildVO(userPage, new UserVO.BuildOption(false, false));
        return new Result<>(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询用户")
    public Result<UserVO> getById(
            @ApiParam(value = "用户id", required = true) @NotNull(message = "用户id不能为空！") @PathVariable Integer id) {
        UserVO userVO = userService.buildVO(id, new UserVO.BuildOption(true, true));
        userVO.setPassword(null);
        return new Result<>(userVO);
    }

    @PostMapping
    @ApiOperation(value = "保存用户")
    public Result<?> save(@Validated({UserSave.class}) User user) {
        user.setCreateTime(System.currentTimeMillis())
                .setLoginTime(null).setLocked(false).setActivated(false).setDeleted(false);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户
        userService.saveUser(user);
        // 发送激活链接邮件
        userService.sendActivateUserMail(user);
        return new Result<>().setMessage(String.format(USER_ACTIVATE_MESSAGE_TEMPLATE, user.getEmail(), USER_ACTIVATE_EXPIRE_DAY));
    }

    @PutMapping
    @ApiOperation(value = "更新用户")
    public Result<?> update(@Validated({UserUpdate.class}) User user) {
        User userData = userService.getById(user.getId());
        // 清除不可更新的属性
        String[] ignoreProperties = {"createTime", "loginTime", "locked", "deleted", "password", "type"};
        BeanUtils.copyPropertiesIgnoreNull(user, userData, ignoreProperties);
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException(user, "更新用户失败！");
    }

    @PutMapping("/personal")
    @ApiOperation(value = "更新个人信息")
    public Result<?> selfUpdate(@Validated({UserUpdate.class}) User user) {
        belongingService.checkOwner(user);
        User userData = userService.getById(user.getId());
        // 清除不可更新的属性
        String[] ignoreProperties = {"createTime", "loginTime", "locked", "deleted", "password", "type"};
        BeanUtils.copyPropertiesIgnoreNull(user, userData, ignoreProperties);
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException(user, "更新个人信息失败！");
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
        if (userService.defaultUpdateBatchById(users)) {
            return new Result<>();
        }
        throw new ServerException(ids, "锁定用户账户失败！");
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
        if (userService.defaultUpdateBatchById(users)) {
            return new Result<>();
        }
        throw new ServerException(ids, "解锁用户账户失败！");
    }

    @DeleteMapping
    @ApiOperation(value = "删除用户账户")
    public Result<?> remove(
            @ApiParam(value = "用户id", required = true) @NotEmpty(message = "用户id不能为空！") @RequestParam List<Integer> ids) {
        userService.removeUsersByIds(ids);
        return new Result<>();
    }

    @PatchMapping("/password")
    @ApiOperation(value = "修改用户账户密码")
    public Result<?> updatePassword(@Validated({UserUpdate.class}) User user) {
        User userData = userService.getById(user.getId());
        userData.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.updateById(userData)) {
            return new Result<>();
        }
        throw new ServerException(user, "修改用户账户密码失败！");
    }

    @PatchMapping("/personal/password")
    @ApiOperation(value = "修改个人账户密码")
    public Result<?> updatePersonalPassword(
            @ApiParam(value = "原密码", required = true) @NotBlank(message = "原密码不能为空！") @RequestParam String originalPwd,
            @ApiParam(value = "新密码", required = true) @NotBlank(message = "新密码不能为空！") @RequestParam String password) {
        User user = SecurityUtils.getCurrentUser();
        if (!passwordEncoder.matches(originalPwd, user.getPassword())) {
            throw new ClientException(400, "原密码错误！");
        }
        user.setPassword(passwordEncoder.encode(password));
        if (userService.updateById(user)) {
            return new Result<>();
        }
        throw new ServerException(user, "修改个人账户密码失败！");
    }

    @GetMapping("/reset-password")
    @ApiOperation(value = "获取重置密码链接")
    public Result<?> generateResultPasswordUrl(
            @ApiParam(value = "用户名", required = true) @NotBlank(message = "用户名不能为空！") @RequestParam String username) {
        Long time = (Long) redisTemplate.opsForValue().get(String.format(RESET_PASSWORD_KEY_TEMPLATE, username));
        long now = System.currentTimeMillis();
        if (time != null && (time + RESET_PASSWORD_RETRY_TIMEOUT_MILLISECOND) > now) {
            throw new ClientException(403, "操作过快，请稍后再试！");
        }
        User user = userService.getOne(new QueryParams<>(new User().setUsername(username)));
        String key = UUID.randomUUID().toString();
        String url = String.format("%s?key=%s", resetPasswordUrl, key);
        // 发送邮件
        Mail mail = new Mail();
        mail.setSubject(RESET_PASSWORD_MAIL_TITLE);
        mail.setType(MailType.PRIVATE);
        mail.setCreateTime(now);
        mail.setToUserIds(Collections.singletonList(user.getId()));
        MailContent mailContent = new MailContent();
        String content = String.format(RESET_PASSWORD_MAIL_CONTENT_TEMPLATE, user.getUsername(), url, url, RESET_PASSWORD_KEY_EXPIRE_MINUTE);
        mailContent.setContent(content);
        mailService.saveMail(mail, mailContent);
        mailService.send(mail, mailContent);
        // 将key保存到redis
        redisTemplate.boundValueOps(String.format(RESET_PASSWORD_KEY_TEMPLATE, username))
                .set(now, RESET_PASSWORD_RETRY_TIMEOUT_SECOND, TimeUnit.SECONDS);
        redisTemplate.boundValueOps(String.format(RESET_PASSWORD_KEY_TEMPLATE, key))
                .set(user.getId(), RESET_PASSWORD_KEY_EXPIRE_SECOND, TimeUnit.SECONDS);
        return new Result<>().setMessage(String.format(RESET_PASSWORD_MESSAGE_TEMPLATE, user.getEmail(), RESET_PASSWORD_KEY_EXPIRE_MINUTE));
    }

    @PatchMapping("/reset-password")
    @ApiOperation(value = "重置账户密码")
    public Result<?> resetPassword(
            @ApiParam(value = "重置密码key", required = true) @NotBlank(message = "重置密码key不能为空！") @RequestParam String key,
            @ApiParam(value = "密码", required = true) @NotBlank(message = "密码不能为空！") @RequestParam String password) {
        String cacheKey = String.format(RESET_PASSWORD_KEY_TEMPLATE, key);
        Integer id = (Integer) redisTemplate.opsForValue().get(cacheKey);
        if (id == null) {
            throw new ClientException(404, "链接已经失效！");
        }
        User userData = userService.getById(id);
        userData.setPassword(passwordEncoder.encode(password));
        if (userService.updateById(userData)) {
            redisTemplate.delete(cacheKey);
            return new Result<>();
        }
        throw new ServerException(String.format("key = %s, password = %s", key, password), "修改个人账户密码失败！");
    }

    @PatchMapping("/activate")
    @ApiOperation(value = "激活账户")
    public Result<?> activate(
            @ApiParam(value = "激活账户key", required = true) @NotBlank(message = "激活账户key不能为空！") @RequestParam String key) {
        String cacheKey = String.format(USER_ACTIVATE_KEY_TEMPLATE, key);
        Integer id = (Integer) redisTemplate.opsForValue().get(cacheKey);
        if (id == null) {
            throw new ClientException(404, "链接已经失效！");
        }
        User userData = userService.getById(id);
        userData.setActivated(true);
        if (userService.updateById(userData)) {
            redisTemplate.delete(cacheKey);
            return new Result<>();
        }
        throw new ServerException(String.format("key = %s", key), "激活账户失败！");
    }

    @GetMapping("/rebind-email-code")
    @ApiOperation(value = "生成重新绑定邮箱验证码")
    public Result<?> rebindEmailCode(
            @ApiParam(value = "新邮箱", required = true) @NotBlank(message = "邮箱不能为空！")
            @Email(message = "邮箱格式不正确！") @Length(max = 50, message = "邮箱不能大于50字！")
            @RequestParam String email) {
        User user = SecurityUtils.getCurrentUser();
        Long time = (Long) redisTemplate.opsForValue().get(String.format(REBIND_EMAIL_CODE_RETRY_TIMEOUT_KEY_TEMPLATE, user.getId()));
        long now = System.currentTimeMillis();
        if (time != null && (time + REBIND_EMAIL_CODE_RETRY_TIMEOUT_MILLISECOND) > now) {
            throw new ClientException(403, "操作过快，请稍后再试！");
        }
        String code = RandomStringUtils.randomAlphanumeric(REBIND_EMAIL_CODE_LENGTH);
        // 发送邮件
        Mail mail = new Mail();
        mail.setSubject(REBIND_EMAIL_CODE_MAIL_TITLE);
        mail.setType(MailType.PRIVATE);
        mail.setCreateTime(now);
        mail.setToEmail(email);
        MailContent mailContent = new MailContent();
        String content = String.format(REBIND_EMAIL_CODE_MAIL_CONTENT_TEMPLATE, user.getUsername(), code, REBIND_EMAIL_CODE_KEY_EXPIRE_MINUTE);
        mailContent.setContent(content);
        mailService.saveMail(mail, mailContent);
        mailService.send(mail, mailContent);
        // 将验证码保存到redis
        redisTemplate.boundValueOps(String.format(REBIND_EMAIL_CODE_RETRY_TIMEOUT_KEY_TEMPLATE, user.getId()))
                .set(now, REBIND_EMAIL_CODE_RETRY_TIMEOUT_SECOND, TimeUnit.SECONDS);
        redisTemplate.boundValueOps(String.format(REBIND_EMAIL_CODE_KEY_TEMPLATE, user.getId(), email))
                .set(code, REBIND_EMAIL_CODE_KEY_EXPIRE_SECOND, TimeUnit.SECONDS);
        return new Result<>().setMessage(String.format(REBIND_EMAIL_MESSAGE_TEMPLATE, email, REBIND_EMAIL_CODE_KEY_EXPIRE_MINUTE));
    }

    @PatchMapping("/rebind-email")
    @ApiOperation(value = "重新绑定邮箱")
    public Result<?> rebindEmail(
            @ApiParam(value = "验证码", required = true) @NotBlank(message = "验证码不能为空！") @RequestParam String code,
            @ApiParam(value = "新邮箱", required = true) @NotBlank(message = "邮箱不能为空！")
            @Email(message = "邮箱格式不正确！") @Length(max = 50, message = "邮箱不能大于50字！")
            @RequestParam String email) {
        User user = SecurityUtils.getCurrentUser();
        String cacheKey = String.format(REBIND_EMAIL_CODE_KEY_TEMPLATE, user.getId(), email);
        String cacheCode = (String) redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isEmpty(cacheCode)) {
            throw new ClientException(404, "验证码已经失效！");
        }
        if (!Objects.equals(cacheCode, code)) {
            throw new ClientException(400, "验证码错误！");
        }
        user.setEmail(email);
        if (userService.updateById(user)) {
            redisTemplate.delete(cacheKey);
            return new Result<>();
        }
        throw new ServerException(String.format("email = %s", email), "重新绑定邮箱失败！");
    }

    @PostMapping("/{id}/role")
    @ApiOperation(value = "更新用户角色")
    public Result<?> updateUserRole(
            @ApiParam(value = "用户id", required = true) @NotNull(message = "用户id不能为空！") @PathVariable Integer id,
            @ApiParam(value = "角色id", required = true) @NotEmpty(message = "角色id不能为空！") @RequestParam List<Integer> roleIds) {
        List<UserRoleLink> newLinks = roleIds.stream().map(roleId -> new UserRoleLink(id, roleId)).collect(Collectors.toList());
        List<UserRoleLink> oldLinks = userRoleLinkService.list(new QueryParams<>(new UserRoleLink().setUserId(id)));
        boolean result = userRoleLinkService.merge(newLinks, oldLinks, (sources, target) -> sources.stream()
                .filter(source -> Objects.equals(source.getRoleId(), target.getRoleId()))
                .findFirst().orElse(null));
        if (result) {
            return new Result<>();
        }
        throw new ServerException(newLinks, "更新用户角色失败！");
    }
}
