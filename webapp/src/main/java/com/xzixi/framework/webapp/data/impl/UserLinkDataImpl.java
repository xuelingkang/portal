package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.UserLink;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IUserLinkData;
import com.xzixi.framework.webapp.mapper.UserLinkMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "userLink:base", casualCacheName = "userLink:casual")
public class UserLinkDataImpl extends MybatisPlusDataImpl<UserLinkMapper, UserLink> implements IUserLinkData {
}
