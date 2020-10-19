package com.xzixi.framework.backend.service;

import com.xzixi.framework.backend.model.po.Role;
import com.xzixi.framework.backend.model.vo.RoleVO;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IRoleService extends IBaseService<Role>, IVoService<Role, RoleVO, RoleVO.BuildOption> {

    /**
     * 根据用户id查询角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    Collection<Role> listByUserId(Integer userId);

    /**
     * 根据角色id删除角色，同时删除与用户和权限的关联
     *
     * @param ids 角色id
     */
    void removeRolesByIds(Collection<Integer> ids);
}
