package com.xzixi.framework.backend.service;

import com.xzixi.framework.common.model.po.Authority;
import com.xzixi.framework.common.model.po.Role;
import com.xzixi.framework.common.model.vo.AuthorityVO;
import com.xzixi.framework.boot.webmvc.model.search.QueryParams;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.IVoService;

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
     * @param queryParams 角色条件
     * @return List&lt;Authority>
     */
    Collection<Authority> listByRoleParams(QueryParams<Role> queryParams);

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
