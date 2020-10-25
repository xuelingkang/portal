package com.xzixi.framework.webapps.task;

import com.xzixi.framework.boot.webmvc.config.async.annotation.EnableFrameworkAsync;
import com.xzixi.framework.boot.webmvc.config.cache.annotation.EnableFrameworkCache;
import com.xzixi.framework.boot.webmvc.config.exception.annotation.EnableFrameworkExceptionHandler;
import com.xzixi.framework.boot.webmvc.config.json.annotation.EnableFrameworkJson;
import com.xzixi.framework.boot.webmvc.config.mybatis.annotation.EnableFrameworkMybatis;
import com.xzixi.framework.boot.webmvc.config.validation.annotation.EnableFrameworkValidation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuelingkang
 * @date 2020-10-22
 */
@SpringBootApplication
@EnableFrameworkAsync
@EnableFrameworkCache
@EnableFrameworkJson
@EnableFrameworkMybatis
@EnableFrameworkValidation
@EnableFrameworkExceptionHandler
@MapperScan(basePackages = "com.xzixi.framework.webapps.task.mapper")
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
