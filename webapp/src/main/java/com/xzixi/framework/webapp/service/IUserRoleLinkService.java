package com.xzixi.framework.webapp.service;

import com.xzixi.framework.webapp.model.po.UserRoleLink;
import com.xzixi.framework.boot.webmvc.service.IBaseService;

import java.util.Collection;
import java.util.List;

/**
 * @author 薛凌康
 */
public interface IUserRoleLinkService extends IBaseService<UserRoleLink> {

    /**
     * 根据用户id查询
     *
     * @param userId 用户id
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByUserId(Integer userId);

    /**
     * 根据用户id集合查询
     *
     * @param userIds 用户id集合
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByUserIds(Collection<Integer> userIds);

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByRoleId(Integer roleId);

    /**
     * 根据角色id集合查询
     *
     * @param roleIds 角色id集合
     * @return List&lt;UserRoleLink>
     */
    List<UserRoleLink> listByRoleIds(Collection<Integer> roleIds);
}
