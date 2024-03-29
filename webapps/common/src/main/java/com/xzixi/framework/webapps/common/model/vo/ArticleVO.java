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

package com.xzixi.framework.webapps.common.model.vo;

import com.xzixi.framework.webapps.common.model.valid.ArticleSave;
import com.xzixi.framework.webapps.common.model.valid.ArticleUpdate;
import com.xzixi.framework.webapps.common.model.po.Article;
import com.xzixi.framework.webapps.common.model.po.ArticleContent;
import com.xzixi.framework.webapps.common.model.po.ArticleTag;
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
