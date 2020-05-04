# blog_multiple
个人博客系统改造为多人博客系统
## 平台要求
- 容器：由于SpringBoot项目使用内置Tomcat，无需额外安装容器
- Java环境：由于项目使用了大多JDK1.8的新特性，为了正常运行该项目，需要JDK1.8及以上的环境
- 数据库环境：由于项目使用了MySQL数据库开发，为了正常运行该项目，需要MySQL5.5及以上的环境
- 缓存环境：由于项目使用了Redis数据库作为缓存，为了正常运行该项目，需要安装Redis3.0.5及以上的环境

## 安装方法
1. 数据库（本项目默认数据库用户名为root，密码为123456，启动端口为3306）
    1. 把jar包中的 blog_remote.sql 文件解压出来
    2. 启动MySQL服务 
    3. 进入数据库控制台
    4. 先创建数据库，执行命令：CREATE DATABASE myblog DEFAULT CHARACTER SET utf8;
    5. 退出数据库控制台，导入数据，执行命令： -u root -p123456 --default-character-set=utf8 myblog < /{项目路径}/myblog.sql
2. 缓存：启动Redis服务
3. 启动项目：
    1. 进入CMD命令行控制台，进入存放项目jar包的目录
    2. 执行命令：java -jar blog.jar
4. 访问主页（默认端口为8080）：http://{服务器域名，例如本地为localhost}:8080/blog
5. 访问后台管理页面
    1. 访问http://{服务器域名，例如本地为localhost}:8080/blog/admin
    2. 登录root超级管理员 账号：admin 密码：123456

## 自定义配置
（如数据库的端口、账号、密码，上下文路径、邮箱的账号、密码，缓存的端口、密码等）
1. 获取jar包,在项目jar包的路径下执行命令：jar tf blog.jar
2. 提取配置文件，执行命令：jar xf blog.jar BOOT-INF/classes/application.properties
3. 修改配置文件，执行命令：vim BOOT-INF/classes/application.properties
4. 更新配置文件，执行命令：jar uf blog.jar BOOT-INF/classes/application.properties
5. 更新jar包，执行命令：jar uf blog.jar

## 代码结构
- java:后端业务逻辑代码
    - 主代码
        - bean：存储系统所需的数据库实体
        - dao：存取数据库
        - service：业务逻辑和数据处理
        - controller：进行逻辑处理和页面跳转
    - 相关代码
        - annotation:系统注解，目前系统主要为“字段校验注解”
        - aspect：切面，目前系统主要为“字段校验切面”
        - config：配置
            - 跨域配置
            - session配置
            - 拦截器配置
            - 缓存配置
            - 权限shiro配置
            - 校验validate配置
        - interceptor：拦截器
            - 前台主页面拦截器
            - 后台管理页面拦截器
            - 登录拦截器
        - filter：过滤器，目前系统主要为“路径匹配过滤器”
        - exception：异常处理
            - 全局异常处理
            - 全局异常处理器
        - enums：枚举类，目前系统主要为“校验规则枚举类”
        - realm：安全数据域
        - cache：缓存配置，主要存储缓存KEY的前缀
        - es：数据处理，目前系统主要为“词林处理”
        - run：系统初始化，目前系统主要为“词林初始”和“模块路径初始”
        - utils：工具包，如“分页需要的页面实体”，“响应状态码实体”，“响应结果实体”等
- resources:系统页面
    - 后端动态模板thymeleaf文件
    - 前端静态页面HTML文件
- webapp:静态资源
    - css文件
        - 前台主系统css文件
        - 后台管理系统css文件
        - 异常处理css文件
        - 公共css文件
    - js文件
        - 前台主系统js文件
        - 后台管理系统js文件
        - 公共js文件
    - image文件
        - 文章图片
        - 分类图片
        - 权限认证图片
        - 轮播图
        - 用户头像
        - 系统图片

## 特色

### 权限

1. 判断是否登录：通过 shiro 判断
2. 判断资源路径：
    1. 采用树的深度遍历算法，获取所有维护模块的路径
    2. 优化：
        - 由于不需要保证数据一致性，系统只需要读不需要写，使用本地缓存，用单例模式设计。
        - 指定 map 空间大小，节省空间开销
3. 判断管理员权限：获取权限，进行匹配

### 安全
1. 加密
    1. 密码加密：采用 MD5 算法加盐，多次加密的方式
    2. url加密：采用 btoa 算法对URL进行编码
2. 校验
    1. 前端使用 jquery.validationEngine 插件进行简单校验
    2. 采用 AOP +注解的方式，对JSON数据进行字段校验
    3. 采用 Hibernate Validate 框架，对普通请求字段进行校验

### 会话信息
1. 使用 session 保存用户信息，更为安全。使用 redis 做分布式缓存，使得服务器集群可以共享会话信息
2. 使用 cookie 保存临时用户信息（如消息数），并放在缓存中，生成token交给用户
