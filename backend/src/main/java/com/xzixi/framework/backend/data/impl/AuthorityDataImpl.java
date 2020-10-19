package com.xzixi.framework.backend.data.impl;

import com.xzixi.framework.backend.model.po.Authority;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.backend.data.IAuthorityData;
import com.xzixi.framework.backend.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "authority:base", casualCacheName = "authority:casual")
public class AuthorityDataImpl extends MybatisPlusDataImpl<AuthorityMapper, Authority> implements IAuthorityData {
}
