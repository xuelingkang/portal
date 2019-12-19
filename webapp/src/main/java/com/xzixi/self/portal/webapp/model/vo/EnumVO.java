package com.xzixi.self.portal.webapp.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "枚举")
public class EnumVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "枚举项集合")
    private List<EnumItemVO> items;
}
