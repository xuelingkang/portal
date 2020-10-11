package com.xzixi.framework.boot.webmvc.config.mybatis.annotation;

import com.xzixi.framework.boot.webmvc.config.mybatis.MybatisPlusConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisPlusConfig.class)
public @interface EnableFrameworkMybatis {
}
