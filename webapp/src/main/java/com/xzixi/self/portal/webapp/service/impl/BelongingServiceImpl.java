package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.webapp.framework.exception.LogicException;
import com.xzixi.self.portal.webapp.framework.model.IBelonging;
import com.xzixi.self.portal.webapp.framework.service.IBelongingService;
import com.xzixi.self.portal.webapp.framework.util.SecurityUtil;
import com.xzixi.self.portal.webapp.model.po.User;
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
            throw new LogicException(401, "未登录！");
        }
        if (!currentUser.getId().equals(belonging.ownerId())) {
            throw new LogicException(403, "没有权限！");
        }
    }
}
