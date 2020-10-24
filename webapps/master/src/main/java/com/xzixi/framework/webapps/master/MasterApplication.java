package com.xzixi.framework.webapps.master;

import com.xzixi.framework.boot.webmvc.config.json.annotation.EnableFrameworkJson;
import com.xzixi.framework.boot.webmvc.config.validation.annotation.EnableFrameworkValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuelingkang
 * @date 2020-10-22
 */
@SpringBootApplication
@EnableFrameworkJson
@EnableFrameworkValidation
public class MasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterApplication.class, args);
    }
}
