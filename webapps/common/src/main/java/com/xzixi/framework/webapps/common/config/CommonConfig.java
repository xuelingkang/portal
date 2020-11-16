package com.xzixi.framework.webapps.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xuelingkang
 * @date 2020-11-12
 */
@Configuration
@EnableFeignClients(basePackages = {"com.xzixi.framework.webapps.common.feign"})
public class CommonConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
