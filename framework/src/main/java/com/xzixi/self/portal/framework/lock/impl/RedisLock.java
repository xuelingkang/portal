package com.xzixi.self.portal.framework.lock.impl;

import com.xzixi.self.portal.framework.lock.ILock;
import com.xzixi.self.portal.framework.lock.LockException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 薛凌康
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RedisLock implements ILock {

    private static final long DEFAULT_LISTEN_INTERVAL = 500L;
    private static final long DEFAULT_LOCK_EXPIRE = 3600L;
    private static final long DEFAULT_NODE_EXPIRE = 600L;
    private static final String NODE_SEPARATOR = ":";
    private String parentNode;
    private RedisTemplate redisTemplate;
    private long listenInterval;
    private long lockExpire;
    private long nodeExpire;

    public RedisLock(String parentNode, RedisTemplate redisTemplate) {
        this(parentNode, redisTemplate, DEFAULT_LISTEN_INTERVAL);
    }

    public RedisLock(String parentNode, RedisTemplate redisTemplate, long listenInterval) {
        this(parentNode, redisTemplate, listenInterval, DEFAULT_LOCK_EXPIRE);
    }

    public RedisLock(String parentNode, RedisTemplate redisTemplate, long listenInterval, long lockExpire) {
        this(parentNode, redisTemplate, listenInterval, lockExpire, DEFAULT_NODE_EXPIRE);
    }

    public RedisLock(String parentNode, RedisTemplate redisTemplate, long listenInterval, long lockExpire, long nodeExpire) {
        this.parentNode = parentNode;
        this.redisTemplate = redisTemplate;
        this.listenInterval = listenInterval;
        this.lockExpire = lockExpire;
        this.nodeExpire = nodeExpire;
    }

    @Override
    public boolean acquire(String value) {
        if (StringUtils.isBlank(value)) {
            throw new LockException("锁的值不能为空！");
        }
        // 往队列中追加值
        redisTemplate.opsForList().rightPush(parentNode, value);
        redisTemplate.expire(parentNode, lockExpire, TimeUnit.SECONDS);
        // 当前锁的值和value相等表示加锁成功
        return check(value);
    }

    @Override
    public void release(String value) {
        if (StringUtils.isBlank(value)) {
            throw new LockException("锁的值不能为空！");
        }
        redisTemplate.opsForList().remove(parentNode, 1, value);
    }

    @Override
    public void mount(String node) {
        if (StringUtils.isBlank(node)) {
            throw new LockException("节点不能为空！");
        }
        String key = parentNode + NODE_SEPARATOR + node;
        redisTemplate.opsForValue().set(key, 1, nodeExpire, TimeUnit.SECONDS);
    }

    @Override
    public void unmount(String node) {
        if (StringUtils.isBlank(node)) {
            throw new LockException("节点不能为空！");
        }
        String key = parentNode + NODE_SEPARATOR + node;
        redisTemplate.delete(key);
    }

    @Override
    public String lockValue() {
        // 返回队列的第一个元素
        return (String) redisTemplate.opsForList().index(parentNode, 0);
    }

    @Override
    public int nodeCount() {
        Set<String> keys = redisTemplate.keys(parentNode + NODE_SEPARATOR + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return 0;
        }
        return keys.size();
    }

    @Override
    public void waitLock(String value, Listener listener) {
        if (listener == null) {
            throw new LockException("监听器不能为空！");
        }
        while (!check(value)) {
            // 如果与期望的值不同就循环等待
            try {
                Thread.sleep(listenInterval);
            } catch (Exception e) {
                throw new LockException("监听器等待执行期间出现错误！", e);
            }
        }
        // 执行监听器
        listener.execute();
    }

    @Override
    public void waitNode(Listener listener) {
        if (listener == null) {
            throw new LockException("监听器不能为空！");
        }
        while (!check(0)) {
            // 如果节点不为空就循环等待
            try {
                Thread.sleep(listenInterval);
            } catch (Exception e) {
                throw new LockException("监听器等待执行期间出现错误！", e);
            }
        }
        // 执行监听器
        listener.execute();
    }
}
