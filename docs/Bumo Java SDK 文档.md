# Bumo Java SDK 文档

## 概述
本文档简要概述Bumo Java SDK部分常用接口文档

- [配置](#配置)
	- [简单配置](#简单配置)
	- [高级配置](#高级配置)
- [使用方法](#使用方法)
    - [生成账户](#生成账户)
    - [发起TX](#发起tx)
    - [查询](#查询)
- [Operation说明](#operation说明)
	- [创建账户](#创建账户)
	- [发行资产](#发行资产)
	- [转移资产](#转移资产)
	- [发送BU](#发送bu)
	
- [查询说明](#查询说明)
    - [查询最新区块](#查询最新区块)
    - [查询账户](#查询账户)
	- [根据hash查询交易](#根据hash查询交易)
	- [根据区块序列号查询交易](#根据区块序列号查询交易)
- [示例](#示例)
   - [-生成账户](#-生成账户)
   - [-创建账户](#-创建账户)
   - [-查询账户](#-查询账户)
   - [-发行资产](#-发行资产)
   - [-转移资产](#-转移资产)
   - [-发送BU](#-发送bu)
   - [-根据hash查询交易](#-根据hash查询交易)
   - [-根据区块序列号查询交易](#-根据区块序列号查询交易)
- [错误码](#错误码)

### 配置
Bumo Java SDK无任何依赖框架，使用时只需要载入配置即可运行。配置如下：

### 简单配置

无需分布式服务，单机运行，具体配置如下：

```

    SDKConfig config = new SDKConfig();
    
    SDKProperties sdkProperties = new SDKProperties();
    
    // 为了使SDK访问区块链节点具备高可用能力，SDK提供多个节点负载连接的支持
    
    String ips = "seed1.bumotest.io:26002,seed2.bumotest.io:26002,seed3.bumotest.io:26002";
    sdkProperties.setIps(ips); // 设置http协议的节点IP列表
    
    String eventUtis = "ws://seed1.bumotest.io:26003,ws://seed2.bumotest.io:26003,ws://seed3.bumotest.io:26003";
    sdkProperties.setEventUtis(eventUtis);// 设置tcp协议的节点IP列表
    
    
    config.configSdk(sdkProperties);

    // 完成配置获得spi
    config.getOperationService();
    config.getQueryService();
```
### 高级配置
SDK具备分布式能力，需要引入redis服务，具体配置如下：

```

    SDKConfig config = new SDKConfig();
    
    SDKProperties sdkProperties = new SDKProperties();
    
    // 为了使SDK访问区块链节点具备高可用能力，SDK提供多个节点负载连接的支持
    
    String ips = "seed1.bumotest.io:26002,seed2.bumotest.io:26002,seed3.bumotest.io:26002";
    sdkProperties.setIps(ips); // 设置http协议的节点IP列表
    
    String eventUtis = "ws://seed1.bumotest.io:26003,ws://seed2.bumotest.io:26003,ws://seed3.bumotest.io:26003";
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

### 使用方法
#### 生成账户
通过调用SecureKeyGenerator的generateBumoKeyPair生成账户，例如：
```
BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();
```
详情见[生成账户](#-生成账户)

#### 发起TX
> 创建账户、发行资产、转移资产、发送BU等功能可通过以下四步完成

1. 创建operation
   
通过调用OperationFactory的方法，创建出指定功能的操作，以创建账户为例：
   
```
CreateAccountOperation operation = OperationFactory.newCreateAccountOperation(targetAddress, initBalance);
```
详情见[Operation说明](#operation说明)

2.  构建transaction

   先创建transaction对象，将operation绑定到transaction对象，并设置gasPrice、feeLimit和signer等信息
> 注意：gasPrice和feeLimit的单位是MO，且 1 BU = 10^8 MO
   
   例如：
   
```
Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);
transaction
	.buildAddOperation(bcOperation)
	.buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
	.buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【必填】手续费 (1000000MO = 0.01BU)
	.buildAddSigner(txSubmitAccountPublicKey, txSubmitAccountPrivateKey);
```

3. 提交transaction

提交transaction，等待区块网络确认结果，一般确认时间是10秒，确认超时时间为500秒。
 
```
TransactionCommittedResult result = transaction.commit();
```

4. 获取transaction hash

hash是transaction的唯一标识，开发者可以通过hash查询transaction最终状态。成功和部分失败的交易会返回hash， 而基本参数校验失败的交易（如：序列号有误，余额不足，手续费不足，权重不足等），不会返回hash，会抛出异常。
  
```
result.getHash();
```

#### 查询
调用QueryService的相应的接口，例如：查询账户信息
```
Account account = queryService.getAccount(address);
```
详情见[查询说明](#查询说明)
	
### Operation说明

#### 创建账户

###### 调用方法

```
CreateAccountOperation operation = OperationFactory.newCreateAccountOperation(destAddress, initBalance);

```
###### 入参
   参数     |     类型     |     描述                    |
----------- | ------------ | --------------------------- |
destAddress |    String    | 区块链账户地址              |
initBalance |    long      | 创建账户初始化账户余额，最少10000000 MO（注:1 BU = 10^8 MO） |

#### 发行资产

> 可通过该方法将数字化资产登记到区块链网络中。资产编码和资产发行方区块链地址共同确定唯一性资产。为此再次发行同一种资产该资产数量累加（追加）。

###### 调用方法

```
IssueAssetOperation operation = OperationFactory.newIssueAssetOperation(assetCode, amount);

```

###### 入参
| 参数           |    类型    |   描述                |
| :------------- | ---------- | --------------------- |
| assetCode      |   String   | 资产代码, 如: "HNC"   |
| amount         |   long     | 资产数量              |

#### 转移资产

> 将当前账户（资产发送方）已拥有资产转移（转给）指定账户（资产接收方）

###### 调用方法：

```
PayAssetOperation operation = OperationFactory.newPayAssetOperation(targetAddress, issuerAddress, assetCode, amount);

```

###### 入参
| 参数           |    类型    |   描述            |
| :------------- | ---------- | ----------------- |
| targetAddress  |   String   | 目标账户地址      |
| issuerAddress  |   String   | 资产发行账户地址  |
| assetCode      |   String   | 资产代码          |
| amount    |   long     | 资产数量          |

#### 发送BU
> 将当前账户（发送方）已拥有BU转移（转给）指定账户（接收方）

###### 调用方法

```
OperationFactory.newPayCoinOperation(targetAddress, amount);

```

##### 入参
参数             |      类型    |      描述      |
---------------- | ------------ |  ------------  |
targetAddress    |   String     | 目标区块链地址 | 
sendTokenAmount  |    long      | 发送数量 (单位 : MO　注:1 BU = 10^8 MO)    | 

### 查询说明

#### 查询最新区块

###### 调用方法
```
Ledger ledger = queryService.getLatestLedger();

```

###### 返回参数
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
header      |  [Header](#header对象)  |  区块头    |

###### Header对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
accountTreeHash    |  String    |  账户树哈希    |
closeTime          |  long      |  区块关闭时间    |
consensusValueHash |  String    |  共识内容哈希    |
previousHash       |  String    |  前一个区块头哈希  |
seq                |  long      |  区块序列号      |
txCount            |  long      |  交易数    |
validatorsHash     |  String    |  验证节点列表哈希 |
version            |  long      |  版本号    |
consensusValue     |  [ConsensusValue](#consensusvalue对象)  | 共识内容    |

###### ConsensusValue对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
closeTime    |  long    |  区块关闭时间    |
previousLedgerHash |  String      |  前一个区块hash    |
txset |  [Txset](#txset对象)    |  交易集    |

###### Txset对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
txs    |  [Transaction](#transaction对象)[]    |  区块关闭时间    |


#### 查询账户

###### 调用方法

```
Account account = queryService.getAccount(address);

```
###### 入参
参数    |   类型    |      描述      |
------- | --------- | -------------- |
address |  String   | 账户区块链地址 |

###### 返回参数
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
address     |  String        |  账户区块链地址     |
balance     |  Long          |  账户余额 (单位 : MO　注:1 BU = 10^8 MO)           |
assetsHash  |  String        |  账户资产hash       |
storageHash |  String        |  账户区块链存储hash |
nonce       |  long          |  账户交易次数       |
assets      |  [Asset](#asset对象)[]       |  资产列表           |
contract    |  [Contract](#contract对象)      |  账户合约           |
metadatas   |  [SetMetadata](#setmetadata对象)[] |  备注信息           |
priv        |  [Priv](#priv对象)          |  权限               |

###### Asset对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
amount      |  Long        |  账户资产数量       |
key         |  [Key](#key对象)           |  资产标识         |

###### Key对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
code        |  String        |  资产代码           |
issuer      |  String        |  资产发行方         |

###### Contract对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
payload     |  String        |  合约入参           |

###### SetMetadata对象
参数        |   类型         |       描述          |       
----------- | -------------- | ------------------- |
key         |  String        |  关键字索引         |
value       |  String        |  内容               |
version     |  long          |  版本               |

###### Priv对象
参数         |   类型         |       描述          |       
------------ | -------------- | ------------------- |
masterWeight |  long          |  账户自身权重       |
signers      |  List<[Signer](#signer对象)>     |  签名者列表         |
threshold    |  [Threshold](#threshold对象)     |  门限权重           |

###### Signer对象
参数         |   类型         |       描述          |       
------------ | -------------- | ------------------- |
address      |  String        |  签名者账户地址     |
weight       |  long          |  签名者权重         |

###### Threshold对象
参数            |   类型                |       描述            |       
--------------- | --------------------- | --------------------- |
txThreshold     |  long                 |  交易默认门限权重     |
type_thresholds |  List<[TypeThreshold](#typethreshold对象)>  |  指定操作类型门限权重 |

###### TypeThreshold对象
参数            |   类型                |       描述            |       
--------------- | --------------------- | --------------------- |
type            |  long                 |  指定操作类型         |
threshold       |  long                 |  门限权重，默认-1     |



#### 根据hash查询交易

> 通过hash查询交易的终态信息

###### 调用方法

```
TransactionHistory tx = queryService.getTransactionHistoryByHash(txHash);

```

###### 入参
| 参数           |    类型    |   描述     |
| :------------- | ---------- | ---------- |
| txHash         |   String   | 交易hash   |

###### 返回参数
| 参数    |        类型                |    描述        |
| :------ | -------------------------- | -------------- |
| tx      | [TransactionHistory](#transactionhistory对象)         |  历史结果  |

###### TransactionHistory对象
| 参数          |   类型            |    描述              |
| :------------ | ----------------- | -------------------- |
| totalCount    |   long            | 查询到的交易总数 |
| transactions  |   [Transaction](#transaction对象)[]   | 交易列表         |

###### Transaction对象
| 参数          |   类型            |    描述              |
| :------------ | ----------------- | -------------------- |
| closeTime     |   long            |   历史交易关闭时间   |
| errorCode     |   long            |   错误码             |
| errorDesc     |   String          |   错误描述           |
| ledgerSeq     |   long            |   历史交易所在区块   |
| hash          |   String          |  历史交易hash        |
| signatures    |   [Signature](#signature对象)[]     |  交易的签名列表  |
| transaction   |   [SubTransaction](#subtransaction对象)  |  子交易              |

###### Signature对象
| 参数          |   类型            |    描述              |
| :------------ | ----------------- | -------------------- |
| publicKey     |   String          |   签名者公钥         |
| signData      |   String          |   签名数据           |

###### SubTransaction对象
| 参数          |   类型            |    描述              |
| :------------ | ----------------- | -------------------- |
| sourceAddress |   String          |   交易发起账户地址   |
| nonce         |   long            |   交易序列号         |
| metadata      |   String          |   附加信息           |
| feeLimit      |   long            |   交易手续费 (单位 : MO　注:1 BU = 10^8 MO)         |
| gasPrice      |   long            |   打包费用 (单位 : MO　注:1 BU = 10^8 MO)          |
| operations    |   [Operation](#operation对象)[]     |   操作列表           |

###### Operation对象
| 参数            |   类型            |    描述              |
| :-------------- | ----------------- | -------------------- |
| sourceAddress   |   String          |   源账户地址         |
| type            |   int             |   操作类型           |
| metadata        |   String          |   附加信息           |
| createAccount   |   [CreateAccount](#createaccount对象)   |   创建账户操作       |
| issueAsset      |   [IssueAsset](#issueasset对象)      |   发行资产操作       |
| payAsset        |   [PayAsset](#payasset对象)        |   转移资产操作       |
| payCoin         |   [PayCoin](#paycoin对象)         |   转移BU操作         |

###### CreateAccount对象
| 参数            |   类型            |    描述              |
| :-------------- | ----------------- | -------------------- |
| metadata   |   String          |   附加信息         |
| destAddress        |   String          |   目标账户地址           |
| initBalance   |   CreateAccount   |   初始化资产 (单位 : MO　注:1 BU = 10^8 MO)      |
| initInput      |   String      |   合约入参       |
| contract        |   [Contract](#contract对象)        |   合约       |
| metadatas         |   List<[SetMetadata](#setmetadata对象)>         |   附加信息         |
| priv         |   [Priv](#Priv对象)         |   权限         |

###### IssueAsset对象
| 参数            |   类型            |    描述              |
| :-------------- | ----------------- | -------------------- |
| metadata   |   String          |   附加信息         |
| amount   |   CreateAccount   |   资产数量      |
| code      |   String      |   资产代码       |

###### PayAsset对象
| 参数            |   类型            |    描述              |
| :-------------- | ----------------- | -------------------- |
| metadata   |   String          |   附加信息         |
| destAddress   |   String          |   目标地址         |
| asset   |   [Asset](#asset对象)   |   资产数量      |
| input      |   String      |   合约入参       |

###### PayCoin对象
| 参数            |   类型            |    描述              |
| :-------------- | ----------------- | -------------------- |
| destAddress   |   String          |   目标地址         |
| amount   |   long   |   BU数量 (单位 : MO　注:1 BU = 10^8 MO)     |
| input      |   String      |   合约入参       |

##### 根据区块序列号查询交易

> 通过区块序列号查询交易的终态信息

###### 调用方法

```
TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(ledgerSeq);

```
###### 入参
| 参数           |    类型    |   描述     |
| :------------- | ---------- | ---------- |
| txHash         |   String   | 交易hash   |

返回参数请参考“[根据hash查询交易](#根据hash查询交易)”的说明

### 示例

#### -生成账户
```
// 随机一个Bumo区块账户的公私钥对及区块链地址
BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

// 注：开发者系统需要记录该账户的公私钥对及地址

String accountAddress = keyPair.getBumoAddress(); // Block chain account address
String accountSk = keyPair.getPriKey(); // Block chain account private key
String accountPk = keyPair.getPubKey(); // Block chain account public key
```

#### -创建账户

> 创建新账户需要创建账户操作者(区块链已有账户)花费约0.01BU的交易费用，并且给新账户至少0.1BU的初始化数量，该初始化BU数量由创建账户操作者提供。

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
	
	// 创建账户目前最少初始化账户余额是0.1BU
	CreateAccountOperation operation = OperationFactory.newCreateAccountOperation(accountAddress, ToBaseUnit.BU2MO("0.1")); // 创建创建账户操作
	
	// 构造Tx
	Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);
	transaction.buildAddOperation(operation)
		 .buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
		 .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【必填】手续费 (1000000MO = 0.01BU)
		 .buildAddSigner(txSubmitAccountPublicKey, txSubmitAccountPrivateKey);
	
	// 提交Tx
	TransactionCommittedResult result = transaction.commit();
	
	// 获取交易hash
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```
#### -发行资产

```
try {
	// 资产发行方区块链账户地址
	String issueAssetsAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// 资产发行方账户公钥
	String issueAssetsAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// 资产发行方账户私钥
	String issueAssetsAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	
	// 创建operation
	String assetCode = "HNC";
	Long issueAmount = 1000000000L; // 发行10亿 HNC
	IssueAssetOperation operation = OperationFactory.newIssueAssetOperation(assetCode,issueAmount ); // 创建资产发行操作
	
	// 构建Tx
	Transaction transaction = operationService.newTransaction(issueAssetsAccountAddress);
	transaction.buildAddOperation(operation)
		 .buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
		 .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【必填】手续费 (1000000MO = 0.01BU)
		 .buildAddSigner(issueAssetsAccountPublicKey, issueAssetsAccountPrivateKey);
	
	// 提交Tx
	TransactionCommittedResult result = transaction.commit();
	
	// 获取交易hash
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```

#### -转移资产

```
try {
	// 资产拥有方区块链账户地址
	String assetOwnerAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// 资产拥有方账户公钥
	String assetOwnerAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// 资产拥有方账户私钥
	String assetOwnerAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	
	String destAccountAddress = "buQjM8zQV3VXiUETCaJCogKbFoofnSWufgXo"; // 资产接收方账户地址
	String issuerAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";// 资产发行方
	String assetCode = "HNC"; // 资产ID
	Long sendAmount = 1000000000L; // 发行10亿 HNC
	
	// 创建operation
	PayAssetOperation operation = OperationFactory.newPayAssetOperation(destAccountAddress,issuerAddress,assetCode,sendAmount); // 创建资产发行操作
	
	// 构建Tx
	Transaction transaction = operationService.newTransaction(assetOwnerAccountAddress);
	transaction.buildAddOperation(operation)
		.buildAddGasPrice(1000) // 【必填】 Gas的价格，目前至少1000MO
		.buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【必填】手续费 (1000000MO = 0.01BU)
		.buildAddSigner(assetOwnerAccountPublicKey, assetOwnerAccountPrivateKey);
	
	
	// 提交Tx
	TransactionCommittedResult result = transaction.commit();

	// 获取交易hash
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```

#### -发送bu

```
private static String address = "buQgNykdqpT652bVGp513iXUP2tXY5h4CGC1";
private static String publicKey = "b00180b8cc9291f29636446b068fa0b496443efbc95ded6bc62fb8b5b4f6282e6fc9cbee8fb4";
private static String privateKey = "privbzy1hgypHK8fiew6kpwG5VNYjNDg4QhGjpBJvRWo4L86yJpKFoSL";

try {
    String txSubmitAccountAddress = address;// Trade author block chain account address
    String targetAddress = "buQchyqkRdJeyfrRwQVCEMdxEV2BPSoeQsGx";
	Long sendTokenAmount = ToBaseUnit.BU2MO("0.6");
	
	// 创建operation
	PayCoinOperation operation = OperationFactory.newPayCoinOperation(targetAddress, sendTokenAmount);
	
	// 构建Tx
	Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);
	transaction.buildAddOperation(operation)
		.buildTxMetadata("send BU token")
		.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
	    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
	    .buildAddSigner(publicKey, privateKey);
	    
	// 提交Tx
	TransactionCommittedResult result = transaction.commit();
	
	// 获取交易hash
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}

```

#### -查询最新区块

```
Ledger ledger = queryService.getLatestLedger();
System.out.println(ledger);

```

#### -查询账户

```
String address = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT"; 
Account account = queryService.getAccount(address);

```

#### -根据hash查询交易

```
String txHash = "";
TransactionHistory tx = queryService.getTransactionHistoryByHash(txHash);
```

#### -根据区块序列号查询交易

```
Long ledgerSeq = 1L;
TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(ledgerSeq);
```
### 错误码

> 接口调用错误码信息

参数 | 描述
-----|-----
0     | 成功
1 | 服务内部错误
2 | 参数错误
3 | 对象已存在， 如重复提交交易
4 | 对象不存在，如查询不到账号、TX、区块等
5 | 交易超时，意味着交易已经从交易缓冲队列中删除
20 | 指表达式执行结果为 false，意味着该 TX 当前没有执行成功，但这并不代表在以后的区块不能成功
21 | 指表达式语法分析错误，代表该 TX 一定会失败
90 | 公钥非法
91 | 私钥非法
92 | 资产issue 地址非法
93 | 签名权重不够，达不到操作的门限值
94 | 地址非法
95 | 不在时间范围内
96 | 没有共识
97 | 交易中缺少操作
98 | 交易中包含的操作数量超过限制
99 | 交易的序号非法
100 | 可用内置币余额不足
101 | 目标地址等于源地址
102 | 目标帐号已经存在
103 | 目标账户不存在
104 | 可用资产余额不足
105 | 资产数量过大，超出了int64的范围
111 | 提供的手续费不足
112 | 交易提交过早
113 | 交易提交过晚
114 | 服务器收到的交易数过多,正在处理
120 | 权重值无效
130 | 输入不存在
131 | 输入非法
132 | 非供应链类型交易
144 | 账户的metadata版本号错误
151 | 合约执行失败
152 | 合约语法分析失败
10001 | 操作必须设置目标地址
10002 | 运行状态异常
10003 | 合同地址不能为空
10004 | 资产发行者不能为空
10005 | 资产代码不能为空
10006 | 传输量必须大于0
10007 | 设置元数据不能为空
10008 | 您必须设置操作权重或签名者
10009 | 您必须设置交易阀值或阀值类型
10010 | 发行的资产数量不应该小于0
10011 | 交易阀值不能小于0
10012 | 操作权重不能小于0
10013 | 签名者的权重不应该小于0
10014 | 特定操作阈值不应小于0
10015 | 添加的签名者的地址不能为空
10016 | 特定阈值操作类型不能为空
10017 | BU货币的支付必须大于0
10018 | 评估操作不能为空
10019 | 输入不能是空的
10020 | 最小余额值是非法的
10021 | 输入不能为空
10022 | 元数据的键或值不能是空的
10100 | 事务的发起者不能为空
10101 | 事务签名列表不能为空
10102 | 非正常交易状态
10103 | 交易失败!还没有收到任何通知
10104 | blob不能为空
10105 | 公钥不能为空
10106 | 不要重复生成Blob
10107 | 操作列表不能为空
10108 | 私有密匙不能为空
10109 | 费用值是非法的
10110 | gasPrice值是非法的
90001 | 解析URI错误，请检查
90002 | 解析IP错误，请检查
90003 | 初始化节点管理器失败!请确认至少可以访问一个节点
90004 | 访问底部超时!
90005 | 本地签名验证失败
90006 | 未找到注册的事件处理程序
90007 | 签名异常!请确认公钥或私钥的正确性
90008 | 路由节点故障!请确认监视器配置和访问配置可以匹配
90009 | 超时异常:24小时内未通知，所以事务同步器自动删除
90010 | 复述,锁定超时!
99999 | 内部错误
