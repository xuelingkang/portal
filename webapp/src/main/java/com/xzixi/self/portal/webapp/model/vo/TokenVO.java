package com.xzixi.self.portal.webapp.model.vo;

import com.xzixi.self.portal.webapp.model.po.Token;
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
@ApiModel(value = "令牌VO")
public class TokenVO extends Token {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户VO")
    private UserVO user;

    public TokenVO(Token token, String... ignoreProperties) {
        BeanUtils.copyProperties(token, this, ignoreProperties);
    }
}
