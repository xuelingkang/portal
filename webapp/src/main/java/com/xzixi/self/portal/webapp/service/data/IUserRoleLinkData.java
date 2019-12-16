package com.xzixi.self.portal.webapp.service.data;

import com.xzixi.self.portal.webapp.model.po.UserRoleLink;

/**
 * @author 薛凌康
 */
public interface IUserRoleLinkData extends IBaseData<UserRoleLink> {

    String BASE_CACHE_NAME = "userRoleLink:base";
    String CASUAL_CACHE_NAME = "userRoleLink:casual";
}
