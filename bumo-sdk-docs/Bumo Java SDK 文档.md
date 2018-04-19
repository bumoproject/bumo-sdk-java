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

示例如下：



```
// 随机一个Bumo区块账户的公私钥对及区块链地址
BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

// 注：开发者系统需要记录该账户的公私钥对及地址

String accountAddress = keyPair.getBumoAddress(); // 区块链账户地址 
String accountSk = keyPair.getPriKey(); // 区块链账户私钥
String accountPk = keyPair.getPubKey(); // 区块链账户公钥

try {
    // 交易提交人区块链账户地址
	String txSubmitAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// 交易提交人账户公钥
	String txSubmitAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// 交易提交人账户私钥
	String txSubmitAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);

	// 创建账户目前最少初始化账户余额是0.1BU
	BcOperation bcOperation = OperationFactory.newCreateAccountOperation(accountAddress, ToBaseUnit.BU2MO("0.1")); // 创建创建账户操作
	
	TransactionCommittedResult result = transaction
			.buildAddOperation(bcOperation)
			.buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
		    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【必填】手续费 (1000000MO = 0.01BU)
		    .buildAddSigner(txSubmitAccountPublicKey, txSubmitAccountPrivateKey)
			.commit();
	
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```
#### 3.2 查询账户信息

示例如下：



```
String address = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT"; 
Account account = queryService.getAccount(address);

```
#### 3.3 发行资产
资产发行方发行10亿资产ID为"HNC"的数字资产，示例如下：



```
try {
	// 资产发行方区块链账户地址
	String issueAssetsAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// 资产发行方账户公钥
	String issueAssetsAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// 资产发行方账户私钥
	String issueAssetsAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	
	Transaction transaction = operationService.newTransaction(issueAssetsAccountAddress);
	
	String assetCode = "HNC";
	Long issueAmount = 1000000000L; // 发行10亿 HNC
	BcOperation bcOperation = OperationFactory.newIssueAssetOperation(assetCode,issueAmount ); // 创建资产发行操作
	
	TransactionCommittedResult result = transaction
			.buildAddOperation(bcOperation)
			.buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
		    .buildAddFeeLimit(ToBaseUnit.BU2MO("50.01")) // 【必填】手续费 + 操作费 (1000000MO + 5000000000MO = 50.01BU)
		    .buildAddSigner(issueAssetsAccountPublicKey, issueAssetsAccountPrivateKey)
			.commit();
	
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```
#### 3.4 转移资产

示例如下：



```
try {
	// 资产拥有方区块链账户地址
	String assetOwnerAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// 资产拥有方账户公钥
	String assetOwnerAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// 资产拥有方账户私钥
	String assetOwnerAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	
	Transaction transaction = operationService.newTransaction(assetOwnerAccountAddress);
	
	String destAccountAddress = "buQjM8zQV3VXiUETCaJCogKbFoofnSWufgXo"; // 资产接收方账户地址
	String issuerAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";// 资产发行方
	String assetCode = "HNC"; // 资产ID
	Long sendAmount = 1000000000L; // 发行10亿 HNC
	BcOperation bcOperation = OperationFactory.newPaymentOperation(destAccountAddress,issuerAddress,assetCode,sendAmount); // 创建资产发行操作
	
	TransactionCommittedResult result = transaction
			.buildAddOperation(bcOperation)
			.buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
		    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【必填】手续费 (1000000MO = 0.01BU)
		    .buildAddSigner(assetOwnerAccountPublicKey, assetOwnerAccountPrivateKey)
			.commit();
	
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```

#### 3.5 查询Tx

通过hash查询交易的终态信息

示例如下：



```
String txHash = "";
ServiceResponse tx = queryService.getTransactionResultByHash(txHash);
```