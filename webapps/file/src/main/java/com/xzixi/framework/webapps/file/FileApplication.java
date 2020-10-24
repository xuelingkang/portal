package com.xzixi.framework.webapps.file;

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
 * @date 2020-10-24
 */
@SpringBootApplication
@EnableFrameworkCache
@EnableFrameworkJson
@EnableFrameworkMybatis
@EnableFrameworkValidation
@EnableFrameworkExceptionHandler
@MapperScan(basePackages = "com.xzixi.framework.webapps.file.mapper")
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
