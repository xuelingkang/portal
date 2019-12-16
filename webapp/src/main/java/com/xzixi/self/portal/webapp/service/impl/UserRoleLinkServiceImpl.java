package com.xzixi.self.portal.webapp.service.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.mapper.UserRoleLinkMapper;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.service.IUserRoleLinkService;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class UserRoleLinkServiceImpl extends BaseServiceImpl<UserRoleLinkMapper, UserRoleLink> implements IUserRoleLinkService {
}
