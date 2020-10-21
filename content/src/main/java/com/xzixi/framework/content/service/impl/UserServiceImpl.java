package com.xzixi.framework.content.service.impl;

import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.exception.ServerException;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.common.constant.UserConstant;
import com.xzixi.framework.content.data.IUserData;
import com.xzixi.framework.common.model.enums.MailType;
import com.xzixi.framework.common.model.po.*;
import com.xzixi.framework.common.model.vo.UserVO;
import com.xzixi.framework.content.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<IUserData, User> implements IUserService {

    @Value("${user-activate-url}")
    private String userActivateUrl;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private IUserRoleLinkService userRoleLinkService;
    @Autowired
    private IMailService mailService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void checkUsername(String username) {
        List<User> usersByUsername = list(new QueryParams<>(new User().setUsername(username)));
        if (CollectionUtils.isNotEmpty(usersByUsername)) {
            throw new ClientException(400, "用户名重复！");
        }
    }

    @Override
    public void checkEmail(String email) {
        List<User> usersByEmail = list(new QueryParams<>(new User().setEmail(email)));
        if (CollectionUtils.isNotEmpty(usersByEmail)) {
            throw new ClientException(400, "邮箱重复！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        // 检查属性
        checkUsername(user.getUsername());
        checkEmail(user.getEmail());
        // 保存用户
        if (!save(user)) {
            throw new ServerException(user, "保存用户失败！");
        }
    }

    @Override
    public void sendActivateUserMail(User user) {
        String key = UUID.randomUUID().toString();
        String url = String.format("%s?key=%s", userActivateUrl, key);
        long now = System.currentTimeMillis();
        // 发送邮件
        Mail mail = new Mail();
        mail.setSubject(UserConstant.USER_ACTIVATE_MAIL_TITLE);
        mail.setType(MailType.PRIVATE);
        mail.setCreateTime(now);
        mail.setToUserIds(Collections.singletonList(user.getId()));
        MailContent mailContent = new MailContent();
        String content = String.format(UserConstant.USER_ACTIVATE_MAIL_CONTENT_TEMPLATE, user.getUsername(), url, url, UserConstant.USER_ACTIVATE_EXPIRE_DAY);
        mailContent.setContent(content);
        mailService.saveMail(mail, mailContent);
        mailService.send(mail, mailContent);
        // 将key保存到redis
        redisTemplate.boundValueOps(String.format(UserConstant.USER_ACTIVATE_KEY_TEMPLATE, key))
                .set(user.getId(), UserConstant.USER_ACTIVATE_EXPIRE_SECOND, TimeUnit.SECONDS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsersByIds(Collection<Integer> ids) {
        if (!removeByIds(ids)) {
            throw new ServerException(ids, "删除用户失败！");
        }

        List<UserRoleLink> userRoleLinks = userRoleLinkService.listByUserIds(ids);
        if (CollectionUtils.isNotEmpty(userRoleLinks)) {
            List<Integer> linkIds = userRoleLinks.stream().map(UserRoleLink::getId).collect(Collectors.toList());
            if (!userRoleLinkService.removeByIds(linkIds)) {
                throw new ServerException(userRoleLinks, "删除用户与角色的关联失败！");
            }
        }
    }

    @Override
    public UserVO buildVO(User user, UserVO.BuildOption option) {
        UserVO userVO = new UserVO(user);
        if (option.isRoles()) {
            // 查询角色
            Collection<Role> roles = roleService.listByUserId(user.getId());
            if (CollectionUtils.isNotEmpty(roles)) {
                userVO.setRoles(roles);
            }
            if (option.isAuthorities() && CollectionUtils.isNotEmpty(roles)) {
                // 查询权限
                Collection<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
                Collection<Authority> authorities = authorityService.listByRoleIds(roleIds);
                if (CollectionUtils.isNotEmpty(authorities)) {
                    userVO.setAuthorities(authorities);
                    // 权限标识
                    userVO.setAuthoritySignals(authorities.stream().map(authority -> authorityService.genSignal(authority))
                            .collect(Collectors.toSet()));
                }
            }
        }
        return userVO;
    }

    @Override
    public List<UserVO> buildVO(Collection<User> users, UserVO.BuildOption option) {
        return users.stream().map(user -> buildVO(user, option)).collect(Collectors.toList());
    }
}
