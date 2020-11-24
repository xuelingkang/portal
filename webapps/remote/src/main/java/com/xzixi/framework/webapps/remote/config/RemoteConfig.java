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

import com.xzixi.framework.webapps.remote.controller.RemoteServiceExceptionHandler;
import feign.Feign;
import feign.QueryMapEncoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author xuelingkang
 * @date 2020-11-17
 */
@Configuration
@EnableFeignClients(basePackages = {"com.xzixi.framework.webapps.remote.service"})
@ComponentScan(basePackages = {"com.xzixi.framework.webapps.remote.fallback"})
public class RemoteConfig {

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnProperty(name = "feign.hystrix.enabled")
    @ConditionalOnBean(QueryMapEncoder.class)
    public Feign.Builder feignHystrixBuilder(QueryMapEncoder queryMapEncoder) {
        return HystrixFeign.builder().queryMapEncoder(queryMapEncoder);
    }

    @Bean
    public RemoteServiceExceptionHandler remoteServiceExceptionHandler() {
        return new RemoteServiceExceptionHandler();
    }
}
