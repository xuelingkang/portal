package com.xzixi.framework.boot.webmvc.config.elasticsearch.annotation;

import com.xzixi.framework.boot.webmvc.config.elasticsearch.ElasticsearchConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ElasticsearchConfig.class)
public @interface EnableFrameworkElasticsearch {
}
