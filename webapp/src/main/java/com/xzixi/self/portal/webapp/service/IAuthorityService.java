package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.data.IAuthorityData;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IAuthorityService extends IBaseService<Authority, IAuthorityData> {

    /**
     * 根据角色id查询权限
     *
     * @param roleIds 角色id
     * @return 权限
     */
    Collection<Authority> listByRoleIds(Collection<Integer> roleIds);
}
