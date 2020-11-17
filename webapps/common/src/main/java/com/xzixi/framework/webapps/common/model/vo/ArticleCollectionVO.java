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

import com.xzixi.framework.webapps.common.model.po.Article;
import com.xzixi.framework.webapps.common.model.po.ArticleCollection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章收藏夹")
public class ArticleCollectionVO extends ArticleCollection {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章个数")
    private Integer articleCount;

    @ApiModelProperty(value = "文章集合")
    private Collection<Article> articles;
}
