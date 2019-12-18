package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.webapp.model.po.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(value = "权限VO")
public class AuthorityVO extends Authority {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限标识")
    private String authoritySignal;

    public AuthorityVO(Authority authority, String... ignoreProperties) {
        BeanUtils.copyProperties(authority, this, ignoreProperties);
    }
}
