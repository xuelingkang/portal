package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.IBelonging;
import com.xzixi.self.portal.webapp.model.valid.ArticleSave;
import com.xzixi.self.portal.webapp.model.valid.ArticleUpdate;
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
