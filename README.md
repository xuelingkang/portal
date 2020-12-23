# 基于spring boot的快速开发框架

`Talk is cheap. Show you my code.`

## 链接

[Github](https://github.com/xuelingkang/portal)
[码云](https://gitee.com/xuelingkang/portal)

## 目录结构

```
portal
├── boot ········································ 整合常用组件
│   ├── starter ································· 自定义 spring boot starter 聚合工程
│   │   ├── sftp ································ sftp 聚合工程
│   │   │   ├── sftp-client ····················· sftp client 实现
│   │   │   └── sftp-client-spring-boot-starter · sftp client spring boot starter
│   │   └── swagger2 ···························· swagger2 聚合工程
│   │       ├── swagger2-annotations ············ 扩展注解
│   │       ├── swagger2-extension ·············· swagger2 扩展
│   │       └── swagger2-spring-boot-starter ···· swagger2 spring boot starter
│   ├── core ···································· 框架的公共接口和对象
│   ├── persistent ······························ 持久层接口
│   ├── mybatis ································· 基于 mybatis plus 实现持久层
│   ├── elasticsearch ··························· 基于 elasticsearch 实现持久层
│   ├── redis ··································· redis 配置，分布式锁，pipeline工具
│   ├── cache ··································· redis 缓存配置，扩展缓存管理器，定义常用KeyGenerator
│   ├── webmvc ·································· web 项目通用配置，序列化、参数验证、异常拦截等
│   ├── feign ··································· feign 通用配置，支持对象传参和form-data
│   └── enhance ································· 编译期增强，自动管理缓存，必须和mybatis、cache同时使用
├── develop ····································· 开发环境，基于docker-compose
│   ├── mysql ··································· mysql 配置、初始数据
│   ├── elasticsearch ··························· elasticsearch 配置和分词器
│   └── kibana ·································· kibana 配置
└── webapps ····································· webapp 聚合工程
    ├── common ·································· 公共对象、常量
    ├── system ·································· 系统模块，用户、角色、应用等
    ├── content ································· 内容模块
    ├── file ···································· 文件模块
    ├── notice ·································· 通知模块
    ├── queue ··································· 消息队列模块
    ├── task ···································· 任务调度模块
    ├── remote ·································· 远程接口
    ├── gateway ································· 网关
    ├── admin ··································· 后台
    ├── master ·································· 前台
    └── sso ····································· 单点登录
        ├── sso-common ·························· 公共对象、常量
        ├── sso-server ·························· 单点登录server
        └── sso-client ·························· 单点登录client，通用接口和feign接口
```

- [更新日志](./UPDATELOG.md)

## 使用方式

### 导入依赖管理

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.xzixi.framework</groupId>
            <artifactId>boot</artifactId>
            <version>2.2.2</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 导入需要的包

```xml
<!-- sftp连接池启动器 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>sftp-client-spring-boot-starter</artifactId>
</dependency>
```
```xml
<!-- swagger2启动器 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>swagger2-spring-boot-starter</artifactId>
</dependency>
```
```xml
<!-- mybatis-plus实现持久层 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>mybatis</artifactId>
</dependency>
```
```xml
<!-- elasticsearch实现持久层 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>elasticsearch</artifactId>
</dependency>
```
```xml
<!-- redis配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>redis</artifactId>
</dependency>
```
```xml
<!-- 缓存配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>cache</artifactId>
</dependency>
```
```xml
<!-- 常用依赖、配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>webmvc</artifactId>
</dependency>
```
```xml
<!-- feign通用配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>feign</artifactId>
</dependency>
```
```xml
<!-- 编译期增强 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>enhance</artifactId>
    <scope>provided</scope>
</dependency>
```

## 启动webapp

执行以下操作的前提是，***Linux***操作系统，已经安装了***docker***和***docker-compose***，且当前用户有***docker权限***

1. ***重要***，`enhance`模块***不要***开启idea的***Enable annotation processing***选项
2. 在develop目录下执行命令`docker-compose up -d`
3. webapps下的`content`、`file`、`notice`、`queue`、`system`、`task`项目设置VM参数：`-javaagent:/path/to/aspectjweaver/aspectjweaver-1.9.6.jar`
4. 使用maven package命令打包`boot`模块，`mvn clean package -Dmaven.test.skip=true`
5. 启动项目
