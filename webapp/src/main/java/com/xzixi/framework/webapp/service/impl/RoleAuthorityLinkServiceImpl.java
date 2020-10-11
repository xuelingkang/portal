package com.xzixi.framework.webapp.service.impl;

import com.xzixi.framework.webapp.data.IRoleAuthorityLinkData;
import com.xzixi.framework.webapp.model.po.RoleAuthorityLink;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapp.service.IRoleAuthorityLinkService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class RoleAuthorityLinkServiceImpl extends BaseServiceImpl<IRoleAuthorityLinkData, RoleAuthorityLink> implements IRoleAuthorityLinkService {

    @Override
    public List<RoleAuthorityLink> listByRoleId(Integer roleId) {
        return list(new QueryParams<>(new RoleAuthorityLink().setRoleId(roleId)));
    }

    @Override
    public List<RoleAuthorityLink> listByRoleIds(Collection<Integer> roleIds) {
        return list(new QueryParams<RoleAuthorityLink>().in("roleId", roleIds));
    }

    @Override
    public List<RoleAuthorityLink> listByAuthorityId(Integer authorityId) {
        return list(new QueryParams<>(new RoleAuthorityLink().setAuthorityId(authorityId)));
    }

    @Override
    public List<RoleAuthorityLink> listByAuthorityIds(Collection<Integer> authorityIds) {
        return list(new QueryParams<RoleAuthorityLink>().in("authorityId", authorityIds));
    }
}
