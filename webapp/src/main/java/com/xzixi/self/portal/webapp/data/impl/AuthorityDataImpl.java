package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.data.IAuthorityData;
import com.xzixi.self.portal.webapp.mapper.AuthorityMapper;
import com.xzixi.self.portal.webapp.model.po.Authority;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "authority:base", casualCacheName = "authority:casual")
public class AuthorityDataImpl extends BaseDataImpl<AuthorityMapper, Authority> implements IAuthorityData {
}
