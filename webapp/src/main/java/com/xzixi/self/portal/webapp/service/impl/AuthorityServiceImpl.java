package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;
import com.xzixi.self.portal.webapp.service.IAuthorityService;
import com.xzixi.self.portal.webapp.data.IAuthorityData;
import com.xzixi.self.portal.webapp.data.IRoleAuthorityLinkData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority, IAuthorityData> implements IAuthorityService {

    @Autowired
    private IRoleAuthorityLinkData roleAuthorityLinkData;

    @Override
    public Collection<Authority> listByRoleIds(Collection<Integer> roleIds) {
        List<RoleAuthorityLink> roleAuthorityLinks = roleAuthorityLinkData
                .list(new QueryWrapper<RoleAuthorityLink>().in("role_id", roleIds));
        if (CollectionUtils.isEmpty(roleAuthorityLinks)) {
            return null;
        }
        List<Integer> authorityIds = roleAuthorityLinks.stream()
                .map(RoleAuthorityLink::getAuthorityId).collect(Collectors.toList());
        return baseData.listByIds(authorityIds);
    }
}
