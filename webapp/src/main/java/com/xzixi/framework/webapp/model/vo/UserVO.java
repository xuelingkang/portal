package com.xzixi.framework.webapp.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.webapp.model.po.Authority;
import com.xzixi.framework.webapp.model.po.Role;
import com.xzixi.framework.webapp.model.po.User;
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
@ApiModel(description = "用户")
public class UserVO extends User {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色集合")
    private Collection<Role> roles;

    @ApiModelProperty(value = "权限集合")
    private Collection<Authority> authorities;

    @ApiModelProperty(value = "权限标识")
    private Collection<String> authoritySignals;

    public UserVO(User user, String... ignoreProperties) {
        BeanUtils.copyProperties(user, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
        private boolean roles;
        private boolean authorities;
    }
}
