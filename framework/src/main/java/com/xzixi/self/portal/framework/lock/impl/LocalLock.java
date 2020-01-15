package com.xzixi.self.portal.framework.lock.impl;

import com.xzixi.self.portal.framework.lock.ILock;

/**
 * TODO 在本地实现
 *
 * @author 薛凌康
 */
public class LocalLock implements ILock {

    @Override
    public void mount(String node) {

    }

    @Override
    public void unmount(String node) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public void register(Listener listener) throws Exception {

    }
}
