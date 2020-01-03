package com.xzixi.self.portal.webapp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.framework.service.IBaseService;
import com.xzixi.self.portal.framework.service.IVoService;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.vo.AuthorityVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IAuthorityService extends IBaseService<Authority>,
        IVoService<Authority, AuthorityVO, AuthorityVO.BuildOption> {

    /**
     * 根据角色id查询权限
     *
     * @param roleId 角色id
     * @return 权限
     */
    Collection<Authority> listByRoleId(Integer roleId);

    /**
     * 根据角色id查询权限
     *
     * @param roleIds 角色id
     * @return 权限
     */
    Collection<Authority> listByRoleIds(Collection<Integer> roleIds);

    /**
     * 根据角色条件查询权限集合
     *
     * @param queryWrapper 角色条件
     * @return List&lt;Authority>
     */
    Collection<Authority> listByRoleWrapper(QueryWrapper<Role> queryWrapper);

    /**
     * 根据权限id删除权限，同时删除与角色的关联
     *
     * @param ids 权限id
     */
    void removeAuthoritiesByIds(Collection<Integer> ids);

    /**
     * 生成权限签名
     *
     * @param authority 权限
     * @return 权限签名
     */
    String genSignal(Authority authority);
}
