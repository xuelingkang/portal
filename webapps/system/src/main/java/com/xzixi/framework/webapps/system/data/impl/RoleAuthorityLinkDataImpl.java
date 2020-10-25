package com.xzixi.framework.webapps.system.data.impl;

import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.common.model.po.RoleAuthorityLink;
import com.xzixi.framework.webapps.system.data.IRoleAuthorityLinkData;
import com.xzixi.framework.webapps.system.mapper.RoleAuthorityLinkMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "roleAuthorityLink:base", casualCacheName = "roleAuthorityLink:casual")
public class RoleAuthorityLinkDataImpl extends MybatisPlusDataImpl<RoleAuthorityLinkMapper, RoleAuthorityLink> implements IRoleAuthorityLinkData {
}
