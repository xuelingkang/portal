package com.xzixi.self.portal.webapp.framework.service;

import com.xzixi.self.portal.webapp.framework.model.IBelonging;

/**
 * @author 薛凌康
 */
public interface IBelongingService {

    /**
     * 检查当前用户是否传入物品的拥有者
     *
     * @param belonging IBelonging
     */
    void checkOwner(IBelonging belonging);
}
