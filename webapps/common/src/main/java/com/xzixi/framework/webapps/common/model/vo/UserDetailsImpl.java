/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.webapps.common.model.po.Authority;
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
