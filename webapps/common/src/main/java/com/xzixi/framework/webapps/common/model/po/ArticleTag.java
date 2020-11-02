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
import com.xzixi.framework.boot.webmvc.model.IBelonging;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 薛凌康
 */
@Data
@ApiModel(description = "文章标签")
public class ArticleTag extends BaseModel implements IBelonging {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "标签名称")
    @Length(groups = {ArticleSave.class, ArticleUpdate.class}, max = 10, message = "标签字数必须不能超过10！")
    private String name;

    @Override
    public Integer ownerId() {
        return userId;
    }
}
