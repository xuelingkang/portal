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

package com.xzixi.framework.boot.mybatis.generator;

import lombok.Data;

/**
 * @author xuelingkang
 * @date 2020-10-09
 */
@Data
public class CodeGeneratorConfig {

    private String jdbcUrl;
    private String driverClassName;
    private String username;
    private String password;
    private String tablePrefix;
    private String[] tables;
    private String author;
    private String baseDir;
    private String entityBaseDir;
    private String mapperBaseDir;
    private String dataBaseDir;
    private String dataImplBaseDir;
    private String serviceBaseDir;
    private String serviceImplBaseDir;
    private String controllerBaseDir;
    private String entityPackage;
    private String mapperPackage;
    private String dataPackage;
    private String dataImplPackage;
    private String servicePackage;
    private String serviceImplPackage;
    private String controllerPackage;
    private String entityTemplate;
    private String mapperTemplate;
    private String dataTemplate;
    private String dataImplTemplate;
    private String serviceTemplate;
    private String serviceImplTemplate;
    private String controllerTemplate;
}
