package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.exception.ClientException;
import com.xzixi.self.portal.framework.model.IBelonging;
import com.xzixi.self.portal.framework.service.IBelongingService;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.util.SecurityUtil;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
public class BelongingServiceImpl implements IBelongingService {

    @Override
    public void checkOwner(IBelonging belonging) {
        User currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null) {
            throw new ClientException(401, "未登录！");
        }
        if (!currentUser.getId().equals(belonging.ownerId())) {
            throw new ClientException(403, "没有权限！");
        }
    }
}
