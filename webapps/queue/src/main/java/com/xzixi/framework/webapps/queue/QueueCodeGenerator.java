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

package com.xzixi.framework.webapps.queue;

import com.xzixi.framework.boot.mybatis.generator.CodeGenerator;
import com.xzixi.framework.boot.mybatis.generator.CodeGeneratorConfig;

/**
 * @author xuelingkang
 * @date 2020-10-10
 */
public class QueueCodeGenerator {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/portal_queue?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setTablePrefix("t_");
        config.setTables(new String[]{"t_test"});
        config.setAuthor("xuelingkang");
        config.setBaseDir("/webapps/queue/src/main/java");
        config.setEntityBaseDir("/webapps/common/src/main/java");
        config.setEntityPackage("com.xzixi.framework.webapps.common.model.po");
        config.setMapperPackage("com.xzixi.framework.webapps.queue.mapper");
        config.setDataPackage("com.xzixi.framework.webapps.queue.data");
        config.setDataImplPackage("com.xzixi.framework.webapps.queue.data.impl");
        config.setServicePackage("com.xzixi.framework.webapps.queue.service");
        config.setServiceImplPackage("com.xzixi.framework.webapps.queue.service.impl");
        config.setControllerPackage("com.xzixi.framework.webapps.queue.controller");
        generator.execute(config);
    }
}
