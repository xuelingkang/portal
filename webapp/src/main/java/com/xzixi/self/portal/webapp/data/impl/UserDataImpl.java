package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.framework.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.framework.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.self.portal.webapp.data.IUserData;
import com.xzixi.self.portal.webapp.mapper.UserMapper;
import com.xzixi.self.portal.webapp.model.po.User;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "user:base", casualCacheName = "user:casual")
public class UserDataImpl extends MybatisPlusDataImpl<UserMapper, User> implements IUserData {
}
