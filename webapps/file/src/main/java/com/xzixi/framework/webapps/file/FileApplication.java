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

package com.xzixi.framework.webapps.file;

import com.xzixi.framework.boot.cache.annotation.EnableFrameworkCache;
import com.xzixi.framework.boot.webmvc.config.exception.annotation.EnableFrameworkExceptionHandler;
import com.xzixi.framework.boot.webmvc.config.json.annotation.EnableFrameworkJson;
import com.xzixi.framework.boot.mybatis.annotation.EnableFrameworkMybatis;
import com.xzixi.framework.boot.webmvc.config.validation.annotation.EnableFrameworkValidation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuelingkang
 * @date 2020-10-24
 */
@SpringBootApplication
@EnableFrameworkCache
@EnableFrameworkJson
@EnableFrameworkMybatis
@EnableFrameworkValidation
@EnableFrameworkExceptionHandler
@MapperScan(basePackages = "com.xzixi.framework.webapps.file.mapper")
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
