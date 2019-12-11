package com.xzixi.self.portal.webapp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus配置
 *
 * @author 薛凌康
 */
@Configuration
@MapperScan(basePackages = "com.xzixi.self.portal.webapp.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor()
                .setOverflow(true);
    }
}
