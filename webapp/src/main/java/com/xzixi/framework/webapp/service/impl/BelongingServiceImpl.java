package com.xzixi.framework.webapp.service.impl;

import com.xzixi.framework.webapp.util.SecurityUtils;
import com.xzixi.framework.boot.webmvc.exception.ClientException;
import com.xzixi.framework.boot.webmvc.model.IBelonging;
import com.xzixi.framework.boot.webmvc.service.IBelongingService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 薛凌康
 */
@Service
public class BelongingServiceImpl implements IBelongingService {

    @Override
    public void checkOwner(IBelonging belonging) {
        if (!Objects.equals(SecurityUtils.getCurrentUserId(), belonging.ownerId())) {
            throw new ClientException(403, "没有权限！");
        }
    }
}
