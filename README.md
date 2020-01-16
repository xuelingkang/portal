# 个人门户服务端

`Talk is cheap. Show me the code.`

## 启动开发环境

1. 准备一个centOS7虚拟机，3G内存以上，有root权限
2. 将`develop`目录下文件（文本文件先转为unix）拷贝到虚拟机上
3. 执行`init.sh`，等待安装完成
4. `webapp`项目设置VM参数：`-javaagent:目录路径\aspectjweaver-1.9.4.jar`
5. 使用maven package命令打包`enhance`和`starter`模块
6. 启动`webapp`项目

## 架构设计

- **模块化**
- **面向接口**

### 模块划分

| 编号 | 名称 | 描述 |
| :---: | :---: | :---: |
| 1 | `enhance` | 使用注解驱动器实现编译期增强 |
| 2 | `extension` | 对第三方类库的扩展 |
| 3 | `starter` | 自定义的springboot启动器 |
| 4 | `framework` | 项目架构核心 |
| 5 | `webapp` | web项目 |

### 项目分层

在传统的三层架构基础上增加了数据层

| 编号 | 名称 | 描述 |
| :---: | :---: | :---: |
| 1 | 控制层 | 调用业务层，完成接口功能 |
| 2 | 业务层 | 对数据的访问只能通过调用数据层来完成，禁止直接调用持久层 |
| 3 | 数据层 | 数据层，用来维护数据库、缓存、全文搜索引擎等数据的一致 |
| 4 | 持久层 | 仅供数据层调用 |

- 业务层通过`IBaseService`和`BaseServiceImpl`静态代理了对应数据层的所有方法，一般情况下不需要显式的调用数据层，
业务之间互相调用，也通过service，而不是去调用其他业务的数据层对象，这样可以使代码更加整洁（个人习惯）

### 接口文档

使用swagger2自动生成restful文档，支持调用，使用`SwaggerFilter`将登录和登出接口添加到动态文档中，在其他接口的参数中统一增加了认证参数，
调用接口时，将登录返回的认证参数填入即可

### 参数设计

- `BaseSearchParams`是查询参数的基类，没有特殊需求只需要继承这个类就可以使用，默认会包含分页参数和实体类的所有属性，也可以在子类中扩展属性
- `QueryParams`是mybatis和elasticsearch的查询参数，会在具体的实现类中做转换，也对具体的实现解耦

### 参数校验

- 直接在controller的方法参数上使用validation框架的注解，需要在controller类加上`@Validate`
- 在com.xzixi.self.portal.webapp.model.valid包下创建接口用作校验分组，在controller的方法参数上添加`@Validated`，会按照实体类字段上定义的校验规则进行校验

### 返回值设计

restful接口返回值统一都是`Result`

- `code`：状态码，意思和http状态码一致
- `message`：消息
- `data`：数据

### 缓存设计

- 使用`FuzzyEvictRedisCacheManager`扩展`RedisCacheManager`，`FuzzyEvictRedisCache`扩展`RedisCache`，实现了使用通配符匹配key
- 在com.xzixi.self.portal.webapp.config.redis包定义了一些默认的`KeyGenerator`，包括：
    1. 根据id和id集合生成key
    2. 根据参数列表的hashCode生成key
- `@CacheEnhance`编译期注解的作用：编译期重写`ServiceImpl`的方法，并添加缓存管理注解

### 认证授权

使用spring-security框架，认证支持前后分离，自定义授权逻辑，具体流程：

1. `FilterInvocationSecurityMetadataSourceImpl`，使用`AntPathRequestMatcher`匹配当前请求所需要的权限，将权限封装到返回值
2. `AccessDecisionManagerImpl`，接收上一步的返回值，比对当前用户的权限，没有权限就抛出异常，有权限就`return`执行真正的请求

com.xzixi.self.portal.webapp.config.security包除了以上两个类，还包括一些认证授权成功或失败处理器等

### 全局异常拦截

1. 处理参数错误，给出友好的提示
2. 项目运行期出现的可预期的异常，给出友好的提示
3. 项目运行期出现的不可预期的bug，防止堆栈信息暴露
