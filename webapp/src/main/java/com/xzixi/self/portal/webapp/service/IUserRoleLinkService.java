package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.model.po.UserRoleLink;

/**
 * @author 薛凌康
 */
public interface IUserRoleLinkService extends IBaseService<UserRoleLink> {

    String BASE_CACHE_NAME = "userRoleLink:base";
    String CASUAL_CACHE_NAME = "userRoleLink:casual";
}
