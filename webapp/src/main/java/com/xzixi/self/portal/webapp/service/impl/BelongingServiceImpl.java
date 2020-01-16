package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.exception.ClientException;
import com.xzixi.self.portal.framework.model.IBelonging;
import com.xzixi.self.portal.framework.service.IBelongingService;
import com.xzixi.self.portal.webapp.util.SecurityUtils;
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
