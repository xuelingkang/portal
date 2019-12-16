package com.xzixi.self.portal.webapp.service.data.impl;

import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.webapp.mapper.RoleMapper;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.service.data.IRoleData;
import org.springframework.stereotype.Service;

/**
 * @author 薛凌康
 */
@Service
@CacheEnhance
public class RoleDataImpl extends BaseDataImpl<RoleMapper, Role> implements IRoleData {
}
