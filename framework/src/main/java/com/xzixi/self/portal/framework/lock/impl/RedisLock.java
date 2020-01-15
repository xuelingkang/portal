package com.xzixi.self.portal.framework.lock.impl;

import com.xzixi.self.portal.framework.lock.ILock;
import com.xzixi.self.portal.framework.lock.LockException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author 薛凌康
 */
@SuppressWarnings("all")
public class RedisLock implements ILock {

    private static final long DEFAULT_LISTEN_INTERVAL = 500L;
    private String nodeKeyTemplate;
    private RedisTemplate redisTemplate;
    private TaskExecutor taskExecutor;
    private long listenInterval;

    public RedisLock(String nodeKeyTemplate, RedisTemplate redisTemplate, TaskExecutor taskExecutor) {
        this(nodeKeyTemplate, redisTemplate, taskExecutor, DEFAULT_LISTEN_INTERVAL);
    }

    public RedisLock(String nodeKeyTemplate, RedisTemplate redisTemplate, TaskExecutor taskExecutor, long listenInterval) {
        this.nodeKeyTemplate = nodeKeyTemplate;
        this.redisTemplate = redisTemplate;
        this.taskExecutor = taskExecutor;
        this.listenInterval = listenInterval;
    }

    @Override
    public void mount(String node) {
        String key = String.format(nodeKeyTemplate, node);
        redisTemplate.boundValueOps(key).set(1);
    }

    @Override
    public void unmount(String node) {
        String key = String.format(nodeKeyTemplate, node);
        redisTemplate.delete(key);
    }

    @Override
    public boolean isEmpty() {
        Set keys = redisTemplate.keys(String.format(nodeKeyTemplate, "*"));
        return CollectionUtils.isEmpty(keys);
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public void register(Listener listener) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        taskExecutor.execute(new ListenerTask(listener, latch));
        // 等待监听器执行完毕之后再返回
        latch.await();
    }

    @AllArgsConstructor
    class ListenerTask implements Runnable {

        private Listener listener;
        private CountDownLatch latch;

        @Override
        public void run() {
            while (isNotEmpty()) {
                // 如果节点不为空就循环等待
                try {
                    Thread.sleep(listenInterval);
                } catch (InterruptedException e) {
                    throw new LockException("等待执行监听器时出现异常！", e);
                }
            }
            // 执行监听器
            listener.execute();
            // 计数减一，继续主线程
            latch.countDown();
        }
    }
}
