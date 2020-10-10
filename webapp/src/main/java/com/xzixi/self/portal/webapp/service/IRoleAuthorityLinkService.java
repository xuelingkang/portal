package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.webmvc.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.RoleAuthorityLink;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IRoleAuthorityLinkService extends IBaseService<RoleAuthorityLink> {

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByRoleId(Integer roleId);

    /**
     * 根据角色id集合查询
     *
     * @param roleIds 角色id集合
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByRoleIds(Collection<Integer> roleIds);

    /**
     * 根据权限id查询
     *
     * @param authorityId 权限id
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByAuthorityId(Integer authorityId);

    /**
     * 根据权限id集合查询
     *
     * @param authorityIds 权限id集合
     * @return List&lt;RoleAuthorityLink>
     */
    List<RoleAuthorityLink> listByAuthorityIds(Collection<Integer> authorityIds);
}
