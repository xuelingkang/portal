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
 * 配合enhance模块的CacheEnhance注解，扩展了模糊删除缓存的功能
 *
 * <p>2020-01-09，删除了listByIds的缓存，在listByIds方法中循环调用getById方法，
 * <p>提高缓存的利用率，如果这种方式效率不行的话，就将这个包下的keyGenerator
 * <p>和RedisConstant以及enhance模块下的CacheEnhanceProcessor还原到2020-01-09之前的版本
 *
 * @author 薛凌康
 */
package com.xzixi.framework.boot.webmvc.config.cache;
