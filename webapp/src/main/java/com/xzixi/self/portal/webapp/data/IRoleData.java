package com.xzixi.self.portal.webapp.data;

import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.model.po.Role;

/**
 * @author 薛凌康
 */
public interface IRoleData extends IBaseData<Role> {

    String BASE_CACHE_NAME = "role:base";
    String CASUAL_CACHE_NAME = "role:casual";
}
