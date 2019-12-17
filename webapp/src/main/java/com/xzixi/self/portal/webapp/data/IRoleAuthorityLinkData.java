package com.xzixi.self.portal.webapp.data;

import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;

/**
 * @author 薛凌康
 */
public interface IRoleAuthorityLinkData extends IBaseData<RoleAuthorityLink> {

    String BASE_CACHE_NAME = "roleAuthorityLink:base";
    String CASUAL_CACHE_NAME = "roleAuthorityLink:casual";
}
