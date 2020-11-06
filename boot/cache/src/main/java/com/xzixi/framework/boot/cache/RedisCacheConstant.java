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

package com.xzixi.framework.boot.cache;

/**
 * @author 薛凌康
 */
public interface RedisCacheConstant {

    /** 正则key前缀 */
    String REGEX_KEY_PREFIX = "regex=";
    /** 多个key之间的分隔符 */
    String KEYS_SEPARATOR = ",";
    /** key层次分隔符 */
    String KEY_SEPARATOR = ":";
    /** getById方法名 */
    String GET_BY_ID_METHOD_NAME = "getById";
    /** 值为null的参数 */
    String NULL_PARAM = "null";
    /** 无参方法key */
    String EMPTY_PARAM = "emptyParam";
    /** 参数分隔符 */
    String PARAM_SEPARATOR = "_";
    /** 正则key通配符 */
    String WILDCARD = "*";
}
