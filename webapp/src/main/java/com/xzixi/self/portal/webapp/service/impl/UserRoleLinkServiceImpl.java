package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.framework.webmvc.model.search.QueryParams;
import com.xzixi.self.portal.framework.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IUserRoleLinkData;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.service.IUserRoleLinkService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class UserRoleLinkServiceImpl extends BaseServiceImpl<IUserRoleLinkData, UserRoleLink> implements IUserRoleLinkService {

    @Override
    public List<UserRoleLink> listByUserId(Integer userId) {
        return list(new QueryParams<>(new UserRoleLink().setUserId(userId)));
    }

    @Override
    public List<UserRoleLink> listByUserIds(Collection<Integer> userIds) {
        return list(new QueryParams<UserRoleLink>().in("userId", userIds));
    }

    @Override
    public List<UserRoleLink> listByRoleId(Integer roleId) {
        return list(new QueryParams<>(new UserRoleLink().setRoleId(roleId)));
    }

    @Override
    public List<UserRoleLink> listByRoleIds(Collection<Integer> roleIds) {
        return list(new QueryParams<UserRoleLink>().in("roleId", roleIds));
    }
}
