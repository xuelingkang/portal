package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.webapp.model.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@ApiModel(value = "用户")
public class UserVO extends User {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限集合")
    private Collection<AuthorityVO> authorities;
    @ApiModelProperty(value = "权限标识")
    private Collection<String> authoritySignals;

    public UserVO(User user, String... ignoreProperties) {
        BeanUtils.copyProperties(user, this, ignoreProperties);
    }
}
