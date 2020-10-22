package com.xzixi.framework.webapps.content.data.impl;

import com.xzixi.framework.webapps.common.model.po.User;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.webapps.content.data.IUserData;
import com.xzixi.framework.webapps.content.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance(baseCacheName = "user:base", casualCacheName = "user:casual")
public class UserDataImpl extends MybatisPlusDataImpl<UserMapper, User> implements IUserData {
}
