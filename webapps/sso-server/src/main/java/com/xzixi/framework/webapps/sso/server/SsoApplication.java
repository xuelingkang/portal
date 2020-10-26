package com.xzixi.framework.webapps.sso.server;

import com.xzixi.framework.boot.webmvc.config.async.annotation.EnableFrameworkAsync;
import com.xzixi.framework.boot.webmvc.config.cache.annotation.EnableFrameworkCache;
import com.xzixi.framework.boot.webmvc.config.exception.annotation.EnableFrameworkExceptionHandler;
import com.xzixi.framework.boot.webmvc.config.json.annotation.EnableFrameworkJson;
import com.xzixi.framework.boot.webmvc.config.mybatis.annotation.EnableFrameworkMybatis;
import com.xzixi.framework.boot.webmvc.config.validation.annotation.EnableFrameworkValidation;
import com.xzixi.framework.webapps.common.component.annotation.EnableCommonComponents;
import com.xzixi.framework.webapps.common.feign.annotation.EnableCommonRemoteServices;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuelingkang
 * @date 2020-10-21
 */
@SpringBootApplication
@EnableFrameworkAsync
@EnableFrameworkCache
@EnableFrameworkJson
@EnableFrameworkMybatis
@EnableFrameworkValidation
@EnableFrameworkExceptionHandler
@EnableCommonRemoteServices
@EnableCommonComponents
@MapperScan(basePackages = "com.xzixi.framework.webapps.sso.mapper")
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}
