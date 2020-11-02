/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.common.model.po;

import com.xzixi.framework.webapps.common.model.valid.ArticleSave;
import com.xzixi.framework.webapps.common.model.valid.ArticleUpdate;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.webapps.common.constant.ProjectConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章内容")
@Document(indexName = ProjectConstant.INDEX_NAME_PREFIX + "article-content")
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
