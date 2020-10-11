package com.xzixi.framework.boot.sftp.client.component;

import org.apache.commons.pool2.impl.AbandonedConfig;

import java.io.PrintWriter;

/**
 * sftp废弃对象跟踪配置
 *
 * @author 薛凌康
 */
public class SftpAbandonedConfig extends AbandonedConfig {

    public static class Builder {
        private boolean removeAbandonedOnBorrow;
        private boolean removeAbandonedOnMaintenance;
        private int removeAbandonedTimeout;
        private boolean logAbandoned;
        private boolean requireFullStackTrace;
        private PrintWriter logWriter;
        private boolean useUsageTracking;
        public SftpAbandonedConfig build() {
            SftpAbandonedConfig config = new SftpAbandonedConfig();
            config.setRemoveAbandonedOnBorrow(removeAbandonedOnBorrow);
            config.setRemoveAbandonedOnMaintenance(removeAbandonedOnMaintenance);
            config.setRemoveAbandonedTimeout(removeAbandonedTimeout);
            config.setLogAbandoned(logAbandoned);
            config.setRequireFullStackTrace(requireFullStackTrace);
            config.setLogWriter(logWriter);
            config.setUseUsageTracking(useUsageTracking);
            return config;
        }
        public Builder removeAbandonedOnBorrow(boolean removeAbandonedOnBorrow) {
            this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
            return this;
        }
        public Builder removeAbandonedOnMaintenance(boolean removeAbandonedOnMaintenance) {
            this.removeAbandonedOnMaintenance = removeAbandonedOnMaintenance;
            return this;
        }
        public Builder removeAbandonedTimeout(int removeAbandonedTimeout) {
            this.removeAbandonedTimeout = removeAbandonedTimeout;
            return this;
        }
        public Builder logAbandoned(boolean logAbandoned) {
            this.logAbandoned = logAbandoned;
            return this;
        }
        public Builder requireFullStackTrace(boolean requireFullStackTrace) {
            this.requireFullStackTrace = requireFullStackTrace;
            return this;
        }
        public Builder logWriter(PrintWriter logWriter) {
            this.logWriter = logWriter;
            return this;
        }
        public Builder useUsageTracking(boolean useUsageTracking) {
            this.useUsageTracking = useUsageTracking;
            return this;
        }
    }
}
