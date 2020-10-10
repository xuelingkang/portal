package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IUserRoleLinkData;
import com.xzixi.self.portal.webapp.mapper.UserRoleLinkMapper;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "userRoleLink:base", casualCacheName = "userRoleLink:casual")
public class UserRoleLinkDataImpl extends MybatisPlusDataImpl<UserRoleLinkMapper, UserRoleLink> implements IUserRoleLinkData {
}
