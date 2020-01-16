package com.xzixi.self.portal.framework.lock.impl;

import com.xzixi.self.portal.framework.lock.ILock;

/**
 * TODO 在本地实现
 *
 * @author 薛凌康
 */
public class LocalLock implements ILock {

    @Override
    public boolean acquire(String value) {
        return false;
    }

    @Override
    public void release(String value) {

    }

    @Override
    public void mount(String node) {

    }

    @Override
    public void unmount(String node) {

    }

    @Override
    public String lockValue() {
        return null;
    }

    @Override
    public int nodeCount() {
        return 0;
    }

    @Override
    public void register(Listener listener) {

    }
}
