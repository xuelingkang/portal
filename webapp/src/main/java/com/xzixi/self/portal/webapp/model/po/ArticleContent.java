package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.webapp.model.valid.ArticleSave;
import com.xzixi.self.portal.webapp.model.valid.ArticleUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章内容")
public class ArticleContent extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章内容id")
    private Integer id;

    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    @ApiModelProperty(value = "文章内容")
    @NotBlank(groups = {ArticleSave.class}, message = "文章内容不能为空！")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 100000, message = "文章内容不能超过100000字！")
    private String content;
}
