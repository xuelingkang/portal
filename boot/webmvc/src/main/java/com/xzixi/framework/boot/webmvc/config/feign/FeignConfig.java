package com.xzixi.framework.boot.webmvc.config.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuelingkang
 * @date 2020-10-25
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignRequestHeaderInterceptor() {
        return new FeignRequestHeaderInterceptor();
    }
}
