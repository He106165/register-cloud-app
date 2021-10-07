## 平台简介



### 此分支为基于cendev开发平台二次开发微服务框架，适用于在cendev平台上构建具体的业务系统或者功能模块，其中的业务逻辑依赖cendev中的用户、权限、菜单体系内容。
### 如不依赖cendev内置功能，可以自行构建独立工程，或者参考little分支
#### 部署准备：

- JDK >= 1.8(推荐1.8版本)
- MySQL >= 5.5.0 (推荐5.7版本)
- Maven >= 3.0
- lombok 插件



1. 安装redis并设置端口和密码

2. 修改`cendev-config/src/main/resources/config`中onlineuser配置文件`redis`配置

4. 依次绑定host：
```bash
127.0.0.1 eureka.cloud.xg.com
127.0.0.1 gateway.cloud.xg.com
```
    如果要使用eureka集群，请依次绑定eureka7002.com,eureka7003.com后修改各项目中的注释部分
    也可以随意更换本地host，但需要修改相应的配置文件
    
#### 工程结构说明：
```
cendev-cloud-app
|
├──cendev-common --通用包
|  |
|  ├──cendev-common-core --核心工具包
|  |
|  ├──cendev-common-redis --redis工具包
|  |
|  ├──cendev-common-log --日志工具包
|  |
|  ├──cendev-common-auth --权限工具包
|
├──cendev-service-api --服务api模块
|  |
|  ├──cendev-system-api --系统业务api
|
├──cendev-app --微服务
|  |
|  ├──cendev-demo --示例服务1（获取redis中的在线用户）
|  

```



####启动顺序：
- eureka
- config
- gateway
- demo


