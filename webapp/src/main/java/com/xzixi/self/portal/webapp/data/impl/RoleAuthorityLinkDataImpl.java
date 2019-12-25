package com.xzixi.self.portal.webapp.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.framework.data.impl.BaseDataImpl;
import com.xzixi.self.portal.webapp.mapper.RoleAuthorityLinkMapper;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;
import com.xzixi.self.portal.webapp.data.IRoleAuthorityLinkData;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class RoleAuthorityLinkDataImpl extends BaseDataImpl<RoleAuthorityLinkMapper, RoleAuthorityLink> implements IRoleAuthorityLinkData {
}