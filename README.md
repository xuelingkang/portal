# 个人门户服务端

`Talk is cheap. Show you my code.`

## 启动开发环境

1. 在develop目录下执行命令`docker-compose up -d`
2. webapps下除`admin`、`master`、`sso`、`gateway`外，其他项目设置VM参数：`-javaagent:/path/to/aspectjweaver/aspectjweaver-1.9.6.jar`
3. **重要**，`enhance`模块**不要**开启idea的`Enable annotation processing`选项
4. 使用maven package命令打包`enhance`和`starter`模块
5. 启动项目

## boot模块说明

| 编号 | 名称 | 描述 |
| :---: | :---: | :---: |
| 1 | `enhance` | 使用注解驱动器实现编译期增强 |
| 2 | `starter` | 自定义的springboot启动器 |
| 3 | `webmvc` | 项目架构核心 |

- [更新日志](./UPDATELOG.md)

关键模块maven坐标

```xml
<!-- 编译期增强 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>enhance</artifactId>
    <version>2.1.0</version>
    <scope>provided</scope>
</dependency>
```
```xml
<!-- 基础web框架 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>webmvc</artifactId>
    <version>2.1.0</version>
</dependency>
```
```xml
<!-- sftp连接池启动器 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>sftp-client-spring-boot-starter</artifactId>
    <version>2.1.0</version>
</dependency>
```
```xml
<!-- swagger2启动器 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>swagger2-spring-boot-starter</artifactId>
    <version>2.1.0</version>
</dependency>
```
