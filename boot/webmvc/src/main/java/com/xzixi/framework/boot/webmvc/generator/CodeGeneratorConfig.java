package com.xzixi.framework.boot.webmvc.generator;

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
