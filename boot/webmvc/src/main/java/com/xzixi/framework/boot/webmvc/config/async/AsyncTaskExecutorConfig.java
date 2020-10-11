package com.xzixi.framework.boot.webmvc.config.async;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * {@link EnableAsync}开启异步支持，
 * 使用注解{@link org.springframework.scheduling.annotation.Async}声明异步任务，
 * 异步任务由配置中的线程池调度
 *
 * @author 薛凌康
 */
@EnableAsync(mode = AdviceMode.ASPECTJ)
@Configuration
public class AsyncTaskExecutorConfig {

    /**
     * 线程池
     *
     * @return TaskExecutor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数量，默认1
        taskExecutor.setCorePoolSize(1);
        // 最大线程数量，默认int最大值
        taskExecutor.setMaxPoolSize(16);
        // 任务队列容量，达到最大时线程池会扩容，默认int最大值
        taskExecutor.setQueueCapacity(100);
        // 线程超时时间，默认60
        taskExecutor.setKeepAliveSeconds(60);
        // 允许核心线程超时，默认false
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("task-");
        // 线程优先级
        taskExecutor.setThreadPriority(5);
        return taskExecutor;
    }
}
