package com.xzixi.self.portal.framework.lock.impl;

import com.xzixi.self.portal.framework.lock.ILock;
import com.xzixi.self.portal.framework.lock.LockException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 薛凌康
 */
public class LocalLock implements ILock {

    private Queue<String> values = new ConcurrentLinkedQueue<>();
    private Queue<String> nodes = new ConcurrentLinkedQueue<>();
    private final Object valueLock = new Object();
    private final Object nodeLock = new Object();

    @Override
    public boolean acquire(String value) {
        if (StringUtils.isBlank(value)) {
            throw new LockException("锁的值不能为空！");
        }
        values.offer(value);
        return Objects.equals(value, lockValue());
    }

    @Override
    public void release(String value) {
        if (StringUtils.isBlank(value)) {
            throw new LockException("锁的值不能为空！");
        }
        values.remove(value);
        valueLock.notifyAll();
    }

    @Override
    public void mount(String node) {
        if (StringUtils.isBlank(node)) {
            throw new LockException("节点不能为空！");
        }
        nodes.offer(node);
    }

    @Override
    public void unmount(String node) {
        if (StringUtils.isBlank(node)) {
            throw new LockException("节点不能为空！");
        }
        nodes.remove(node);
        if (check(0)) {
            nodeLock.notifyAll();
        }
    }

    @Override
    public String lockValue() {
        return values.peek();
    }

    @Override
    public int nodeCount() {
        if (CollectionUtils.isEmpty(nodes)) {
            return 0;
        }
        return nodes.size();
    }

    @Override
    public void waitLock(String value, Listener listener) {
        if (listener == null) {
            throw new LockException("监听器不能为空！");
        }
        // 同步代码块只负责等待，监听器的回调在同步块外执行
        synchronized (valueLock) {
            while (!Objects.equals(value, lockValue())) {
                try {
                    valueLock.wait();
                } catch (Exception e) {
                    throw new LockException("监听器等待执行期间出现错误！", e);
                }
            }
        }
        listener.execute();
    }

    @Override
    public void waitNode(Listener listener) {
        if (listener == null) {
            throw new LockException("监听器不能为空！");
        }
        // 同步代码块只负责等待，监听器的回调在同步块外执行
        synchronized (nodeLock) {
            while (!check(0)) {
                try {
                    nodeLock.wait();
                } catch (Exception e) {
                    throw new LockException("监听器等待执行期间出现错误！", e);
                }
            }
        }
        listener.execute();
    }
}
