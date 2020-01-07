package com.xzixi.self.portal.webapp.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.model.enums.UserType;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.xzixi.self.portal.webapp.constant.UserConstant.SYSTEM_ADMIN_USER_ID;

/**
 * 自定义授权决策
 *
 * @author 薛凌康
 */
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(configAttributes)) {
            // 权限管理范围外的请求，直接放行
            return;
        }
        // 当前用户权限
        Collection<? extends GrantedAuthority> authorities;
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 游客权限
            Collection<Authority> guestAuthorities = authorityService
                    .listByRoleWrapper(new QueryWrapper<>(new Role().setGuest(true)));
            authorities = guestAuthorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(String.valueOf(authority.getId())))
                    .collect(Collectors.toSet());
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // 系统管理员直接跳过
            if (SYSTEM_ADMIN_USER_ID.equals(userDetails.getUser().getId())) {
                return;
            }
            if (UserType.WEBSITE.equals(userDetails.getUser().getType())) {
                // 网站用户权限
                Collection<Authority> websiteAuthorities = authorityService
                        .listByRoleWrapper(new QueryWrapper<>(new Role().setWebsite(true)));
                authorities = websiteAuthorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(String.valueOf(authority.getId())))
                        .collect(Collectors.toSet());
            } else {
                // 当前用户权限
                authorities = authentication.getAuthorities();
            }
        }
        if (CollectionUtils.isNotEmpty(authorities)) {
            // 需求权限id
            String authorityId = ((List<ConfigAttribute>) configAttributes).get(0).getAttribute();
            // 检查是否包含权限
            boolean contains = authorities.stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet()).contains(authorityId);
            if (contains) {
                return;
            }
        }
        // 没有匹配到表示权限不足
        throw new AccessDeniedException("权限不足！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
