package com.xzixi.self.portal.sftp.pool.component;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * sftp连接池
 *
 * @author 薛凌康
 */
public class SftpPool extends GenericObjectPool<Sftp> {

    /**
     * Creates a new <code>GenericObjectPool</code> that tracks and destroys
     * objects that are checked out, but never returned to the pool.
     * 创建一个{@link GenericObjectPool}对象池，跟踪使用后未返回给对象池的对象，防止对象泄漏。
     *
     * @param factory         The object factory to be used to create object instances
     *                        used by this pool
     *                        对象工厂
     * @param config          The base pool configuration to use for this pool instance.
     *                        The configuration is used by value. Subsequent changes to
     *                        the configuration object will not be reflected in the
     *                        pool.
     *                        对象池配置
     * @param abandonedConfig Configuration for abandoned object identification
     *                        废弃对象跟踪配置
     */
    public SftpPool(SftpFactory factory, SftpPoolConfig config, SftpAbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
