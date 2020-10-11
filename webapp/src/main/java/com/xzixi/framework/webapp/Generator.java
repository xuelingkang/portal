package com.xzixi.framework.webapp;

import com.xzixi.framework.boot.webmvc.generator.CodeGenerator;
import com.xzixi.framework.boot.webmvc.generator.CodeGeneratorConfig;

/**
 * @author xuelingkang
 * @date 2020-10-10
 */
public class Generator {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/portal?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setTablePrefix("t_");
        config.setTables(new String[]{"t_test"});
        config.setAuthor("xuelingkang");
        config.setBaseDir("/webapp/src/main/java");
        config.setEntityPackage("com.xzixi.framework.webapp.model.po");
        config.setMapperPackage("com.xzixi.framework.webapp.mapper");
        config.setDataPackage("com.xzixi.framework.webapp.data");
        config.setDataImplPackage("com.xzixi.framework.webapp.data.impl");
        config.setServicePackage("com.xzixi.framework.webapp.service");
        config.setServiceImplPackage("com.xzixi.framework.webapp.service.impl");
        config.setControllerPackage("com.xzixi.framework.webapp.controller");
        config.setEntityTemplate("code-templates/entity.java");
        config.setMapperTemplate("code-templates/mapper.java");
        config.setDataTemplate("code-templates/data.java");
        config.setDataImplTemplate("code-templates/dataImpl.java");
        config.setServiceTemplate("code-templates/service.java");
        config.setServiceImplTemplate("code-templates/serviceImpl.java");
        config.setControllerTemplate("code-templates/controller.java");
        generator.execute(config);
    }
}
