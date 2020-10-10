package com.xzixi.self.portal.webapp;

import com.xzixi.self.portal.framework.webmvc.generator.CodeGenerator;
import com.xzixi.self.portal.framework.webmvc.generator.CodeGeneratorConfig;

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
        config.setEntityPackage("com.xzixi.self.portal.webapp.model.po");
        config.setMapperPackage("com.xzixi.self.portal.webapp.mapper");
        config.setDataPackage("com.xzixi.self.portal.webapp.data");
        config.setDataImplPackage("com.xzixi.self.portal.webapp.data.impl");
        config.setServicePackage("com.xzixi.self.portal.webapp.service");
        config.setServiceImplPackage("com.xzixi.self.portal.webapp.service.impl");
        config.setControllerPackage("com.xzixi.self.portal.webapp.controller");
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
