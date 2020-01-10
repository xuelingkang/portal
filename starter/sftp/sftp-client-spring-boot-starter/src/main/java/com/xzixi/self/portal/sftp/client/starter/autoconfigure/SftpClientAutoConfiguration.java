package com.xzixi.self.portal.sftp.client.starter.autoconfigure;

import com.xzixi.self.portal.sftp.client.component.*;
import org.apache.commons.pool2.impl.DefaultEvictionPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintWriter;

/**
 * sftp连接池自动配置
 *
 * @author 薛凌康
 */
@Configuration
@ConditionalOnClass(SftpPool.class)
@EnableConfigurationProperties(SftpClientProperties.class)
public class SftpClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SftpFactory sftpFactory(SftpClientProperties sftpClientProperties) {
        return new SftpFactory.Builder()
                .host(sftpClientProperties.getHost())
                .port(sftpClientProperties.getPort())
                .username(sftpClientProperties.getUsername())
                .password(sftpClientProperties.getPassword())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SftpPoolConfig sftpPoolConfig(SftpClientProperties sftpClientProperties) {
        SftpClientProperties.Pool pool = sftpClientProperties.getPool();
        return new SftpPoolConfig.Builder()
                .maxTotal(pool.getMaxTotal())
                .maxIdle(pool.getMaxIdle())
                .minIdle(pool.getMinIdle())
                .lifo(pool.isLifo())
                .fairness(pool.isFairness())
                .maxWaitMillis(pool.getMaxWaitMillis())
                .minEvictableIdleTimeMillis(pool.getMinEvictableIdleTimeMillis())
                .evictorShutdownTimeoutMillis(pool.getEvictorShutdownTimeoutMillis())
                .softMinEvictableIdleTimeMillis(pool.getSoftMinEvictableIdleTimeMillis())
                .numTestsPerEvictionRun(pool.getNumTestsPerEvictionRun())
                .evictionPolicy(null)
                .evictionPolicyClassName(DefaultEvictionPolicy.class.getName())
                .testOnCreate(pool.isTestOnCreate())
                .testOnBorrow(pool.isTestOnBorrow())
                .testOnReturn(pool.isTestOnReturn())
                .testWhileIdle(pool.isTestWhileIdle())
                .timeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis())
                .blockWhenExhausted(pool.isBlockWhenExhausted())
                .jmxEnabled(pool.isJmxEnabled())
                .jmxNamePrefix(pool.getJmxNamePrefix())
                .jmxNameBase(pool.getJmxNameBase())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SftpAbandonedConfig sftpAbandonedConfig(SftpClientProperties sftpClientProperties) {
        SftpClientProperties.Abandoned abandoned = sftpClientProperties.getAbandoned();
        return new SftpAbandonedConfig.Builder()
                .removeAbandonedOnBorrow(abandoned.isRemoveAbandonedOnBorrow())
                .removeAbandonedOnMaintenance(abandoned.isRemoveAbandonedOnMaintenance())
                .removeAbandonedTimeout(abandoned.getRemoveAbandonedTimeout())
                .logAbandoned(abandoned.isLogAbandoned())
                .requireFullStackTrace(abandoned.isRequireFullStackTrace())
                .logWriter(new PrintWriter(System.out))
                .useUsageTracking(abandoned.isUseUsageTracking())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SftpPool sftpPool(SftpFactory sftpFactory,
                             SftpPoolConfig sftpPoolConfig,
                             SftpAbandonedConfig sftpAbandonedConfig) {
        return new SftpPool(sftpFactory, sftpPoolConfig, sftpAbandonedConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public SftpClient sftpClient(SftpPool sftpPool) {
        return new SftpClient(sftpPool);
    }
}
