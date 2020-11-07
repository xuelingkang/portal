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

package com.xzixi.framework.boot.elasticsearch.config;

import org.springframework.context.annotation.Configuration;

/**
 * elasticsearch配置
 *
 * @author 薛凌康
 */
@Configuration
public class ElasticsearchConfig {

    static {
        // 解决netty冲突导致初始化client报错的问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}