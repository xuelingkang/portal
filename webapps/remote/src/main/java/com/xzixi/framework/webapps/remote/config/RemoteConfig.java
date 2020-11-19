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

package com.xzixi.framework.webapps.remote.config;

import feign.Feign;
import feign.QueryMapEncoder;
import feign.hystrix.HystrixFeign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author xuelingkang
 * @date 2020-11-17
 */
@Configuration
@EnableFeignClients(basePackages = {"com.xzixi.framework.webapps.remote.service"})
public class RemoteConfig {

    @Bean
    @ConditionalOnMissingBean
    public QueryMapEncoder queryMapEncoder() {
        return new ObjectQueryMapEncoder();
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnProperty(name = "feign.hystrix.enabled")
    public Feign.Builder feignHystrixBuilder(QueryMapEncoder queryMapEncoder) {
        return HystrixFeign.builder().queryMapEncoder(queryMapEncoder);
    }
}
