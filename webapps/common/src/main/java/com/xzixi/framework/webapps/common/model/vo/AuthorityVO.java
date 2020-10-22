package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.boot.webmvc.util.BeanUtils;
import com.xzixi.framework.webapps.common.model.po.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(description = "权限")
public class AuthorityVO extends Authority {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限标识")
    private String authoritySignal;

    public AuthorityVO(Authority authority, String... ignoreProperties) {
        BeanUtils.copyProperties(authority, this, ignoreProperties);
    }

    @Getter
    @AllArgsConstructor
    public static class BuildOption {
    }
}
