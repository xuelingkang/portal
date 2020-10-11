package com.xzixi.framework.webapp;

import com.xzixi.framework.boot.webmvc.config.async.annotation.EnableFrameworkAsync;
import com.xzixi.framework.boot.webmvc.config.cache.annotation.EnableFrameworkCache;
import com.xzixi.framework.boot.webmvc.config.elasticsearch.annotation.EnableFrameworkElasticsearch;
import com.xzixi.framework.boot.webmvc.config.json.annotation.EnableFrameworkJson;
import com.xzixi.framework.boot.webmvc.config.mail.annotation.EnableFrameworkMail;
import com.xzixi.framework.boot.webmvc.config.mybatis.annotation.EnableFrameworkMybatis;
import com.xzixi.framework.boot.webmvc.config.validation.annotation.EnableFrameworkValidation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 薛凌康
 */
@SpringBootApplication
@EnableFrameworkAsync
@EnableFrameworkCache
@EnableFrameworkElasticsearch
@EnableFrameworkJson
@EnableFrameworkMail
@EnableFrameworkMybatis
@EnableFrameworkValidation
@MapperScan(basePackages = "com.xzixi.framework.webapp.mapper")
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
