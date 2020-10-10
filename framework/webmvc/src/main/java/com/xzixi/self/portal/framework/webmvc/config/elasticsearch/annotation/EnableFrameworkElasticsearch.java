package com.xzixi.self.portal.framework.webmvc.config.elasticsearch.annotation;

import com.xzixi.self.portal.framework.webmvc.config.elasticsearch.ElasticsearchConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ElasticsearchConfig.class)
public @interface EnableFrameworkElasticsearch {
}
