package com.xzixi.framework.webapps.system.data.impl;

import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.common.model.po.UserLink;
import com.xzixi.framework.webapps.system.data.IUserLinkData;
import com.xzixi.framework.webapps.system.mapper.UserLinkMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "userLink:base", casualCacheName = "userLink:casual")
public class UserLinkDataImpl extends MybatisPlusDataImpl<UserLinkMapper, UserLink> implements IUserLinkData {
}
