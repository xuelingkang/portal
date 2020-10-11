package com.xzixi.framework.boot.webmvc.config.elasticsearch;

import org.springframework.context.annotation.Configuration;

/**
 * elasticsearch配置
 *
 * @author 薛凌康
 */
@Configuration
public class ElasticsearchConfig {

    static {
        // 解决netty冲突导致初始化client报错的问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
