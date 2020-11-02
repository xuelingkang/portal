/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.sftp.client.starter.autoconfigure;

import com.xzixi.framework.boot.sftp.client.component.*;
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
    public ISftpClient sftpClient(SftpClientProperties sftpClientProperties) {
        if (sftpClientProperties.isMultiple()) {
            MultipleSftpClient multipleSftpClient = new MultipleSftpClient();
            sftpClientProperties.getClients().forEach((name, properties) -> {
                SftpFactory sftpFactory = createSftpFactory(properties);
                SftpPoolConfig sftpPoolConfig = createSftpPoolConfig(properties);
                SftpAbandonedConfig sftpAbandonedConfig = createSftpAbandonedConfig(properties);
                SftpPool sftpPool = new SftpPool(sftpFactory, sftpPoolConfig, sftpAbandonedConfig);
                ISftpClient sftpClient = new SftpClient(sftpPool);
                multipleSftpClient.put(name, sftpClient);
            });
            return multipleSftpClient;
        }
        SftpFactory sftpFactory = createSftpFactory(sftpClientProperties);
        SftpPoolConfig sftpPoolConfig = createSftpPoolConfig(sftpClientProperties);
        SftpAbandonedConfig sftpAbandonedConfig = createSftpAbandonedConfig(sftpClientProperties);
        SftpPool sftpPool = new SftpPool(sftpFactory, sftpPoolConfig, sftpAbandonedConfig);
        return new SftpClient(sftpPool);
    }

    public SftpFactory createSftpFactory(SftpClientProperties properties) {
        return new SftpFactory.Builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }

    public SftpPoolConfig createSftpPoolConfig(SftpClientProperties properties) {
        SftpClientProperties.Pool pool = properties.getPool();
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

    public SftpAbandonedConfig createSftpAbandonedConfig(SftpClientProperties properties) {
        SftpClientProperties.Abandoned abandoned = properties.getAbandoned();
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
}
