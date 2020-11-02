/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.sso.server.config;

import com.xzixi.framework.webapps.sso.server.config.security.TokenFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * 认证授权配置
 * TODO 研究一下cas
 *
 * @author 薛凌康
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private TokenFilter tokenFilter;
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 使用密钥生成jwt加密需要的key
     *
     * @return key
     */
    @Bean
    public Key key() {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * 定义认证用户信息获取来源，密码校验规则等
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 定义安全策略
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 基于token，所以不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 放开所有路径
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.formLogin()
                .loginProcessingUrl("/login")
                // 认证成功
                .successHandler(authenticationSuccessHandler)
                // 认证失败
                .failureHandler(authenticationFailureHandler);
        http.exceptionHandling()
                // 授权异常
                .accessDeniedHandler(accessDeniedHandler)
                // 认证异常
                .authenticationEntryPoint(authenticationEntryPoint);
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
