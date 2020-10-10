package com.xzixi.self.portal.webapp.model.po;

import com.xzixi.self.portal.framework.webmvc.model.BaseModel;
import com.xzixi.self.portal.webapp.model.valid.ArticleSave;
import com.xzixi.self.portal.webapp.model.valid.ArticleUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;

import static com.xzixi.self.portal.webapp.constant.ProjectConstant.INDEX_NAME_PREFIX;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章内容")
@Document(indexName = INDEX_NAME_PREFIX + "article-content")
public class ArticleContent extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章内容id")
    @Field(type = FieldType.Keyword)
    private Integer id;

    @ApiModelProperty(value = "文章id")
    @Field(type = FieldType.Keyword)
    private Integer articleId;

    @ApiModelProperty(value = "文章内容")
    @NotBlank(groups = {ArticleSave.class}, message = "文章内容不能为空！")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 100000, message = "文章内容不能超过100000字！")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;
}
