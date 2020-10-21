package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.common.model.po.UserRoleLink;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IUserRoleLinkData;
import com.xzixi.framework.backend.mapper.UserRoleLinkMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "userRoleLink:base", casualCacheName = "userRoleLink:casual")
public class UserRoleLinkDataImpl extends MybatisPlusDataImpl<UserRoleLinkMapper, UserRoleLink> implements IUserRoleLinkData {
}
