package com.xzixi.self.portal.webapp.config.security;

import com.xzixi.self.portal.framework.webmvc.model.search.QueryParams;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
                    .listByRoleParams(new QueryParams<>(new Role().setGuest(true)));
            authorities = guestAuthorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(String.valueOf(authority.getId())))
                    .collect(Collectors.toSet());
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (Objects.equals(UserType.WEBSITE, userDetails.getUser().getType())) {
                // 网站用户权限
                Collection<Authority> websiteAuthorities = authorityService
                        .listByRoleParams(new QueryParams<>(new Role().setWebsite(true)));
                authorities = websiteAuthorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(String.valueOf(authority.getId())))
                        .collect(Collectors.toSet());
            } else {
                // 当前用户权限
                authorities = authentication.getAuthorities();
            }
        }
        if (CollectionUtils.isNotEmpty(authorities)) {
            // 需求权限
            List<String> requiredIds = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
            // 当前权限
            Set<String> currentIds = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            // 求交集
            currentIds.retainAll(requiredIds);
            if (currentIds.size() > 0) {
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
