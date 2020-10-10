package com.xzixi.self.portal.webapp.service;

import com.xzixi.self.portal.framework.webmvc.service.IBaseService;
import com.xzixi.self.portal.framework.webmvc.service.IVoService;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.vo.RoleVO;

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
