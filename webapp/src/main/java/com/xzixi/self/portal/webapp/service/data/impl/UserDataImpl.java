package com.xzixi.self.portal.webapp.service.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.mapper.UserMapper;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.service.data.IUserData;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class UserDataImpl extends BaseDataImpl<UserMapper, User> implements IUserData {
}
