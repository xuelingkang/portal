package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.mapper.UserRoleLinkMapper;
import com.xzixi.self.portal.webapp.model.po.UserRoleLink;
import com.xzixi.self.portal.webapp.data.IUserRoleLinkData;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class UserRoleLinkDataImpl extends BaseDataImpl<UserRoleLinkMapper, UserRoleLink> implements IUserRoleLinkData {
}
