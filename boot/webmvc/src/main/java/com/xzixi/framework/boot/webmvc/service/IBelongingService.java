package com.xzixi.framework.boot.webmvc.service;

import com.xzixi.framework.boot.webmvc.model.IBelonging;

/**
 * @author 薛凌康
 */
public interface IBelongingService {

    /**
     * 检查当前用户是否传入对象的拥有者
     *
     * @param belonging IBelonging接口的实例
     */
    void checkOwner(IBelonging belonging);
}
