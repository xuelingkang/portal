package com.xzixi.framework.sso;

import com.xzixi.framework.boot.webmvc.generator.CodeGenerator;
import com.xzixi.framework.boot.webmvc.generator.CodeGeneratorConfig;

/**
 * @author xuelingkang
 * @date 2020-10-21
 */
public class SsoCodeGenerator {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/portal_sso?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setTablePrefix("t_");
        config.setTables(new String[]{"t_test"});
        config.setAuthor("xuelingkang");
        config.setBaseDir("/sso/src/main/java");
        config.setEntityBaseDir("/common/src/main/java");
        config.setEntityPackage("com.xzixi.framework.common.model.po");
        config.setMapperPackage("com.xzixi.framework.sso.mapper");
        config.setDataPackage("com.xzixi.framework.sso.data");
        config.setDataImplPackage("com.xzixi.framework.sso.data.impl");
        config.setServicePackage("com.xzixi.framework.sso.service");
        config.setServiceImplPackage("com.xzixi.framework.sso.service.impl");
        config.setControllerPackage("com.xzixi.framework.sso.controller");
        generator.execute(config);
    }
}