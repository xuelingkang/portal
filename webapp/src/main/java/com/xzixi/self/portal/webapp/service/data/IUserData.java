package com.xzixi.self.portal.webapp.service.data;

import com.xzixi.self.portal.webapp.model.po.User;

/**
 * @author 薛凌康
 */
public interface IUserData extends IBaseData<User> {

    String BASE_CACHE_NAME = "user:base";
    String CASUAL_CACHE_NAME = "user:casual";
}
