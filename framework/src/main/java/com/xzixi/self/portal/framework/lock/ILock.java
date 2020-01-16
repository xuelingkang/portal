package com.xzixi.self.portal.framework.lock;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 薛凌康
 */
public interface ILock {

    /**
     * 尝试加锁<br>
     * 如果加锁失败，不会释放锁，可以自行选择等待，或者释放锁
     *
     * @param value 值
     * @return {@code true} 加锁成功 {@code false} 加锁失败
     * @see #waitLock(String, Listener)
     * @see #release(String)
     */
    boolean acquire(String value);

    /**
     * 释放锁
     *
     * @param value 值
     */
    void release(String value);

    /**
     * 挂载一个节点
     *
     * @param node 节点
     */
    void mount(String node);

    /**
     * 卸载一个节点
     *
     * @param node 节点
     */
    void unmount(String node);

    /**
     * 获取锁的值
     *
     * @return 值
     */
    String lockValue();

    /**
     * 获取节点个数
     *
     * @return 节点个数
     */
    int nodeCount();

    /**
     * 检查是否有锁
     *
     * @return {@code true} 有 {@code false} 没有
     */
    default boolean check() {
        return StringUtils.isNotBlank(lockValue());
    }

    /**
     * 检查锁的值
     *
     * @param value 值
     * @return {@code true} 相同 {@code false} 不同
     */
    default boolean check(String value) {
        if (value == null) {
            return false;
        }
        return StringUtils.equals(value, lockValue());
    }

    /**
     * 检查节点个数是否与参数相同
     *
     * @param count 个数
     * @return {@code true} 相同 {@code false} 不同
     */
    default boolean check(int count) {
        return count == nodeCount();
    }

    /**
     * 监听锁，当锁被释放时触发监听器操作
     *
     * @param value 期望的值
     * @param listener 监听器
     */
    void waitLock(String value, Listener listener);

    /**
     * 监听节点，当节点个数为0时触发监听器操作
     *
     * @param listener 监听器
     */
    void waitNode(Listener listener);

    /**
     * 监听器
     */
    interface Listener {

        /**
         * 执行回调
         */
        void execute();
    }
}
