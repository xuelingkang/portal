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

import com.xzixi.framework.boot.core.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联
 *
 * @author 薛凌康
 */
@Data
@NoArgsConstructor
public class ArticleTagLink extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 文章id */
    private Integer articleId;

    /** 标签id */
    private Integer tagId;

    /** 显示顺序 */
    private Integer seq;

    public ArticleTagLink(Integer articleId, Integer tagId, Integer seq) {
        this.articleId = articleId;
        this.tagId = tagId;
        this.seq = seq;
    }
}
