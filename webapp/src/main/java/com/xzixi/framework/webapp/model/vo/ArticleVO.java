package com.xzixi.framework.webapp.model.vo;

import com.xzixi.framework.webapp.model.valid.ArticleSave;
import com.xzixi.framework.webapp.model.valid.ArticleUpdate;
import com.xzixi.framework.webapp.model.po.Article;
import com.xzixi.framework.webapp.model.po.ArticleContent;
import com.xzixi.framework.webapp.model.po.ArticleTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章")
public class ArticleVO extends Article {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章内容")
    @Valid
    private ArticleContent content;

    @ApiModelProperty(value = "文章标签")
    @Size(groups = {ArticleSave.class, ArticleUpdate.class}, max = 3, message = "标签个数不能超过3个！")
    private Collection<ArticleTag> tags;
}
