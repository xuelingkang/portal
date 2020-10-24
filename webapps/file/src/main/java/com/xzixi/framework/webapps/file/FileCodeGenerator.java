package com.xzixi.framework.webapps.file;

import com.xzixi.framework.boot.webmvc.generator.CodeGenerator;
import com.xzixi.framework.boot.webmvc.generator.CodeGeneratorConfig;

/**
 * @author xuelingkang
 * @date 2020-10-10
 */
public class FileCodeGenerator {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/portal_file?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setTablePrefix("t_");
        config.setTables(new String[]{"t_test"});
        config.setAuthor("xuelingkang");
        config.setBaseDir("/webapps/file/src/main/java");
        config.setEntityBaseDir("/webapps/common/src/main/java");
        config.setEntityPackage("com.xzixi.framework.webapps.common.model.po");
        config.setMapperPackage("com.xzixi.framework.webapps.file.mapper");
        config.setDataPackage("com.xzixi.framework.webapps.file.data");
        config.setDataImplPackage("com.xzixi.framework.webapps.file.data.impl");
        config.setServicePackage("com.xzixi.framework.webapps.file.service");
        config.setServiceImplPackage("com.xzixi.framework.webapps.file.service.impl");
        config.setControllerPackage("com.xzixi.framework.webapps.file.controller");
        generator.execute(config);
    }
}
