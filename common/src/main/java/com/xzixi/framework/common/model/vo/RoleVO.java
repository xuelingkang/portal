package com.xzixi.framework.common.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.common.model.po.Authority;
import com.xzixi.framework.common.model.po.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
        private boolean authorities;
    }
}
