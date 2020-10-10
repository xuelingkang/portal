package com.xzixi.self.portal.webapp;

import com.xzixi.self.portal.framework.webmvc.config.async.annotation.EnableFrameworkAsync;
import com.xzixi.self.portal.framework.webmvc.config.cache.annotation.EnableFrameworkCache;
import com.xzixi.self.portal.framework.webmvc.config.elasticsearch.annotation.EnableFrameworkElasticsearch;
import com.xzixi.self.portal.framework.webmvc.config.json.annotation.EnableFrameworkJson;
import com.xzixi.self.portal.framework.webmvc.config.mail.annotation.EnableFrameworkMail;
import com.xzixi.self.portal.framework.webmvc.config.mybatis.annotation.EnableFrameworkMybatis;
import com.xzixi.self.portal.framework.webmvc.config.validation.annotation.EnableFrameworkValidation;
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
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
