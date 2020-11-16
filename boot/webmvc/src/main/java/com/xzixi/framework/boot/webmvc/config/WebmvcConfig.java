package com.xzixi.framework.boot.webmvc.config;

import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.boot.webmvc.service.impl.Md5SignServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuelingkang
 * @date 2020-11-12
 */
@Configuration
public class WebmvcConfig {

    @Bean
    public ISignService md5SignService() {
        return new Md5SignServiceImpl();
    }

    @Bean
    public GlobalControllerExceptionHandler globalControllerExceptionHandler() {
        return new GlobalControllerExceptionHandler();
    }
}
