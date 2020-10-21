package com.xzixi.framework.content.data.impl;

import com.xzixi.framework.common.model.po.Role;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.content.data.IRoleData;
import com.xzixi.framework.content.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "role:base", casualCacheName = "role:casual")
public class RoleDataImpl extends MybatisPlusDataImpl<RoleMapper, Role> implements IRoleData {
}
