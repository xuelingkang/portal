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

/**
 * 可以在这个包扩展注解和枚举，其中注解必须使用value属性表示列名，
 * 在BaseSearchParams的ANNOTATION_CLASSES中添加扩展的注解，
 * 并在Condition的枚举方法中处理这个注解
 *
 * @author 薛凌康
 * @see com.xzixi.framework.boot.webmvc.model.search.BaseSearchParams
 * @see com.xzixi.framework.boot.webmvc.model.search.ConditionType
 */
package com.xzixi.framework.boot.webmvc.model.search;
