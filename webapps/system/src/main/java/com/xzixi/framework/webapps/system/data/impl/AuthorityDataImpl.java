package com.xzixi.framework.webapps.system.data.impl;

import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.common.model.po.Authority;
import com.xzixi.framework.webapps.system.data.IAuthorityData;
import com.xzixi.framework.webapps.system.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "authority:base", casualCacheName = "authority:casual")
public class AuthorityDataImpl extends MybatisPlusDataImpl<AuthorityMapper, Authority> implements IAuthorityData {
}