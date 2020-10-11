package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.Authority;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IAuthorityData;
import com.xzixi.framework.webapp.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "authority:base", casualCacheName = "authority:casual")
public class AuthorityDataImpl extends MybatisPlusDataImpl<AuthorityMapper, Authority> implements IAuthorityData {
}
