package com.xzixi.self.portal.webapp.data;

import com.xzixi.self.portal.webapp.framework.data.IBaseData;
import com.xzixi.self.portal.webapp.model.po.Authority;

/**
 * @author 薛凌康
 */
public interface IAuthorityData extends IBaseData<Authority> {

    String BASE_CACHE_NAME = "authority:base";
    String CASUAL_CACHE_NAME = "authority:casual";
}