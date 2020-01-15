package com.xzixi.self.portal.framework.lock;

/**
 * @author 薛凌康
 */
public interface ILock {

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
     * 检查节点个数是否为0
     *
     * @return {@code true} 节点个数为0 {@code false} 节点个数不为0
     */
    boolean isEmpty();

    /**
     * 检查节点个数是否不为0
     *
     * @return {@code true} 节点个数不为0 {@code false} 节点个数为0
     */
    boolean isNotEmpty();

    /**
     * 注册一个监听器
     *
     * @param listener 监听器
     * @throws Exception 执行监听器有可能抛出的异常
     */
    void register(Listener listener) throws Exception;

    /**
     * 监听器，当节点个数为0时触发
     */
    interface Listener {

        /**
         * 执行回调
         */
        void execute();
    }
}
