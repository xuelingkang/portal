package com.xzixi.self.portal.framework.webmvc.config.mybatis.annotation;

import com.xzixi.self.portal.framework.webmvc.config.mybatis.MybatisPlusConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisPlusConfig.class)
public @interface EnableFrameworkMybatis {
}
