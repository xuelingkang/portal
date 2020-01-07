package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.IBelonging;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章收藏夹")
public class ArticleCollection extends BaseModel implements IBelonging {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏夹id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "收藏夹名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @Override
    public Integer ownerId() {
        return userId;
    }
}
