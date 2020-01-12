package com.xzixi.self.portal.sftp.client.starter.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_FAIRNESS;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_JMX_ENABLE;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_LIFO;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TEST_ON_BORROW;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TEST_ON_CREATE;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_TEST_ON_RETURN;
import static org.apache.commons.pool2.impl.GenericObjectPoolConfig.*;

/**
 * sftp连接池配置参数
 *
 * @author 薛凌康
 */
@Data
@ConfigurationProperties(prefix = "sftp-client")
public class SftpClientProperties {

    /**
     * 默认为单个
     */
    private boolean multiple = false;

    /**
     * 主机ip
     */
    private String host;
    /**
     * 端口号
     */
    private int port = 22;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 池配置
     */
    private Pool pool = new Pool();

    /**
     * 废弃对象配置
     */
    private Abandoned abandoned = new Abandoned();

    /**
     * 当type=ClientType.MULTIPLE时设置
     */
    private Map<String, SftpClientProperties> clients = new LinkedHashMap<>();

    /**
     * 池配置参数
     */
    @Data
    public static class Pool {
        private static final long DEFAULT_MAX_WAIT_MILLIS = 5000L;
        private static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = -1L;
        private static final long DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1000L * 60L * 30L;
        private static final boolean DEFAULT_TEST_WHILE_IDLE = true;
        private static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 1000L * 60L * 10L;
        private static final String DEFAULT_JMX_NAME_PREFIX = "pool";
        private static final String DEFAULT_JMX_NAME_BASE = "sftp";
        /**
         * 对象最大数量
         * 默认值是8
         */
        private int maxTotal = DEFAULT_MAX_TOTAL;
        /**
         * 最大空闲对象数量
         * 默认值是8
         */
        private int maxIdle = DEFAULT_MAX_IDLE;
        /**
         * 最小空闲对象数量
         * 默认值是0
         */
        private int minIdle = DEFAULT_MIN_IDLE;
        /**
         * 对象池存储空闲对象是使用的LinkedBlockingDeque，它本质上是一个支持FIFO和FILO的双向的队列，
         * common-pool2中的LinkedBlockingDeque不是Java原生的队列，而有common-pool2重新写的一个双向队列。
         * 如果为true，表示使用FIFO获取对象。
         * 默认值是true
         */
        private boolean lifo = DEFAULT_LIFO;
        /**
         * common-pool2实现的LinkedBlockingDeque双向阻塞队列使用的是Lock锁。
         * 这个参数就是表示在实例化一个LinkedBlockingDeque时，是否使用lock的公平锁。
         * 默认值是false
         */
        private boolean fairness = DEFAULT_FAIRNESS;
        /**
         * 当没有空闲连接时，获取一个对象的最大等待时间。如果这个值小于0，则永不超时，一直等待，直到有空闲对象到来。
         * 如果大于0，则等待maxWaitMillis长时间，如果没有空闲对象，将抛出NoSuchElementException异常。
         * 可以根据需要自己调整，单位是毫秒。
         * 默认值是5000L
         */
        private long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;
        /**
         * 对象最小的空闲时间。如果为小于等于0，最Long的最大值，如果大于0，当空闲的时间大于这个值时，执行移除这个对象操作。
         * 可以避免连接泄漏
         * 默认值是-1
         */
        private long minEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
        /**
         * shutdown驱逐线程的超时时间。当创建驱逐线(evictor)程时，如发现已有一个evictor正在运行则会停止该evictor，
         * evictorShutdownTimeoutMillis表示当前线程需等待多长时间让ScheduledThreadPoolExecutor停止该evictor线程。
         * (evictor继承自TimerTask，由ScheduledThreadPoolExecutor进行调度)
         * 默认值是10000L，即10秒
         */
        private long evictorShutdownTimeoutMillis = DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS;
        /**
         * 对象最小的空间时间，如果小于等于0，取Long的最大值，如果大于0，当对象的空闲时间超过这个值，
         * 并且当前空闲对象的数量大于最小空闲数量(minIdle)时，执行移除操作。
         * 这个和上面的minEvictableIdleTimeMillis的区别是，它会保留最小的空闲对象数量。而上面的不会，是强制性移除的。
         * 默认值是1000L * 60L * 30L，即30分钟
         */
        private long softMinEvictableIdleTimeMillis = DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
        /**
         * 检测空闲对象线程每次检测的空闲对象的数量。如果这个值小于0，则每次检测的空闲对象数量等于当前空闲对象数量除以这个值的绝对值，并对结果向上取整。
         * 默认值是3
         */
        private int numTestsPerEvictionRun = DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
        /**
         * 在创建对象时检测对象是否有效，true是，默认值是false。做了这个配置会降低性能。
         * 默认值是false
         */
        private boolean testOnCreate = DEFAULT_TEST_ON_CREATE;
        /**
         * 在从对象池获取对象时是否检测对象有效，true是；默认值是false。做了这个配置会降低性能。
         * 默认值是false
         */
        private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;
        /**
         * 在向对象池中归还对象时是否检测对象有效，true是，默认值是false。做了这个配置会降低性能。
         * 默认值是false
         */
        private boolean testOnReturn = DEFAULT_TEST_ON_RETURN;
        /**
         * 在检测空闲对象线程检测到对象不需要移除时，是否检测对象的有效性。
         * true是，默认值是false。建议配置为true，不影响性能，并且保证安全性。
         * 默认值是true
         */
        private boolean testWhileIdle = DEFAULT_TEST_WHILE_IDLE;
        /**
         * 空闲对象检测线程的执行周期，即多长时间执行一次空闲对象检测。
         * 单位是毫秒数。如果小于等于0，则不执行检测线程。
         * 默认值是1000L * 60L * 10L，即10分钟
         */
        private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
        /**
         * 当对象池没有空闲对象时，新的获取对象的请求是否阻塞。true阻塞。
         * 默认值是true
         */
        private boolean blockWhenExhausted = DEFAULT_BLOCK_WHEN_EXHAUSTED;
        /**
         * 是否注册JMX
         * 默认值是true
         */
        private boolean jmxEnabled = DEFAULT_JMX_ENABLE;
        /**
         * JMX前缀
         * 默认值是pool
         */
        private String jmxNamePrefix = DEFAULT_JMX_NAME_PREFIX;
        /**
         * 使用jmxNameBase + jmxNamePrefix + i来生成ObjectName
         * 默认值是sftp
         */
        private String jmxNameBase = DEFAULT_JMX_NAME_BASE;
    }

    /**
     * 废弃对象跟踪配置参数
     */
    @Data
    public static class Abandoned {
        private static final boolean DEFAULT_REMOVE_ABANDONED_ON_BORROW = true;
        private static final boolean DEFAULT_REMOVE_ABANDONED_ON_MAINTENANCE = true;
        private static final int DEFAULT_REMOVE_ABANDONED_TIMEOUT = 300;
        private static final boolean DEFAULT_LOG_ABANDONED = false;
        private static final boolean DEFAULT_REQUIRE_FULL_STACK_TRACE = false;
        private static final boolean DEFAULT_USE_USAGE_TRACKING = false;
        /**
         * 从对象池中获取对象的时候进行清理
         * 如果当前对象池中少于2个空闲状态的对象或者 活跃数量>最大对象数-3 的时候，在获取对象的时候启动泄漏清理
         * 默认值是true
         */
        private boolean removeAbandonedOnBorrow = DEFAULT_REMOVE_ABANDONED_ON_BORROW;
        /**
         * 池维护（evicor）是否执行放弃的对象删除。
         * 必须将连接池的timeBetweenEvictionRunsMillis属性设置为一个有效的值，否者此配置不生效
         * 默认值是true
         */
        private boolean removeAbandonedOnMaintenance = DEFAULT_REMOVE_ABANDONED_ON_MAINTENANCE;
        /**
         * 对象被获取后多长时间没有返回给对象池，则放弃对象，单位秒
         * 默认值是300
         */
        private int removeAbandonedTimeout = DEFAULT_REMOVE_ABANDONED_TIMEOUT;
        /**
         * 是否记录放弃对象的应用程序代码的堆栈跟踪，
         * 废弃对象的日志记录会为每个创建的对象增加开销，因为必须生成堆栈跟踪。
         * 默认值是false
         */
        private boolean logAbandoned = DEFAULT_LOG_ABANDONED;
        /**
         * 当logAbandoned为true时有效，
         * 是否记录完整堆栈信息，如果禁用此功能，则可以使用更快但信息量较少的堆栈遍历机制（如果可用）。
         * 默认值是false
         */
        private boolean requireFullStackTrace = DEFAULT_REQUIRE_FULL_STACK_TRACE;
        /**
         * 如果池实现了org.apache.commons.pool2.UsageTracking接口，是否记录完整堆栈信息用来辅助调试废弃对象
         * 默认值是false
         */
        private boolean useUsageTracking = DEFAULT_USE_USAGE_TRACKING;
    }
}
