package com.xzixi.framework.webapps.content;

import com.xzixi.framework.boot.webmvc.generator.CodeGenerator;
import com.xzixi.framework.boot.webmvc.generator.CodeGeneratorConfig;

/**
 * @author xuelingkang
 * @date 2020-10-10
 */
public class ContentCodeGenerator {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/portal_content?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setTablePrefix("t_");
        config.setTables(new String[]{"t_test"});
        config.setAuthor("xuelingkang");
        config.setBaseDir("/content/src/main/java");
        config.setEntityBaseDir("/common/src/main/java");
        config.setEntityPackage("com.xzixi.framework.webapps.common.model.po");
        config.setMapperPackage("com.xzixi.framework.webapps.content.mapper");
        config.setDataPackage("com.xzixi.framework.webapps.content.data");
        config.setDataImplPackage("com.xzixi.framework.webapps.content.data.impl");
        config.setServicePackage("com.xzixi.framework.webapps.content.service");
        config.setServiceImplPackage("com.xzixi.framework.webapps.content.service.impl");
        config.setControllerPackage("com.xzixi.framework.webapps.content.controller");
        generator.execute(config);
    }
}
