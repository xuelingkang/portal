package com.xzixi.self.portal.webapp.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.model.enums.AuthorityProtocol;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.service.business.IAuthorityBusiness;
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
import java.util.Optional;

/**
 * 根据访问url获取所需权限
 *
 * @author 薛凌康
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IAuthorityBusiness authorityBusiness;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        // 查询所有http类型的权限
        List<Authority> authorities = authorityBusiness
                .list(new QueryWrapper<>(new Authority().setProtocol(AuthorityProtocol.HTTP)));

        // 匹配权限
        Optional<Authority> authorityOptional = authorities.stream().filter(authority ->
                new AntPathRequestMatcher(authority.getPattern(), authority.getMethod().name())
                        .matches(request)).findFirst();

        return authorityOptional.map(authority ->
                SecurityConfig.createList(String.valueOf(authority.getId()))).orElse(null);
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
