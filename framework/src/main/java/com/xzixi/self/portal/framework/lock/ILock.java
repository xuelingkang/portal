package com.xzixi.self.portal.framework.lock;

/**
 * @author 薛凌康
 */
public interface ILock {

    /**
     * 获得锁
     *
     * @param value 值
     * @return {@code true} 加锁成功 {@code false} 加锁失败
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
     * 检查是否有锁
     *
     * @return {@code true} 有 {@code false} 没有
     */
    boolean check();

    /**
     * 检查锁的值
     *
     * @param value 值
     * @return {@code true} 相同 {@code false} 不同
     */
    boolean check(String value);

    /**
     * 检查节点个数是否与参数相同
     *
     * @param count 个数
     * @return {@code true} 相同 {@code false} 不同
     */
    boolean check(int count);

    /**
     * 监听节点，当节点个数为0时触发监听器操作
     *
     * @param listener 监听器
     */
    void register(Listener listener);

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
