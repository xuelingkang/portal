# 个人门户服务端

`Talk is cheap. Show you my code.`

## 启动开发环境

1. 在develop目录下执行命令`docker-compose up -d`
2. webapps下除`common`、`admin`、`master`、`sso`、`gateway`外，其他项目设置VM参数：`-javaagent:/path/to/aspectjweaver/aspectjweaver-1.9.6.jar`
3. 使用maven package命令打包`enhance`和`starter`模块
4. 启动`content`、`sso`、`gateway`项目

### boot模块说明

- [更新日志](./UPDATELOG.md)

| 编号 | 名称 | 描述 |
| :---: | :---: | :---: |
| 1 | `enhance` | 使用注解驱动器实现编译期增强 |
| 2 | `starter` | 自定义的springboot启动器 |
| 3 | `webmvc` | 项目架构核心 |

关键模块maven坐标

```xml
<dependencies>
    <!-- 编译期增强 -->
    <dependency>
        <groupId>com.xzixi.framework</groupId>
        <artifactId>enhance</artifactId>
        <version>xxx</version>
        <scope>provided</scope>
    </dependency>
    <!-- 基础web框架 -->
    <dependency>
        <groupId>com.xzixi.framework</groupId>
        <artifactId>webmvc</artifactId>
        <version>xxx</version>
    </dependency>
    <!-- sftp连接池启动器 -->
    <dependency>
        <groupId>com.xzixi.framework</groupId>
        <artifactId>sftp-client-spring-boot-starter</artifactId>
        <version>xxx</version>
    </dependency>
    <!-- swagger2启动器 -->
    <dependency>
        <groupId>com.xzixi.framework</groupId>
        <artifactId>swagger2-spring-boot-starter</artifactId>
        <version>xxx</version>
    </dependency>
</dependencies>
```
