package com.xzixi.self.portal.webapp.model.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xzixi.self.portal.swagger2.extension.annotation.IgnoreSwagger2Parameter;
import com.xzixi.self.portal.framework.model.BaseModel;
import com.xzixi.self.portal.framework.model.IBelonging;
import com.xzixi.self.portal.webapp.model.enums.ArticleAccess;
import com.xzixi.self.portal.webapp.model.enums.ArticleCategory;
import com.xzixi.self.portal.webapp.model.enums.ArticleType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import static com.xzixi.self.portal.webapp.constant.ProjectConstant.INDEX_NAME_PREFIX;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章")
@Document(indexName = INDEX_NAME_PREFIX + "article")
public class Article extends BaseModel implements IBelonging {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章id")
    @Null(groups = {ArticleSave.class}, message = "文章id必须为空！")
    @NotNull(groups = {ArticleUpdate.class}, message = "文章id不能为空！")
    @Field(type = FieldType.Keyword)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @Field(type = FieldType.Keyword)
    private Integer userId;

    @ApiModelProperty(value = "访问限制")
    @NotNull(groups = {ArticleSave.class}, message = "访问限制不能为空！")
    @Field(type = FieldType.Keyword)
    private ArticleAccess access;

    @ApiModelProperty(value = "类型")
    @NotNull(groups = {ArticleSave.class}, message = "类型不能为空！")
    @Field(type = FieldType.Keyword)
    private ArticleType type;

    @ApiModelProperty(value = "栏目")
    @NotNull(groups = {ArticleSave.class}, message = "栏目不能为空！")
    @Field(type = FieldType.Keyword)
    private ArticleCategory category;

    @ApiModelProperty(value = "标题")
    @NotBlank(groups = {ArticleSave.class}, message = "标题不能为空！")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 200, message = "标题长度不能超过200字！")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @ApiModelProperty(value = "摘要")
    @NotBlank(groups = {ArticleSave.class}, message = "摘要不能为空！")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 200, message = "摘要长度不能超过500字！")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String outline;

    @ApiModelProperty(value = "是否完成")
    @Null(groups = {ArticleSave.class, ArticleUpdate.class}, message = "是否完成必须为空！")
    @Field(type = FieldType.Keyword)
    private Boolean finished;

    @ApiModelProperty(value = "访问次数")
    @Null(groups = {ArticleSave.class, ArticleUpdate.class}, message = "访问次数必须为空！")
    @Field(type = FieldType.Integer)
    private Integer visitTimes;

    @ApiModelProperty(value = "来源")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 20, message = "来源长度不能超过20字！")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String source;

    @ApiModelProperty(value = "来源网址")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 200, message = "来源网址不能超过200字！")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String sourceUrl;

    @ApiModelProperty(value = "创建时间")
    @Null(groups = {ArticleSave.class, ArticleUpdate.class}, message = "创建时间必须为空！")
    @Field(type = FieldType.Long)
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    @Null(groups = {ArticleSave.class, ArticleUpdate.class}, message = "更新时间必须为空！")
    @Field(type = FieldType.Long)
    private Long updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    @IgnoreSwagger2Parameter
    private Boolean deleted;

    @Override
    public Integer ownerId() {
        return userId;
    }
}
