package com.xzixi.self.portal.webapp.service.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.mapper.AuthorityMapper;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.service.data.IAuthorityData;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class AuthorityDataImpl extends BaseDataImpl<AuthorityMapper, Authority> implements IAuthorityData {
}