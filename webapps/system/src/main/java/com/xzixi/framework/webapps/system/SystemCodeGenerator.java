package com.xzixi.framework.webapps.system;

import com.xzixi.framework.boot.webmvc.generator.CodeGenerator;
import com.xzixi.framework.boot.webmvc.generator.CodeGeneratorConfig;

/**
 * @author xuelingkang
 * @date 2020-10-10
 */
public class SystemCodeGenerator {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/portal_system?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setTablePrefix("t_");
        config.setTables(new String[]{"t_app"});
        config.setAuthor("xuelingkang");
        config.setBaseDir("/webapps/system/src/main/java");
        config.setEntityBaseDir("/webapps/common/src/main/java");
        config.setEntityPackage("com.xzixi.framework.webapps.common.model.po");
        config.setMapperPackage("com.xzixi.framework.webapps.system.mapper");
        config.setDataPackage("com.xzixi.framework.webapps.system.data");
        config.setDataImplPackage("com.xzixi.framework.webapps.system.data.impl");
        config.setServicePackage("com.xzixi.framework.webapps.system.service");
        config.setServiceImplPackage("com.xzixi.framework.webapps.system.service.impl");
        config.setControllerPackage("com.xzixi.framework.webapps.system.controller");
        generator.execute(config);
    }
}
