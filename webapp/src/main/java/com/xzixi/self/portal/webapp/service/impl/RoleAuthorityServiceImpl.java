package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.data.IRoleAuthorityLinkData;
import com.xzixi.self.portal.webapp.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;
import com.xzixi.self.portal.webapp.service.IRoleAuthorityLinkService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
@Service
public class RoleAuthorityServiceImpl extends BaseServiceImpl<RoleAuthorityLink, IRoleAuthorityLinkData> implements IRoleAuthorityLinkService {

    @Override
    public List<RoleAuthorityLink> listByRoleId(Integer roleId) {
        return list(new QueryWrapper<>(new RoleAuthorityLink().setRoleId(roleId)));
    }

    @Override
    public List<RoleAuthorityLink> listByRoleIds(Collection<Integer> roleIds) {
        return list(new QueryWrapper<RoleAuthorityLink>().in("role_id", roleIds));
    }

    @Override
    public List<RoleAuthorityLink> listByAuthorityId(Integer authorityId) {
        return list(new QueryWrapper<>(new RoleAuthorityLink().setAuthorityId(authorityId)));
    }

    @Override
    public List<RoleAuthorityLink> listByAuthorityIds(Collection<Integer> authorityIds) {
        return list(new QueryWrapper<RoleAuthorityLink>().in("authority_id", authorityIds));
    }
}
