package com.xzixi.self.portal.framework.service;

import com.xzixi.self.portal.framework.model.IBelonging;

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
