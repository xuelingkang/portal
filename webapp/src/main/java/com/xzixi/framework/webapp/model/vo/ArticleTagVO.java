package com.xzixi.framework.webapp.model.vo;

import com.xzixi.framework.webapp.model.po.Article;
import com.xzixi.framework.webapp.model.po.ArticleTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章标签")
public class ArticleTagVO extends ArticleTag {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章个数")
    private Integer articleCount;

    @ApiModelProperty(value = "文章集合")
    private Collection<Article> articles;
}
