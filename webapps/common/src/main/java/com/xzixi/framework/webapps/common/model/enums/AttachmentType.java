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

package com.xzixi.framework.webapps.common.model.enums;

import com.xzixi.framework.boot.core.model.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 薛凌康
 */
@Getter
@AllArgsConstructor
public enum AttachmentType implements IBaseEnum {

    /** 邮件附件 */
    MAIL("邮件附件"),
    /** 博客附件*/
    BLOG("博客附件"),
    /** 新闻附件*/
    NEWS("新闻附件");

    private final String value;
}
