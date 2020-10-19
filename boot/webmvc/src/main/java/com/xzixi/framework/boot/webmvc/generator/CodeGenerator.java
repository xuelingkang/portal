package com.xzixi.framework.boot.webmvc.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.xzixi.framework.boot.webmvc.data.IBaseData;
import com.xzixi.framework.boot.webmvc.data.impl.MybatisPlusDataImpl;
import com.xzixi.framework.boot.webmvc.mapper.IBaseMapper;
import com.xzixi.framework.boot.webmvc.model.BaseModel;
import com.xzixi.framework.boot.webmvc.service.IBaseService;
import com.xzixi.framework.boot.webmvc.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    private static final String DOT = ".";
    private static final String DOT_VM = ".vm";
    private static final String DOT_REG = "\\.";
    private static final String SEPARATOR = File.separator;
    private static final String DEFAULT_ENTITY_TEMPLATE = "code-templates/entity.java";
    private static final String DEFAULT_MAPPER_TEMPLATE = "code-templates/mapper.java";
    private static final String DEFAULT_DATA_TEMPLATE = "code-templates/data.java";
    private static final String DEFAULT_DATA_IMPL_TEMPLATE = "code-templates/dataImpl.java";
    private static final String DEFAULT_SERVICE_TEMPLATE = "code-templates/service.java";
    private static final String DEFAULT_SERVICE_IMPL_TEMPLATE = "code-templates/serviceImpl.java";
    private static final String DEFAULT_CONTROLLER_TEMPLATE = "code-templates/controller.java";

    public void execute(CodeGeneratorConfig config) {
        // 验证参数
        Assert.hasText(config.getJdbcUrl(), "jdbcUrl不能为空！");
        Assert.hasText(config.getDriverClassName(), "driverClassName不能为空！");
        Assert.hasText(config.getUsername(), "username不能为空！");
        Assert.hasText(config.getPassword(), "password不能为空！");
        Assert.notEmpty(config.getTables(), "tables不能为空！");
        Assert.hasText(config.getBaseDir(), "baseDir不能为空！");
        Assert.hasText(config.getEntityPackage(), "entityPackage不能为空！");
        Assert.hasText(config.getMapperPackage(), "mapperPackage不能为空！");
        Assert.hasText(config.getDataPackage(), "dataPackage不能为空！");
        Assert.hasText(config.getDataImplPackage(), "dataImplPackage不能为空！");
        Assert.hasText(config.getServicePackage(), "servicePackage不能为空！");
        Assert.hasText(config.getServiceImplPackage(), "serviceImplPackage不能为空！");
        Assert.hasText(config.getControllerPackage(), "controllerPackage不能为空！");

        // 设置默认值
        if (StringUtils.isBlank(config.getEntityTemplate())) {
            config.setEntityTemplate(DEFAULT_ENTITY_TEMPLATE);
        }
        if (StringUtils.isBlank(config.getMapperTemplate())) {
            config.setMapperTemplate(DEFAULT_MAPPER_TEMPLATE);
        }
        if (StringUtils.isBlank(config.getDataTemplate())) {
            config.setDataTemplate(DEFAULT_DATA_TEMPLATE);
        }
        if (StringUtils.isBlank(config.getDataImplTemplate())) {
            config.setDataImplTemplate(DEFAULT_DATA_IMPL_TEMPLATE);
        }
        if (StringUtils.isBlank(config.getServiceTemplate())) {
            config.setServiceTemplate(DEFAULT_SERVICE_TEMPLATE);
        }
        if (StringUtils.isBlank(config.getServiceImplTemplate())) {
            config.setServiceImplTemplate(DEFAULT_SERVICE_IMPL_TEMPLATE);
        }
        if (StringUtils.isBlank(config.getControllerTemplate())) {
            config.setControllerTemplate(DEFAULT_CONTROLLER_TEMPLATE);
        }

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + config.getBaseDir());
        globalConfig.setAuthor(config.getAuthor());
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true);
        globalConfig.setFileOverride(true);
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(config.getJdbcUrl());
        dataSourceConfig.setDriverName(config.getDriverClassName());
        dataSourceConfig.setUsername(config.getUsername());
        dataSourceConfig.setPassword(config.getPassword());
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(null);
        packageConfig.setEntity(config.getEntityPackage());
        packageConfig.setMapper(config.getMapperPackage());
        packageConfig.setService(config.getServicePackage());
        packageConfig.setServiceImpl(config.getServiceImplPackage());
        packageConfig.setController(config.getControllerPackage());
        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
            @Override
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                String dataClass = "I" + objectMap.get("entity") + "Data";
                String dataImplClass = objectMap.get("entity") + "DataImpl";
                objectMap.put("superDataClass", IBaseData.class.getSimpleName());
                objectMap.put("superDataClassPackage", IBaseData.class.getName());
                objectMap.put("superDataImplClass", MybatisPlusDataImpl.class.getSimpleName());
                objectMap.put("superDataImplClassPackage", MybatisPlusDataImpl.class.getName());
                objectMap.put("dataClass", dataClass);
                objectMap.put("dataPackage", config.getDataPackage());
                objectMap.put("dataClassPackage", config.getDataPackage() + DOT + dataClass);
                objectMap.put("dataImplClass", dataImplClass);
                objectMap.put("dataImplPackage", config.getDataImplPackage());
                objectMap.put("dataImplClassPackage", config.getDataImplPackage() + DOT + dataImplClass);
                return objectMap;
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(config.getDataTemplate() + DOT_VM) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + config.getBaseDir() + SEPARATOR + config.getDataPackage().replaceAll(DOT_REG, SEPARATOR)
                        + SEPARATOR + "I" + tableInfo.getEntityName() + "Data.java";
            }
        });
        focList.add(new FileOutConfig(config.getDataImplTemplate() + DOT_VM) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + config.getBaseDir() + SEPARATOR + config.getDataImplPackage().replaceAll(DOT_REG, SEPARATOR)
                        + SEPARATOR + tableInfo.getEntityName() + "DataImpl.java";
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        generator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.disable(TemplateType.XML);
        templateConfig.setEntity(config.getEntityTemplate());
        templateConfig.setMapper(config.getMapperTemplate());
        templateConfig.setService(config.getServiceTemplate());
        templateConfig.setServiceImpl(config.getServiceImplTemplate());
        templateConfig.setController(config.getControllerTemplate());
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperEntityClass(BaseModel.class);
        strategyConfig.setSuperMapperClass(IBaseMapper.class.getName());
        strategyConfig.setSuperServiceClass(IBaseService.class);
        strategyConfig.setSuperServiceImplClass(BaseServiceImpl.class);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setChainModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setInclude(config.getTables());
        strategyConfig.setTablePrefix(config.getTablePrefix());
        strategyConfig.setControllerMappingHyphenStyle(true);
        generator.setStrategy(strategyConfig);

        generator.execute();
    }
}
