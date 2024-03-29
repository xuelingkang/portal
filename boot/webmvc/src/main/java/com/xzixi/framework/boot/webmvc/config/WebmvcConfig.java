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

package com.xzixi.framework.boot.webmvc.config;

import com.xzixi.framework.boot.webmvc.controller.GlobalControllerExceptionHandler;
import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.boot.webmvc.service.impl.Md5SignServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xuelingkang
 * @date 2020-11-12
 */
@EnableAsync(mode = AdviceMode.ASPECTJ)
@Configuration
public class WebmvcConfig {

    @Bean
    @ConditionalOnMissingBean(name = "md5SignService")
    public ISignService md5SignService() {
        return new Md5SignServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(name = "globalControllerExceptionHandler")
    public GlobalControllerExceptionHandler globalControllerExceptionHandler() {
        return new GlobalControllerExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
