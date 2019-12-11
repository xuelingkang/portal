package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;

/**
 * @author 薛凌康
 */
public interface IRoleAuthorityLinkService extends IBaseService<RoleAuthorityLink> {

    String BASE_CACHE_NAME = "roleAuthorityLink:base";
    String CASUAL_CACHE_NAME = "roleAuthorityLink:casual";
}
