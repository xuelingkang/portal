package com.xzixi.framework.webapps.content.config.security;

import com.xzixi.framework.webapps.common.constant.AuthorityConstant;
import com.xzixi.framework.webapps.common.model.enums.AuthorityMethod;
import com.xzixi.framework.webapps.common.model.enums.AuthorityProtocol;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据访问url获取所需权限
 *
 * @author 薛凌康
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        // 查询所有http类型的权限
        List<Authority> authorities = authorityService
                .list(new QueryParams<>(new Authority().setProtocol(AuthorityProtocol.HTTP)));

        List<Authority> matched = authorities.stream()
                .filter(authority -> new AntPathRequestMatcher(authority.getPattern(), authority.getMethod() == AuthorityMethod.ALL ? null : authority.getMethod().name()).matches(request))
                .collect(Collectors.toList());

        // 没有匹配的权限，或者只匹配到/**，返回null表示没有匹配的权限
        if (CollectionUtils.isEmpty(matched) || (matched.size() == 1 && matched.get(0).getPattern().equals(AuthorityConstant.ALL_PATH))) {
            return null;
        }

        // 匹配到的权限
        return matched.stream().map(authority -> new SecurityConfig(String.valueOf(authority.getId()))).collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
