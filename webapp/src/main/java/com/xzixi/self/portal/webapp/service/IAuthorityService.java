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
     * 构建AuthorityVO
     *
     * @param authority Authority
     * @return AuthorityVO
     */
    AuthorityVO buildAuthorityVO(Authority authority);
}
