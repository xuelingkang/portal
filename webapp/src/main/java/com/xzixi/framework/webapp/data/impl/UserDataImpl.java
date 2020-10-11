package com.xzixi.framework.webapp.data.impl;

import com.xzixi.framework.webapp.model.po.User;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapp.data.IUserData;
import com.xzixi.framework.webapp.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "user:base", casualCacheName = "user:casual")
public class UserDataImpl extends MybatisPlusDataImpl<UserMapper, User> implements IUserData {
}
