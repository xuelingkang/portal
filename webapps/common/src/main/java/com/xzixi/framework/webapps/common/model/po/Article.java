/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.model.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xzixi.framework.boot.core.model.BaseModel;
import com.xzixi.framework.boot.swagger2.annotations.IgnoreSwagger2Parameter;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import com.xzixi.framework.webapps.common.model.enums.ArticleAccess;
import com.xzixi.framework.webapps.common.model.enums.ArticleCategory;
import com.xzixi.framework.webapps.common.model.enums.ArticleType;
import com.xzixi.framework.webapps.common.model.valid.ArticleSave;
import com.xzixi.framework.webapps.common.model.valid.ArticleUpdate;
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

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章")
@Document(indexName = ProjectConstant.INDEX_NAME_PREFIX + "article")
public class Article extends BaseModel {

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
}
