package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IUserLinkData;
import com.xzixi.self.portal.webapp.mapper.UserLinkMapper;
import com.xzixi.self.portal.webapp.model.po.UserLink;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "userLink:base", casualCacheName = "userLink:casual")
public class UserLinkDataImpl extends MybatisPlusDataImpl<UserLinkMapper, UserLink> implements IUserLinkData {
}
