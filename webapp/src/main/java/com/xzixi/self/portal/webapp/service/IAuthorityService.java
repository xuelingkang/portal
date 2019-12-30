package com.xzixi.self.portal.webapp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.webapp.framework.service.IBaseService;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import com.xzixi.self.portal.webapp.model.vo.AuthorityVO;

import java.util.Collection;

/**
 * @author 薛凌康
 */
public interface IAuthorityService extends IBaseService<Authority> {

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
     * @return {@code true} 删除成功 {@code false} 删除失败
     */
    boolean removeAuthoritiesByIds(Collection<Integer> ids);

    /**
     * 构建AuthorityVO
     *
     * @param id 权限id
     * @return AuthorityVO
     */
    AuthorityVO buildAuthorityVO(Integer id);

    /**
     * 构建AuthorityVO
     *
     * @param authority Authority
     * @return AuthorityVO
     */
    AuthorityVO buildAuthorityVO(Authority authority);
}
