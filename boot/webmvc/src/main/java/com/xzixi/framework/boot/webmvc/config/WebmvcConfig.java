package com.xzixi.framework.boot.webmvc.config;

import com.xzixi.framework.boot.webmvc.controller.GlobalControllerExceptionHandler;
import com.xzixi.framework.boot.webmvc.service.ISignService;
import com.xzixi.framework.boot.webmvc.service.impl.Md5SignServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuelingkang
 * @date 2020-11-12
 */
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
}
