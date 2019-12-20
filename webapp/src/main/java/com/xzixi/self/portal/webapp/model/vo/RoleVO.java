package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.webapp.framework.util.BeanUtils;
import com.xzixi.self.portal.webapp.model.po.Authority;
import com.xzixi.self.portal.webapp.model.po.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "角色")
public class RoleVO extends Role {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限集合")
    private Collection<Authority> authorities;

    @ApiModelProperty(value = "权限标识")
    private Collection<String> authoritySignals;

    public RoleVO(Role role, String... ignoreProperties) {
        BeanUtils.copyProperties(role, this, ignoreProperties);
    }
}
