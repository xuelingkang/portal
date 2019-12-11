package com.xzixi.self.portal.webapp.security;

import com.xzixi.self.portal.webapp.model.vo.RoleVO;
import com.xzixi.self.portal.webapp.service.IRoleService;
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

/**
 * 自定义授权决策
 *
 * @author 薛凌康
 */
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    @Autowired
    private IRoleService roleService;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当前用户权限
        Collection<? extends GrantedAuthority> authorities;
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 查询游客权限
            RoleVO guest = roleService.buildGuest();
            authorities = guest.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(String.valueOf(authority.getId())))
                    .collect(Collectors.toSet());
        } else {
            // 当前用户权限
            authorities = authentication.getAuthorities();
        }
        if (CollectionUtils.isNotEmpty(configAttributes) && CollectionUtils.isNotEmpty(authorities)) {
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
