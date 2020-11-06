# 简化开发框架

`Talk is cheap. Show you my code.`

## 启动开发环境

1. 在develop目录下执行命令`docker-compose up -d`
2. webapps下除`admin`、`master`、`sso`、`gateway`外，其他项目设置VM参数：`-javaagent:/path/to/aspectjweaver/aspectjweaver-1.9.6.jar`
3. **重要**，`enhance`模块**不要**开启idea的`Enable annotation processing`选项
4. 使用maven package命令打包`enhance`和`starter`模块
5. 启动项目

## boot模块说明

| 编号 | 名称 | 描述 |
| --- | --- | --- |
| 1 | `starter` | 自定义的springboot启动器 |
| 2 | `core` | 公共接口和对象等 |
| 3 | `persistent` | 持久层接口 |
| 4 | `mybatis` | 扩展mybatis-plus实现持久层 |
| 5 | `elasticsearch` | 扩展elasticsearch实现持久层 |
| 6 | `redis` | redis配置 |
| 7 | `cache` | 缓存配置，依赖了redis配置 |
| 8 | `webmvc` | 常用依赖、配置 |
| 9 | `enhance` | 使用注解驱动器实现编译期增强，配合`mybatis`、`cache`，可以自动管理缓存 |

- [更新日志](./UPDATELOG.md)

## maven坐标

```xml
<!-- sftp连接池启动器 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>sftp-client-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- swagger2启动器 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>swagger2-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- mybatis-plus实现持久层 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- elasticsearch实现持久层 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>elasticsearch</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- redis配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>redis</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- 缓存配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>cache</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- 常用依赖、配置 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>webmvc</artifactId>
    <version>3.0.0</version>
</dependency>
```
```xml
<!-- 编译期增强 -->
<dependency>
    <groupId>com.xzixi.framework</groupId>
    <artifactId>enhance</artifactId>
    <version>3.0.0</version>
    <scope>provided</scope>
</dependency>
```
