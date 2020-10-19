package com.xzixi.framework.backend.model.po;

import com.xzixi.framework.backend.model.valid.ArticleSave;
import com.xzixi.framework.backend.model.valid.ArticleUpdate;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.boot.webmvc.model.IBelonging;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章标签")
public class ArticleTag extends BaseModel implements IBelonging {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "标签名称")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 10, message = "标签字数必须不能超过10！")
    private String name;

    @Override
    public Integer ownerId() {
        return userId;
    }
}
