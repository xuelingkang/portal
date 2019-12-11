package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.vo.RoleVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IRoleService extends IBaseService<Role> {

    String BASE_CACHE_NAME = "role:base";
    String CASUAL_CACHE_NAME = "role:casual";

    /**
     * 游客角色名称
     */
    String GUEST_ROLE_NAME = "ROLE_GUEST";

    /**
     * 根据用户id查询角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    Collection<Role> listByUserId(Integer userId);

    /**
     * 获取游客角色
     *
     * @return 游客角色
     */
    RoleVO buildGuest();

    /**
     * 构建RoleVO对象
     *
     * @param role 角色
     * @return RoleVO
     */
    RoleVO buildRoleVO(Role role);
}
