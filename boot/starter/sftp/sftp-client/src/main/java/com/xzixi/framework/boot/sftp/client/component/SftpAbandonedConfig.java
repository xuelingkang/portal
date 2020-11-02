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
