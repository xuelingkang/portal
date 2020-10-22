package com.xzixi.framework.webapps.content.config.security;

import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.common.model.vo.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UserVO user;

    public UserDetailsImpl(UserVO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (user != null) {
            Collection<Authority> authorityCollection = user.getAuthorities();
            if (CollectionUtils.isNotEmpty(authorityCollection)) {
                authorityCollection.forEach(authority ->
                        authorities.add(new SimpleGrantedAuthority(String.valueOf(authority.getId()))));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (user != null) {
            return !user.getLocked();
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (user != null) {
            return user.getActivated();
        }
        return true;
    }
}
