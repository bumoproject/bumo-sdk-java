# Bumo Java SDK 文档

## 1 概述
本文档简要概述Bumo Java SDK部分常用接口文档

- [配置](/1)
- [创建账户](/1)
- [查询账户](/1)
- [发行资产](/1)
- [转移资产](/1)
- [查询Tx](/1)

### 2 配置
Bumo Java SDK无任何依赖框架，使用时只需要载入配置即可运行。配置如下：

### 2.1 简单配置

无需分布式服务，单机运行，具体配置如下：

```

    SDKConfig config = new SDKConfig();
    
    SDKProperties sdkProperties = new SDKProperties();
    
    // 为了使SDK访问区块链节点具备高可用能力，SDK提供多个节点负载连接的支持
    
    String ips = "seed1.bumotest.io:26002,seed2.bumotest.io:26002,seed3.bumotest.io:26002";
    sdkProperties.setIps(ips); // 设置http协议的节点IP列表
    
    String eventUtis = "ws://seed1.bumotest.io:26003,ws://seed2.bumotest.io:26003,seed3.bumotest.io:26003";
    sdkProperties.setEventUtis(eventUtis);// 设置tcp协议的节点IP列表
    
    
    config.configSdk(sdkProperties);

    // 完成配置获得spi
    config.getOperationService();
    config.getQueryService();
```
### 2.2 高级配置
SDK具备分布式能力，需要引入redis服务，具体配置如下：

```

    SDKConfig config = new SDKConfig();
    
    SDKProperties sdkProperties = new SDKProperties();
    
    // 为了使SDK访问区块链节点具备高可用能力，SDK提供多个节点负载连接的支持
    
    String ips = "seed1.bumotest.io:26002,seed2.bumotest.io:26002,seed3.bumotest.io:26002";
    sdkProperties.setIps(ips); // 设置http协议的节点IP列表
    
    String eventUtis = "ws://seed1.bumotest.io:26003,ws://seed2.bumotest.io:26003,seed3.bumotest.io:26003";
    sdkProperties.setEventUtis(eventUtis);// 设置tcp协议的节点IP列表
    
    // ###### 需要分布式服务时，需要加入以下配置 ---开始 ###### //
    
    sdkProperties.setRedisSeqManagerEnable(true);// 开启redis
    sdkProperties.setRedisHost("192.168.10.73"); // redis服务IP ,请尽可能使用redis集群
    sdkProperties.setRedisPort(10129); // redis 端口号
    sdkProperties.setRedisPassword("xxxxxx"); // redis 认证密码
    
    // ###### 需要分布式服务时，需要加入以下配置 ---结束 ###### //
    
    config.configSdk(sdkProperties);
    
    

    // 完成配置获得spi
    OperationService operationService = config.getOperationService();
    QueryService queryService = config.getQueryService();
```

### 3 示例说明
完成配置后，可以通过以下示例来操作Bumo区块链网络

#### 3.1 创建账户
> 创建新账户需要创建账户操作者(区块链已有账户)花费约0.01BU的交易费用，并且给新账户至少0.1BU的初始化数量，该初始化BU数量由创建账户操作者提供。