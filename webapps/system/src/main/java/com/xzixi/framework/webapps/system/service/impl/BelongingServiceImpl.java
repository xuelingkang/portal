package com.xzixi.framework.webapps.system.service.impl;

import com.xzixi.framework.boot.webmvc.model.IBelonging;
import com.xzixi.framework.boot.webmvc.service.IBelongingService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class BelongingServiceImpl implements IBelongingService {

    @Override
    public void checkOwner(IBelonging belonging) {
//        if (!Objects.equals(SecurityUtils.getCurrentUserId(), belonging.ownerId())) {
//            throw new ClientException(403, "没有权限！");
//        }
    }
}
