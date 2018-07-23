# Bumo Java SDK

## 概述
本文档详细说明Bumo Java SDK常用接口文档, 使开发者更方便地操作和查询BU区块链。

- [名词解析](#名词解析)
- [请求参数与响应数据格式](#请求参数与响应数据格式)
	- [请求参数](#请求参数)
	- [响应数据](#响应数据)
- [使用方法](#使用方法)
    - [生成SDK实例](#生成sdk实例)
    - [生成公私钥地址](#生成公私钥地址)
    - [有效性校验](#有效性校验)
    - [查询](#查询)
	- [提交交易](#提交交易)
		- [获取账户nonce值](#获取账户nonce值)
		- [构建操作](#构建操作)
		- [构建交易Blob](#构建交易blob)
		- [签名交易](#签名交易)
		- [广播交易](#广播交易)
- [账户服务](#账户服务)
	- [checkValid](#checkvalid-账户)
	- [getInfo](#getinfo-账户)
	- [getNonce](#getnonce)
	- [getBalance](#getbalance-账户)
	- [getAssets](#getassets)
	- [getMetadata](#getmetadata)
- [资产服务](#资产服务)
    - [getInfo](#getinfo-资产)
- [Token服务](#token服务)
    - [checkValid](#checkvalid-token)
	- [allowance](#allowance)
	- [getInfo](#getinfo-token)
	- [getName](#getname)
	- [getSymbol](#getsymbol)
	- [getDecimals](#getdecimals)
	- [getTotalSupply](#gettotalsupply)
	- [getBalance](#getbalance-token)
- [合约服务](#合约服务)
    - [checkValid](#checkvalid-合约)
	- [getInfo](#getinfo-合约)
	- [call](#call)
- [交易服务](#交易服务)
    - [操作说明](#操作说明)
	- [buildBlob](#buildblob)
	- [evaluationFee](#evaluationfee)
	- [sign](#sign)
	- [submit](#submit)
	- [getInfo-交易](#getinfo-交易)
- [区块服务](#区块服务)
    - [getNumber](#getnumber)
	- [checkStatus](#checkstatus)
	- [getTransactions](#gettransactions)
	- [getInfo-区块](#getinfo-区块)
	- [getLatestInfo](#getlatestinfo)
	- [getValidators](#getvalidators)
	- [getLatestValidators](#getlatestvalidators)
	- [getReward](#getreward)
	- [getLatestReward](#getlatestreward)
	- [getFees](#getfees)
	- [getLatestFees](#getlatestfees)
- [错误码](#错误码)

## 名词解析

操作BU区块链： 向BU区块链写入或修改数据

提交交易： 向BU区块链发送写入或修改数据的请求

查询BU区块链： 查询BU区块链中的数据

账户服务： 提供账户相关的有效性校验与查询接口

资产服务： 提供资产相关的查询接口

Token服务：提供合约资产相关的有效性校验与查询接口

合约服务： 提供合约相关的有效性校验与查询接口

交易服务： 提供构建交易Blob接口，签名接口，查询与提交交易接口

区块服务： 提供区块的查询接口

账户nonce值： 每个账户都维护一个序列号，用于用户提交交易时标识交易执行顺序的

## 请求参数与响应数据格式

### 请求参数

接口的请求参数的类名，是[服务名][方法名]Request，比如: 账户服务下的getInfo接口的请求参数格式是AccountGetInfoRequest。

请求参数的成员，是各个接口的入参的成员。例如：账户服务下的getInfo接口的入参成员是address，那么该接口的请求参数的完整结构如下：
```
Class AccountGetInfoRequest {
	String address;
}
```

### 响应数据

接口的响应数据的类名，是[服务名][方法名]Response，比如：账户服务下的getNonce接口的响应数据格式是AccountGetNonceResponse。

响应数据的成员，包括错误码、错误描述和返回结果，比如资产服务下的getInfo接口的响应数据的成员如下：
```
Class AccountGetNonceResponse {
	Integer errorCode;
	String errorDesc;
	AccountGetNonceResult result;
}
```

说明：
1. errorCode: 错误码。0表示无错误，大于0表示有错误
2. errorDesc: 错误描述。
3. result: 返回结果。一个结构体，其类名是[服务名][方法名]Result，其成员是各个接口返回值的成员，例如：账户服务下的getNonce接口的结果类名是AccountGetNonceResult，成员有nonce, 完整结构如下：
```
Class AccountGetNonceResult {
	Long nonce;
}
```

## 使用方法

这里介绍SDK的使用流程，首先需要生成SDK实现，然后调用相应服务的接口，其中服务包括账户服务、资产服务、token服务、合约服务、交易服务、区块服务，接口按使用分类分为生成公私钥地址接口、有效性校验接口、查询接口、提交交易相关接口

### 生成SDK实例

调用SDK的接口getInstance来实现，调用如下：
```
String url = "http://seed1.bumotest.io";
SDK sdk = SDK.getInstance(url);
```

### 生成公私钥地址

此接口生成BU区块链账户的公钥、私钥和地址，直接调用Keypair.generator接口即可，调用如下：
```
Keypair keypair = Keypair.generator();
```

### 有效性校验
此接口用于校验信息的有效性的，直接调用相应的接口即可，比如，校验账户地址有效性，调用如下：
```
// 初始化请求参数
String address = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
AccountCheckValidRequest request = new AccountCheckValidRequest();
request.setAddress(address);

// 调用checkValid接口
AccountCheckValidResponse response = 
sdk.getAccountService().checkValid(request);
if(0 == response.getErrorCode()) {
	System.out.println(response.getResult().isValid());
} else {
	System.out.println("error: " + response.getErrorDesc());
}
```

### 查询
此接口用于查询BU区块链上的数据，直接调用相应的接口即可，比如，查询账户信息，调用如下：
```
// 初始化请求参数
String accountAddress = "buQemmMwmRQY1JkcU7w3nhruo%X5N3j6C29uo";
AccountGetInfoRequest request = new AccountGetInfoRequest();
request.setAddress(accountAddress);

// 调用getInfo接口
AccountGetInfoResponse response =  sdk.getAccountService().getInfo(request);
System.out.println(JSON.toJSONString(response,true));
```

### 提交交易
提交交易的过程包括以下几步：获取账户nonce值，构建操作，构建交易Blob，签名交易和广播交易。

#### 获取账户nonce值

开发者可自己维护各个账户nonce，在提交完一个交易后，自动递增1，这样可以在短时间内发送多笔交易，否则，必须等上一个交易执行完成后，账户的nonce值才会加1。接口调用如下：
```
// 初始化请求参数
String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
AccountGetNonceRequest request = new AccountGetNonceRequest();
request.setAddress(senderAddresss);

// 调用getNonce接口
AccountGetNonceResponse response =  sdk.getAccountService().getNonce(request);

// 赋值nonce
Long nonce = 0L;
if (response.getErrorCode() == 0) {
	AccountGetNonceResult result = response.getResult();
	nonce = result.getNonce();
}
else {
	System.out.println(response.getErrorDesc());
}
```

#### 构建操作

这里的操作是指在交易中做的一些动作。 例如：构建发送BU操作BUSendOperation，调用如下：
```
String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
String destAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauE";
Long amount = ToBaseUnit.BU2MO("10.9");

BUSendOperation operation = new BUSendOperation();
operation.setSourceAddress(senderAddresss);
operation.setDestAddress(destAddress);
operation.setAmount(amount);
```

#### 构建交易Blob

该接口用于生成交易Blob串，接口调用如下：
```
// 初始化请求参数
TransactionBuildBlobRequest request = new TransactionBuildBlobRequest();
request.setSourceAddress(senderAddresss);
request.setNonce(senderNonce);
request.setFeeLimit(feeLimit);
request.setGasPrice(gasPrice);
request.addOperation(operation);

// 调用buildBlob接口
String transactionBlob = null;
TransactionBuildBlobResponse response = sdk.getTransactionService().buildBlob(request);
if (response.getErrorCode() == 0) {
    TransactionBuildBlobResult result = response.getResult();
    String txHash = result.getHash();
    transactionBlob = result.getTransactionBlob();
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

#### 签名交易

该接口用于交易发起者使用私钥对交易进行签名。接口调用如下：
```
// 初始化请求参数
String []signerPrivateKeyArr = {senderPrivateKey};
TransactionSignRequest request = new TransactionSignRequest();
request.setBlob(transactionBlob);
for (int i = 0; i < signerPrivateKeyArr.length; i++) {
	request.addPrivateKey(signerPrivateKeyArr[i]);
}

// 调用sign接口
TransactionSignResponse reponse = sdk.getTransactionService().sign(request);
if (reponse.getErrorCode() == 0) {
    TransactionSignResult result = reponse.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + reponse.getErrorDesc());
}
```

#### 广播交易

该接口用于向BU区块链发送交易，触发交易的执行。接口调用如下：
```
// 初始化请求参数
 TransactionSubmitRequest request = new TransactionSubmitRequest();
request.setTransactionBlob(transactionBlob);
request.setSignatures(transactionSignResponse.getResult().getSignatures());

// 调用submit接口
TransactionSubmitResponse response = sdk.getTransactionService().submit(request);
if (0 == transactionSubmitResponse.getErrorCode()) { 
	System.out.println("交易广播成功，hash=" + response.getResult().getHash());
} else {
	System.out.println("error: " + response.getErrorDesc());
}
```

## 账户服务

账户服务主要是账户相关的接口，包括5个接口：checkValid, getInfo, getNonce, getBalance, getAssets, getMetadata。

### checkValid-账户
> 接口说明

   该接口用于检测账户地址的有效性

> 调用方法

AccounCheckValidResponse checkValid(AccountCheckValidRequest)

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
address     |   String     |  必填，待检测的账户地址   

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
isValid     |   String     |  账户地址是否有效   

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
SYSTEM_ERROR |   20000     |  System error 

> 示例

```
// 初始化请求参数
String address = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
AccountCheckValidRequest request = new AccountCheckValidRequest();
request.setAddress(address);

// 调用checkValid
AccountCheckValidResponse response = sdk.getAccountService().checkValid(request);
if(0 == response.getErrorCode()) {
	System.out.println(response.getResult().isValid());
} else {
	System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-账户

> 接口说明

   该接口用于获取账户信息

> 调用方法

AccountGetInfoResponse GetInfo(AccountGetInfoRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
address     |   String     |  必填，待查询的账户地址   

> 响应数据

   参数    |     类型      |        描述       
--------- | ------------- | ---------------- 
address	  |    String     |    账户地址       
balance	  |    Long       |    账户余额       
nonce	  |    Long       |    账户交易序列号
priv	  | [Priv](#priv) |    账户权限

#### Priv
   成员       |     类型     |        描述       
-----------  | ------------ | ---------------- 
masterWeight |	 Long	    |   账户自身权重
signers	     |[Signer](#signer)[]|   签名者权重
threshold	 |[Threshold](#Threshold)|	门限

#### Signer
   成员       |     类型     |        描述       
-----------  | ------------ | ---------------- 
address	     |   String	    |   签名账户地址
weight	     |   Long	    |   签名账户权重

#### Threshold
   成员       |     类型     |        描述       
-----------  | ------------ | ---------------- 
txThreshold	 |    Long	    |   交易默认门限
typeThresholds|[TypeThreshold](#typethreshold)[]|不同类型交易的门限

#### TypeThreshold
   成员       |     类型     |        描述       
-----------  | ------------ | ---------------- 
type         |    Long	    |    操作类型
threshold    |    Long      |    门限

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
CONNECTNETWORK_ERROR| 11007| Connect network failed
SYSTEM_ERROR |   20000     |  System error 

> 示例

```
// 初始化请求参数
String accountAddress = "buQemmMwmRQY1JkcU7w3nhruo%X5N3j6C29uo";
AccountGetInfoRequest request = new AccountGetInfoRequest();
request.setAddress(accountAddress);

// 调用getInfo接口
AccountGetInfoResponse response =  sdk.getAccountService().getInfo(request);
if (response.getErrorCode() == 0) {
    AccountGetInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getNonce

> 接口说明

   该接口用于获取账户的nonce

> 调用方法

AccountGetNonceResponse getNonce(AccountGetNonceRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
address     |   String     |  必填，待查询的账户地址   

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
nonce       |   Long     |  该账户的交易序列号   

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
CONNECTNETWORK_ERROR| 11007| Connect network failed
SYSTEM_ERROR |   20000     |  System error 

> 示例

```
// 初始化请求参数
String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
AccountGetNonceRequest request = new AccountGetNonceRequest();
request.setAddress(accountAddress);

// 调用getNonce接口
AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
if(0 == response.getErrorCode()){
    System.out.println("账户Nonce:" + response.getResult().getNonce());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getBalance-账户

> 接口说明

   该接口用于账户BU的余额

> 调用方法

AccountGetBalanceResponse getBalance(AccountGetBalanceRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
address     |   String     |  必填，待检测的账户地址   

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
balance     |   Long     |  BU的余额   

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
CONNECTNETWORK_ERROR| 11007| Connect network failed
SYSTEM_ERROR |   20000     |  System error 

> 示例

```
// 初始化请求参数
String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
AccountGetBalanceRequest request = new AccountGetBalanceRequest();
request.setAddress(accountAddress);

// 调用getBalance接口
AccountGetBalanceResponse response = sdk.getAccountService().getBalance(request);
if(0 == response.getErrorCode()){
    System.out.println("BU余额：" + ToBaseUnit.MO2BU(response.getResult().getBalance().toString()) + " BU");
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getAssets

> 接口说明

   该接口用于获取账户所有资产信息

> 调用方法

AccountGetAssets getAssets(AccountGetAssetsRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
address     |   String     |  必填，待检测的账户地址   

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
asset	    | [AssetInfo](#AssetInfo)[] |账户资产

#### AssetInfo

   成员      |     类型     |        描述       
----------- | ------------ | ---------------- 
  key       | [Key](#Key)  | 资产惟一标识
  amount    | int64        | 资产数量
 
 #### Key

   成员   |     类型    |     描述       
-------- | ----------- | -----------
code     |   String    |   资产编码
issuer   |   String    |   资产发行账户地址

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
CONNECTNETWORK_ERROR| 11007| Connect network failed
NO_ASSET_ERROR|11009|The account does not have the asset
SYSTEM_ERROR |   20000     |  System error

> 示例

```
// 初始化请求参数
AccountGetAssetsRequest request = new AccountGetAssetsRequest();
request.setAddress("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");

// 调用getAssets接口
AccountGetAssetsResponse response = sdk.getAccountService().getAssets(request);
if (response.getErrorCode() == 0) {
    AccountGetAssetsResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getMetadata

> 接口说明

   该接口用于获取账户的metadata信息

> 调用方法

AccountGetMetadataResponse getMetadata(AccountGetMetadataRequest);

> 请求参数

   参数   |   类型   |        描述       
-------- | -------- | ---------------- 
address  |  String  |  必填，待检测的账户地址  
key      |  String  |  选填，metadata关键字，长度[1, 1024]

> 响应数据

   参数      |     类型    |        描述       
----------- | ----------- | ---------------- 
metadata    |[MetadataInfo](#MetadataInfo)   |  账户

#### MetadataInfo
   成员      |     类型    |        描述       
----------- | ----------- | ---------------- 
key         |  String     |  metadata的关键词
value       |  String     |  metadata的内容
version     |  int64      |  metadata的版本
   

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR | 11006 | Invalid address
CONNECTNETWORK_ERROR | 11007 | Connect network failed
NO_METADATA_ERROR|11010|The account does not have the metadata
INVALID_DATAKEY_ERROR | 11011 | The length of key must between 1 and 1024
SYSTEM_ERROR | 20000| System error


> 示例

```
// 初始化请求参数
String accountAddress = "buQemmMwmRQY1JkcU7w3nhruo%X5N3j6C29uo";
AccountGetMetadataRequest request = new AccountGetMetadataRequest();
request.setAddress(accountAddress);
request.setKey("hello");

// 调用getMetadata接口
AccountGetMetadataResponse response =  sdk.getAccountService().getMetadata(request);
if (response.getErrorCode() == 0) {
    AccountGetMetadataResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## 资产服务

账户服务主要是资产相关的接口，目前有1个接口：getInfo

### getInfo-资产

> 接口说明

   该接口用于获取账户指定资产信息

> 调用方法

AssetGetInfoResponse getInfo(AssetGetInfoRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
address     |   String    |  必填，待查询的账户地址
code        |   String    |  必填，资产编码，长度[1 1024]
issuer      |   String    |  必填，资产发行账户地址

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
asset	    | [AssetInfo](#AssetInfo)[] |账户资产   

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_ADDRESS_ERROR|11006|Invalid address
CONNECTNETWORK_ERROR|11007|Connect network failed
INVALID_ASSET_CODE_ERROR|11023|The length of asset code must between 1 and 64
INVALID_ISSUER_ADDRESS_ERROR|11027|Invalid issuer address
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
AssetGetInfoRequest request = new AssetGetInfoRequest();
request.setIssuer("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");
request.setCode("TST");
request.setAddress("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");

// 调用getInfo消息
AssetGetInfoResponse response = sdk.getAssetService().getInfo(request);
if (response.getErrorCode() == 0) {
    AssetGetInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Token服务

 账户服务主要是Token相关的接口，目前有8个接口：checkValid, allowance, getInfo, getName, getSymbol, getDecimals, getTotalSupply, getBalance

 ### checkValid-Token

> 接口说明

   该接口用于检测Token是否有效

> 调用方法

TokenCheckValidResponse checkValid(TokenCheckValidRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
contractAddress |   String  |  必填，待检测的Token合约账户地址   

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
isValid     |   String     |  Token是否有效   

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TokenCheckValidRequest request = new TokenCheckValidRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用checkValid接口
TokenCheckValidResponse response = sdk.getTokenService().checkValid(request);
if (response.getErrorCode() == 0) {
    TokenCheckValidResult result = response.getResult();
    System.out.println(result.getValid());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### allowance 

> 接口说明

   该接口用于获取spender仍然被允许从owner提取的金额

> 调用方法

TokenAllowanceResponse allowance(TokenAllowanceRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress|String| 必填，合约账户地址
tokenOwner|String|必填，待分配的目标账户地址
spender|String|必填，授权账户地址

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
allowance|String|允许提取的金额

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
NO_SUCH_TOKEN_ERROR|11030|No such token
INVALID_TOKENOWNER_ERRPR|11035|Invalid token owner
INVALID_SPENDER_ERROR|11043|Invalid spender
GET_ALLOWNANCE_ERROR|11036|Get allowance failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TokenAllowanceRequest request = new TokenAllowanceRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
request.setTokenOwner("buQVU86Jm4FeRW4JcQTD9Rx9NkUkHikYGp6z");
request.setSpender("buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo");

// 调用allowance接口
TokenAllowanceResponse response = sdk.getTokenService().allowance(request);
if (response.getErrorCode() == 0) {
    TokenAllowanceResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-Token

> 接口说明

   该接口用于获取合约token的信息

> 调用方法

TokenGetInfoResponse getInfo(TokenGetInfoRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
symbol|String|合约Token符号
decimals|int|合约数量的精度
totalSupply|String|合约的总供应量
name|String|合约Token的名称

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
NO_SUCH_TOKEN_ERROR|11030|No such token
GET_TOKEN_INFO_ERROR|11066|Get token info failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TokenGetInfoRequest request = new TokenGetInfoRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用getInfo接口
TokenGetInfoResponse response = sdk.getTokenService().getInfo(request);
if (response.getErrorCode() == 0) {
    TokenGetInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getName 

> 接口说明

   该接口用于获取合约token的名称

> 调用方法

TokenGetNameResponse getName(TokenGetNameRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
name     |   String     |  合约Token的名称   |

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
NO_SUCH_TOKEN_ERROR|11030|No such token
GET_TOKEN_INFO_ERROR|11066|Get token info failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TokenGetNameRequest request = new TokenGetNameRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用getName接口
TokenGetNameResponse response = sdk.getTokenService().getName(request);
if (response.getErrorCode() == 0) {
    TokenGetNameResult result = response.getResult();
    System.out.println(result.getName());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getSymbol 

> 接口说明

   该接口用于获取合约token的符号

> 调用方法

TokenGetSymbolResponse getSymbol (TokenGetSymbolRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
symbol     |   String     |  合约Token的符号   |

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
NO_SUCH_TOKEN_ERROR|11030|No such token
GET_TOKEN_INFO_ERROR|11066|底层错误描述
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TokenGetSymbolRequest request = new TokenGetSymbolRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用getSymbol接口
TokenGetSymbolResponse response = sdk.getTokenService().getSymbol(request);
if (response.getErrorCode() == 0) {
    TokenGetSymbolResult result = response.getResult();
    System.out.println(result.getSymbol());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getDecimals 

> 接口说明

   该接口用于获取合约token的精度

> 调用方法

TokenGetDecimalsResponse getDecimals (TokenGetDecimalsRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
decimals     |   Integer     |  合约token精度   |

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
SYSTEM_ERROR |   20000     |  系统错误 |

> 示例

```
// 初始化请求参数
TokenGetDecimalsRequest request = new TokenGetDecimalsRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用getDecimals接口
TokenGetDecimalsResponse response = sdk.getTokenService().getDecimals(request);
if (response.getErrorCode() == 0) {
    TokenGetSymbolResult result = response.getResult();
    System.out.println(result.getSymbol());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getTotalSupply 

> 接口说明

   该接口用于获取合约token的总供应量

> 调用方法

TokenGetTotalSupplyResponse getTotalSupply(TokenGetTotalSupplyRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
totalSupply     |   String     |   合约Token的总供应量  |

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
NO_SUCH_TOKEN_ERROR|11030|No such token
GET_TOKEN_INFO_ERROR|11066|Get token info failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TokenGetTotalSupplyRequest request = new TokenGetTotalSupplyRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用getTotalSupply接口
TokenGetTotalSupplyResponse response = sdk.getTokenService().getTotalSupply(request);
if (response.getErrorCode() == 0) {
    TokenGetTotalSupplyResult result = response.getResult();
    System.out.println(result.getTotalSupply());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getBalance-Token

> 接口说明

   获取合约token拥有者的账户余额

> 调用方法

AccounCheckValidResponse getBalance(AccountCheckValidRequest)

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |
tokenOwner|String|必填，合约Token拥有者的账户地址

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
balance     |   Long     |  token的余额   |

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
INVALID_TOKENOWNER_ERRPR|11035|Invalid token owner
NO_SUCH_TOKEN_ERROR|11030|No such token
GET_TOKEN_INFO_ERROR|11066|Get token info failed
SYSTEM_ERROR|20000|System error


> 示例

```
// 初始化请求参数
TokenGetBalanceRequest request = new TokenGetBalanceRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
request.setTokenOwner("buQYsbybSh7BNckshKU22Pbx8tgk3FDTda4k");

// 调用getBalance接口
TokenGetBalanceResponse response = sdk.getTokenService().getBalance(request);
if (response.getErrorCode() == 0) {
    TokenGetBalanceResult result = response.getResult();
    System.out.println(result.getBalance());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## 合约服务

账户服务主要是合约相关的接口，目前有3个接口：checkValid, getInfo, call

### checkvalid-合约

> 接口说明

   该接口用于检测合约账户的有效性

> 调用方法

ContractCheckValidResponse checkValid(ContractCheckValidRequest)

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
contractAddress     |   String     |  待检测的合约账户地址   

> 响应数据

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
isValid     |   boolean     |  是否有效   

> 错误码

   异常       |     错误码   |   描述   
-----------  | ----------- | -------- 
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
SYSTEM_ERROR |   20000     |  System error 

> 示例

```
// 初始化请求参数
ContractCheckValidRequest request = new ContractCheckValidRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

ContractCheckValidResponse response = sdk.getContractService().checkValid(request);
if (response.getErrorCode() == 0) {
    ContractCheckValidResult result = response.getResult();
    System.out.println(result.getValid());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-合约

> 接口说明

   该接口用于查询合约代码

> 调用方法

ContractGetInfoResponse getInfo (ContractGetInfoRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  待查询的合约账户地址   |

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
contract|[ContractInfo](#contractinfo)|合约信息

#### ContractInfo

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
type|int|合约类型，默认0
payload|String|合约代码

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR|11038|contractAddress is not a contract account
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
ContractGetInfoRequest request = new ContractGetInfoRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// 调用getInfo接口
ContractGetInfoResponse response = sdk.getContractService().getInfo(request);
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### call 

> 接口说明

   该接口用于调试合约代码

> 调用方法

ContractCallesponse call(ContractCallRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
sourceAddress|String|选填，合约触发账户地址
contractAddress|String|选填，合约账户地址，与code不能同时为空
code|String|选填，合约代码，与contractAddress不能同时为空
input|String|选填，合约入参
contractBalance|String|选填，赋予合约的初始 BU 余额
optType|Integer|必填，0: 调用合约的读写接口 init, 1: 调用合约的读写接口 main, 2 :调用只读接口 query
feeLimit|Long|手续费
gasPrice|Long|打包费用


> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
logs|JSONObject|日志信息
queryRets|JSONArray|查询结果集
actualFee|Long|实际费用
stat|[ContractStat](#ContractStat)|合约资源占用信息
txs|[TransactionEnvs](#TransactionEnvs)[]	交易集

#### ContractStat

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
  applyTime|Long|接收时间
  memoryUsage|Long|内存占用量
  stackUsage|Long|堆栈占用量
  step|Long|几步完成

#### TransactionEnvs

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
  transactionEnv|[TransactionEnv](#transactionenv)|交易

#### TransactionEnv

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
transaction|[TransactionInfo](#transactioninfo)|交易内容
trigger|[ContractTrigger](#contracttrigger)|合约触发者

#### TransactionInfo

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
sourceAddress|String|交易发起的源账户地址
feeLimit|int64|交易费用
gasPrice|int64|交易打包费用
nonce|int64|交易序列号
operations|[Operation](#operation)[]|操作列表

#### ContractTrigger
   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
transaction|[TriggerTransaction](#triggertransaction)|触发交易

#### Operation

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
type|int|操作类型
sourceAddress|String|操作发起源账户地址
metadata|String|备注
createAccount|[OperationCreateAccount](#operationcreateaccount)|创建账户操作
issueAsset|[OperationIssueAsset](#operationissueasset)|发行资产操作
payAsset|[OperationPayAsset](#operationpayasset)|转移资产操作
payCoin|[OperationPayCoin](#operationpaycoin)|发送BU操作
setMetadata|[OperationSetMetadata](#operationsetmetadata)|设置metadata操作
setPrivilege|[OperationSetPrivilege](#operationsetprivilege)|设置账户权限操作
log|[OperationLog](#operationlog)|记录日志

#### TriggerTransaction

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
hash|String|交易hash

#### OperationCreateAccount

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
destAddress|String|目标账户地址
contract|[Contract](#contract)|合约信息
priv|[Priv](#priv)|账户权限
metadata|[MetadataInfo](#metadatainfo)[]|账户
initBalance	int64	账户资产
initInput	String	合约init函数的入参

#### Contract

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
type|Integer|约的语种，默认不赋值
payload|String|对应语种的合约代码

#### MetadataInfo

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
key|String|metadata的关键词
value|String|metadata的内容
version|int64|metadata的版本

#### OperationIssueAsset

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
code|String|资产编码
amount|int64|资产数量

#### OperationPayAsset

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
destAddress|String|待转移的目标账户地址
asset|[AssetInfo](#assetinfo)|账户资产
input|String|合约main函数入参

#### OperationPayCoin

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
destAddress|String|待转移的目标账户地址
amount|int64|待转移的BU数量
input|String|合约main函数入参

#### OperationSetMetadata

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
key|String|metadata的关键词
value|String|metadata的内容
version|int64|metadata的版本
deleteFlag|boolean|是否删除metadata

#### OperationSetPrivilege

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
masterWeight|String|账户自身权重，大小[0, max(uint32)]
signers|[Signer](#signer)[]|签名者权重列表
txThreshold|String|交易门限，大小[0, max(int64)]
typeThreshold|[TypeThreshold](#typethreshold)|指定类型交易门限

#### OperationLog

   成员      |     类型     |        描述       |
----------- | ------------ | ---------------- |
topic|String|日志主题
data|String[]|日志内容

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_CODE_BOTH_NULL_ERROR|11063|ContractAddress and code cannot be empty at the same time
INVALID_OPTTYPE_ERROR|11064|OptType must between 0 and 2
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
ContractCallRequest request = new ContractCallRequest();
request.setCode("\"use strict\";log(undefined);function query() { getBalance(thisAddress); }");
request.setFeeLimit(1000000000L);
request.setOptType(2);

// 调用call接口
ContractCallResponse response = sdk.getContractService().call(request);
if (response.getErrorCode() == 0) {
    ContractCallResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## 交易服务

交易服务主要是交易相关的接口，目前有5个接口：buildBlob, evaluationFee, sign, submit, getInfo。

其中调用buildBlob之前需要构建一些操作，目前操作有16种，分别是AccountActivateOperation，AccountSetMetadataOperation, AccountSetPrivilegeOperation, AssetIssueOperation, AssetSendOperation, BUSendOperation, TokenIssueOperation, TokenTransferOperation, TokenTransferFromOperation, TokenApproveOperation, TokenAssignOperation, TokenChangeOwnerOperation, ContractCreateOperation, ContractInvokeByAssetOperation, ContractInvokeByBUOperation, LogCreateOperation

### 操作说明

> BaseOperation

   成员变量    |     类型  |        描述                           
------------- | -------- | ----------------------------------   
sourceAddress |   String |  选填，操作源账户      
metadata      |   String |  选填，备注，必须是16进制字符串 

>  AccountActivateOperation

继承于BaseOperation，feeLimit固定是0.01 BU

   成员变量    |     类型  |        描述                           
------------- | -------- | ---------------------------------- 
sourceAddress |   String |  选填，操作源账户  
destAddress   |   String |  必填，目标账户地址                     
initBalance   |   Long   |  必填，初始化资产，大小[0.1, max(int64)] 
metadata|String|选填，备注

> AccountSetMetadataOperation

继承于BaseOperation，feeLimit固定是0.01 BU

   成员变量    |     类型   |        描述                         
------------- | --------- | ------------------------------- 
sourceAddress |   String |  选填，操作源账户
key           |   String  |  必填，metadata的关键词，长度[1, 1024]
value         |   String  |  必填，metadata的内容，长度[0, 256000]
version       |   Long    |  选填，metadata的版本
deleteFlag    |   Boolean |  选填，是否删除metadata
metadata|String|选填，备注           

> AccountSetPrivilegeOperation

继承于BaseOperation，feeLimit固定是0.01 BU

   成员变量    |     类型   |        描述               
------------- | --------- | --------------------------
sourceAddress |   String |  选填，操作源账户
masterWeight|String|选填，账户自身权重，大小[0, max(uint32)]
signers|[Signer](#signer)[]|选填，签名者权重列表
txThreshold|String|选填，交易门限，大小[0, max(int64)]
typeThreshold|[TypeThreshold](#typethreshold)[]|选填，指定类型交易门限
metadata|String|选填，备注

> AssetIssueOperation

继承于BaseOperation，feeLimit固定是50.01 BU

   成员变量    |     类型   |        描述             
------------- | --------- | ------------------------
sourceAddress|String|选填，发起该操作的源账户地址
code|String|必填，资产编码，长度[1 1024]
assetAmount|int64|必填，资产发行数量，大小[0, max(int64)]
metadata|String|选填，备注

> AssetSendOperation

继承于BaseOperation，feeLimit固定是0.01 BU

   成员变量    |     类型   |        描述            
------------- | --------- | ----------------------
sourceAddress|String|选填，发起该操作的源账户地址
destAddress|String|必填，目标账户地址
code|String|必填，资产编码，长度[1 1024]
issuer|String|必填，资产发行账户地址
assetAmount|int64|必填，资产数量，大小[ 0, max(int64)]
metadata|String|选填，备注

> BUSendOperation

继承于BaseOperation，feeLimit固定是0.01 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
destAddress|String|必填，目标账户地址
buAmount|int64|必填，资产发行数量，大小[0, max(int64)]
metadata|String|选填，备注

> TokenIssueOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
initBalance|int64|必填，给合约账户的初始化资产，大小[1, max(64)]
name|String|必填，token名称，长度[1, 1024]
symbol|String|必填，token符号，长度[1, 1024]
decimals|int|必填，token数量的精度，大小[0, 8]
supply|String|必填，token发行的总供应量(不带精度)，大小[1, max(int64)]
metadata|String|选填，备注

> TokenTransferOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
destAddress|String|必填，待转移的目标账户地址
tokenAmount|String|必填，待转移的token数量，大小[1, int(64)]
metadata|String|选填，备注

> TokenTransferFromOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
fromAddress|String|必填，待转移的源账户地址
destAddress|String|必填，待转移的目标账户地址
tokenAmount|String|必填，待转移的token数量，大小[1, int(64)]
metadata|String|选填，备注

> TokenApproveOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
spender|String|必填，授权的账户地址
tokenAmount|String|必填，被授权的待转移的token数量，大小[1, int(64)]
metadata|String|选填，备注

> TokenAssignOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
destAddress|String|必填，待分配的目标账户地址
tokenAmount|String|必填，待分配的token数量，大小[1, int(64)]
metadata|String|选填，备注

> TokenChangeOwnerOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
tokenOwner|String|必填，待分配的目标账户地址
metadata|String|选填，备注

> ContractCreateOperation

继承于BaseOperation，feeLimit固定是0.02 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
initBalance|int64|必填，给合约账户的初始化资产，大小[1, max(64)]
type|Integer|选填，合约的语种，默认是0
payload|String|必填，对应语种的合约代码
initInput|String|选填，合约代码中init方法的入参
metadata|String|选填，备注

> ContractInvokeByAssetOperation

继承于BaseOperation，feeLimit要根据合约中执行交易来做添加手续费，首先发起交易手续费是0.01BU，然后合约中的交易也需要交易发起者添加相应交易的手续费

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
code|String|选填，资产编码，长度[0, 1024]，当为null时，仅触发合约
issuer|String|选填，资产发行账户地址，当为null时，仅触发合约
amount|int64|选填资产数量，大小[0, max(int64)]，当是0时，仅触发合约
input|String|选填，待触发的合约的main()入参
metadata|String|选填，备注

> ContractInvokeByBUOperation

继承于BaseOperation，feeLimit要根据合约中执行交易来做添加手续费，首先发起交易手续费是0.01BU，然后合约中的交易也需要交易发起者添加相应交易的手续费

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
contractAddress|String|必填，合约账户地址
amount|int64|选填，资产发行数量，大小[0, max(int64)]，当0时仅触发合约
input|String|选填，待触发的合约的main()入参
metadata|String|选填，备注

> LogCreateOperation

继承于BaseOperation，feeLimit固定是0.01 BU

   成员变量    |     类型   |        描述          
------------- | --------- | ---------------------
sourceAddress|String|选填，发起该操作的源账户地址
topic|String|必填，日志主题，长度[1, 128]
data|String[]|必填，日志内容，每个字符串长度[1, 1024]
metadata|String|选填，备注

### buildBlob

> 接口说明

   该接口用于交易Blob的生成

> 调用方法

TransactionBuildBlobResponse buildBlob(TransactionBuildBlobRequest);

> 请求参数

   参数      |     类型     |        描述       
----------- | ------------ | ---------------- 
sourceAddress|String|必填，发起该操作的源账户地址
nonce|int64|必填，待发起的交易序列号，函数里+1，大小[1, max(int64)]
gasPrice|int64|必填，交易打包费用，单位MO，1 BU = 10^8 MO，大小[1000, max(int64)]
feeLimit|int64|必填，交易手续费，单位MO，1 BU = 10^8 MO，大小[1000000, max(int64)]
operation|OperationBase[]|必填，待提交的操作列表，不能为空
ceilLedgerSeq|long|选填，区块高度限制，大于等于0，是0时不限制
metadata|String|选填，备注


> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
transactionBlob|String|Transaction序列化后的16进制字符串
hash|String|交易hash

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_NONCE_ERROR|11048|Nonce must between 1 and max(int64)
INVALID_ GASPRICE_ERROR|11049|Amount must between gasPrice in block and max(int64)
INVALID_FEELIMIT_ERROR|11050|FeeLimit must between 1000000 and max(int64)
INVALID_OPERATION_ERROR|11051|Operation cannot be resolved
INVALID_CEILLEDGERSEQ_ERROR|11052|CeilLedgerSeq must be equal or bigger than 0
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化变量
String senderAddresss = "buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea";
String destAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauE";
Long amount = ToBaseUnit.BU2MO("10.9");
Long gasPrice = 1000L;
Long feeLimit = ToBaseUnit.BU2MO("0.01");
Long nonce = 1L;

// 构建sendBU操作
BUSendOperation operation = new BUSendOperation();
operation.setSourceAddress(senderAddresss);
operation.setDestAddress(destAddress);
operation.setAmount(amount);

// 初始化请求参数
TransactionBuildBlobRequest request = new TransactionBuildBlobRequest();
request.setSourceAddress(senderAddresss);
request.setNonce(senderNonce);
request.setFeeLimit(feeLimit);
request.setGasPrice(gasPrice);
request.addOperation(operation);

// 调用buildBlob接口
String transactionBlob = null;
TransactionBuildBlobResponse response = sdk.getTransactionService().buildBlob(request);
if (response.getErrorCode == 0) {
    TransactionBuildBlobResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### evaluationFee

> 接口说明

   该接口实现交易的费用评估

> 调用方法

TransactionEvaluationFeeResponse evaluationFee (TransactionEvaluationFeeRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
sourceAddress|String|必填，发起该操作的源账户地址
nonce|int64|必填，待发起的交易序列号，大小[1, max(int64)]
operation|OperationBase[]|必填，待提交的操作列表，不能为空
signtureNumber|int32|选填，待签名者的数量，默认是1，大小[1, max(int32)]
metadata|String|选填，备注

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
txs     |   [TestTx](#testtx)[]     |  评估交易集   |

#### TestTx

   成员变量      |     类型     |        描述       |
----------- | ------------ | ---------------- |
transactionEnv| [TestTransactionFees](#testtransactionfees)| 评估交易费用

#### TestTransactionFees

   成员变量      |     类型     |        描述       |
----------- | ------------ | ---------------- |
transactionFees|[TransactionFees](#transactionfees)|交易费用

#### TransactionFees
   成员变量      |     类型     |        描述       |
----------- | ------------ | ---------------- |
feeLimit|Long|交易费用
gasPrice|Long|打包费用

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_NONCE_ERROR|11045|Nonce must between 1 and max(int64)
INVALID_OPERATION_ERROR|11051|Operation cannot be resolved
OPERATIONS_ONE_ERROR|11053|One of operations error
INVALID_SIGNATURENUMBER_ERROR|11054|SignagureNumber must between 1 and max(int32)
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化变量
String senderAddresss = "buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea";
String destAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauE";
Long amount = ToBaseUnit.BU2MO("10.9");
Long gasPrice = 1000L;
Long feeLimit = ToBaseUnit.BU2MO("0.01");
Long nonce = 1L;

// 构建sendBU操作
BUSendOperation buSendOperation = new BUSendOperation();
buSendOperation.setSourceAddress(senderAddresss);
buSendOperation.setDestAddress(destAddress);
buSendOperation.setAmount(amount);

// 初始化评估交易请求参数
TransactionEvaluationFeeRequest request = new TransactionEvaluationFeeRequest();
request.addOperation(buSendOperation);
request.setSourceAddress(senderAddresss);
request.setNonce(nonce);
request.setSignatureNumber(1);
request.setMetadata(HexFormat.byteToHex("evaluation fees".getBytes()));

// 调用evaluationFee接口
TransactionEvaluationFeeResponse response = sdk.getTransactionService().evaluationFee(request);
if (response.getErrorCode() == 0) {
    TransactionEvaluationFeeResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### sign

> 接口说明

   该接口用于实现交易的签名

> 调用方法

TransactionSignResponse sign(TransactionSignRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blob|String|必填，发起该操作的源账户地址
privateKeys|String[]|必填，私钥列表


> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
signatures|[Signature](#signature)	签名后的数据列表

#### Signature

   成员变量      |     类型     |        描述       |
----------- | ------------ | ---------------- |
  signData|int64|签名后数据
  publicKey|int64|公钥

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOB_ERROR|11056|Invalid blob
PRIVATEKEY_NULL_ERROR|11057|PrivateKeys cannot be empty
PRIVATEKEY_ONE_ERROR|11058|One of privateKeys error
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
String issuePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
String []signerPrivateKeyArr = {issuePrivateKey};
String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
TransactionSignRequest request = new TransactionSignRequest();
request.setBlob(transactionBlob);
for (int i = 0; i < signerPrivateKeyArr.length; i++) {
    request.addPrivateKey(signerPrivateKeyArr[i]);
}
TransactionSignResponse response = sdk.getTransactionService().sign(request);
if(0 == response.getErrorCode()){
	System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
	System.out.println("error: " + response.getErrorDesc());
}
```

### submit

> 接口说明

   该接口用于实现交易的提交，只有提交交易中有失败的，就抛出Exception，提示有交易失败，具体哪个交易失败，遍历返回数组中TransactionResult的错误码和错误描述

> 调用方法

TransactionSubmitResponse submit(TransactionSubmitRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
  blob|String|必填，交易blob
  signature|[Signature](#signature)[]|必填，签名列表

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
hash|String|交易hash

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOB_ERROR|11052|Invalid blob
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
Signature signature = new Signature();
signature.setSignData("D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A");
signature.setPublicKey("b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477");
TransactionSubmitRequest request = new TransactionSubmitRequest();
request.setTransactionBlob(transactionBlob);
request.setSignatures();
TransactionSubmitResponse response = sdk.getTransactionService().submit(request);
if (0 == response.getErrorCode()) { // 交易广播成功
    System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-交易

> 接口说明

   该接口用于实现根据交易hash查询交易

> 调用方法

TransactionGetInfoResponse getInfo (TransactionGetInfoRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
hash|String|交易hash

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
totalCount|int64|返回的总交易数
transactions|[TransactionHistory](#transactionhistory)[]|交易内容

#### TransactionHistory

   成员变量  |     类型     |        描述       |
----------- | ------------ | ---------------- |
actualFee|String|交易实际费用
closeTime|int64|交易关闭时间
errorCode|int64|交易错误码
errorDesc|String|交易描述
hash|String|交易hash
ledgerSeq|int64|区块序列号
transaction|[TransactionInfo](#transactioninfo)|交易内容列表
signatures|[Signature](#signature)[]|签名列表
txSize|int64|交易大小

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_HASH_ERROR|11055|Invalid transaction hash
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
TransactionGetInfoRequest request = new TransactionGetInfoRequest();
request.setHash("0d67997af3777b977deb648082c8288b4ba46b09d910d016f0942bcd853ad518");

// 调用getInfo接口
TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## 区块服务

区块服务主要是区块相关的接口，目前有11个接口：getNumber, checkStatus, getTransactions , getInfo, getLatestInfo, getValidators, getLatestValidators, getReward, getLatestReward, getFees, getLatestFees。

### getNumber

> 接口说明

   该接口用于查询最新的区块高度

> 调用方法

BlockGetNumberResponse getNumber();

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
header|BlockHeader|区块头
blockNumber|int64|最新的区块高度，对应底层字段seq

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 调用getNumber接口
BlockGetNumberResponse response = sdk.getBlockService().getNumber();
if(0 == response.getErrorCode()){
	System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
	System.out.println("error: " + response.getErrorDesc());
}
```

### checkStatus

> 接口说明

   该接口用于检查本地节点区块是否同步完成

> 调用方法

BlockCheckStatusResponse checkStatus();

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
isSynchronous    |   boolean     |  区块是否同步   |

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 调用checkStatus
BlockCheckStatusResponse response = sdk.getBlockService().checkStatus();
if(0 == response.getErrorCode()){
	System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
	System.out.println("error: " + response.getErrorDesc());
}
```

### getTransactions

> 接口说明

   该接口用于查询指定区块高度下的所有交易

> 调用方法

   BlockGetTransactionsResponse getTransactions (BlockGetTransactionRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockNumber|int64|必填，待查询的区块高度

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
totalCount|int64|返回的总交易数
transactions|[TransactionHistory](#transactionhistory)[]|交易内容

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
Long blockNumber = 617247L;// 第617247区块
BlockGetTransactionsRequest request = new BlockGetTransactionsRequest();
request.setBlockNumber(blockNumber);

// 调用getTransactions接口
BlockGetTransactionsResponse response = sdk.getBlockService().getTransactions(request);
if(0 == response.getErrorCode()){
    System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-区块

> 接口说明

   该接口用于获取区块信息

> 调用方法

BlockGetInfoResponse getInfo(BlockGetInfoRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockNumber|int64|必填，待查询的区块高度

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
closeTime|int64|区块关闭时间
number|int64|区块高度
txCount|int64|交易总量
version|String|区块版本

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
BlockGetInfoRequest request = new BlockGetInfoRequest();
request.setBlockNumber(629743L);

// 调用getInfo接口
BlockGetInfoResponse response = sdk.getBlockService().getInfo(request);
if (response.getErrorCode() == 0) {
    BlockGetInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestInfo

> 接口说明

   该接口用于获取最新区块信息

> 调用方法

BlockGetLatestInfoResponse getLatestInfo();

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
closeTime|int64|区块关闭时间
number|int64|区块高度，对应底层字段seq
txCount|int64|交易总量
version|String|区块版本


> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 调用getLatestInfo接口
BlockGetLatestInfoResponse response = sdk.getBlockService().getLatestInfo();
if (response.getErrorCode() == 0) {
    BlockGetLatestInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getValidators

> 接口说明

   该接口用于获取指定区块中所有验证节点数

> 调用方法

BlockGetValidatorsResponse getValidators(BlockGetValidatorsRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockNumber|int64|必填，待查询的区块高度

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
validators|[ValidatorInfo](#validatorinfo)[]|验证节点列表

#### ValidatorInfo

   成员变量  |     类型     |        描述       |
----------- | ------------ | ---------------- |
address|String|共识节点地址
plegeCoinAmount|int64|验证节点押金

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
BlockGetValidatorsRequest request = new BlockGetValidatorsRequest();
request.setBlockNumber(629743L);

// 调用getValidators接口
BlockGetValidatorsResponse response = sdk.getBlockService().getValidators(request);
if (response.getErrorCode() == 0) {
    BlockGetValidatorsResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestValidators

> 接口说明

   该接口用于获取最新区块中所有验证节点数

> 调用方法

BlockGetLatestValidatorsResponse getLatestValidators();

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
validators|[ValidatorInfo](#validatorinfo)[]|验证节点列表

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 调用getLatestValidators接口
BlockGetLatestValidatorsResponse response = sdk.getBlockService().getLatestValidators();
if (response.getErrorCode() == 0) {
    BlockGetLatestValidatorsResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getReward

> 接口说明

   该接口用于获取指定区块中的区块奖励和验证节点奖励

> 调用方法

BlockGetRewardResponse getReward(BlockGetRewardRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockNumber|int64|必填，待查询的区块高度

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockReward|int64|区块奖励数
validatorsReward|[ValidatorReward](#validatorreward)[]|验证节点奖励情况

#### ValidatorReward

   成员变量  |     类型     |        描述       |
----------- | ------------ | ---------------- |
  validator|String|验证节点地址
  reward|int64|验证节点奖励


> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
BlockGetRewardRequest request = new BlockGetRewardRequest();
request.setBlockNumber(629743L);

// 调用getReward接口
BlockGetRewardResponse response = sdk.getBlockService().getReward(request);
if (response.getErrorCode() == 0) {
    BlockGetRewardResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestReward

> 接口说明

   获取最新区块中的区块奖励和验证节点奖励

> 调用方法

BlockGetLatestRewardResponse getLatestReward();

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockReward|int64|区块奖励数
validatorsReward|[ValidatorReward](#validatorreward)[]|验证节点奖励情况

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 调用getLatestReward接口
BlockGetLatestRewardResponse response = sdk.getBlockService().getLatestReward();
if (response.getErrorCode() == 0) {
    BlockGetLatestRewardResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getFees

> 接口说明

   获取指定区块中的账户最低资产限制和打包费用

> 调用方法

BlockGetFeesResponse getFees(BlockGetFeesRequest);

> 请求参数

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
blockNumber|int64|必填，待查询的区块高度

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
fees|[Fees](#fees)|费用

#### Fees

   成员变量  |     类型     |        描述       |
----------- | ------------ | ---------------- |
baseReserve|int64|账户最低资产限制
gasPrice|int64|打包费用，单位MO，1 BU = 10^8 MO

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 初始化请求参数
BlockGetFeesRequest request = new BlockGetFeesRequest();
request.setBlockNumber(629743L);

// 调用getFees接口
BlockGetFeesResponse response = sdk.getBlockService().getFees(request);
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestFees

> 接口说明

   该接口用于获取最新区块中的账户最低资产限制和打包费用

> 调用方法

BlockGetLatestFeesResponse getLatestFees();

> 响应数据

   参数      |     类型     |        描述       |
----------- | ------------ | ---------------- |
fees|[Fees](#fees)|费用

> 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Connect network failed
SYSTEM_ERROR|20000|System error

> 示例

```
// 调用getLatestFees接口
BlockGetLatestFeesResponse response = sdk.getBlockService().getLatestFees();
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## 错误码

   异常       |     错误码   |   描述   |
-----------  | ----------- | -------- |
ACCOUNT_CREATE_ERROR|11001|Create account failed
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_DESTADDRESS_ERROR|11003|Invalid destAddress
INVALID_INITBALANCE_ERROR|11004|InitBalance must between 1 and max(int64) 
SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR|11005|SourceAddress cannot be equal to destAddress
INVALID_ADDRESS_ERROR|11006|Invalid address
CONNECTNETWORK_ERROR|11007|Connect network failed
METADATA_NOT_HEX_STRING_ERROR|11008|Metadata must be a hex string
NO_ASSET_ERROR|11009|The account does not have the asset
NO_METADATA_ERROR|11010|The account does not have the metadata
INVALID_DATAKEY_ERROR|11011|The length of key must between 1 and 1024
INVALID_DATAVALUE_ERROR|11012|The length of value must between 0 and 256000
INVALID_DATAVERSION_ERROR|11013|The version must be equal or bigger than 0
INVALID_MASTERWEIGHT_ERROR|11015|MasterWeight must between 0 and max(uint32)
INVALID_SIGNER_ADDRESS_ERROR|11016|Invalid signer address
INVALID_SIGNER_WEIGHT_ERROR|11017|Signer weight must between 0 and max(uint32)
INVALID_TX_THRESHOLD_ERROR|11018|TxThreshold must between 0 and max(int64)
INVALID_OPERATION_TYPE_ERROR|11019|Operation type must between 1 and 100
INVALID_TYPE_THRESHOLD_ERROR|11020|TypeThreshold must between 0 and max(int64)
INVALID_ASSET_CODE_ERROR|11023|The length of key must between 1 and 64
INVALID_ASSET_AMOUNT_ERROR|11024|AssetMount must between 0 and max(int64)
INVALID_BU_AMOUNT_ERROR|11026|BuAmount must between 0 and max(int64)
INVALID_ISSUER_ADDRESS_ERROR|11027|Invalid issuer address
NO_SUCH_TOKEN_ERROR|11030|No such token
INVALID_TOKEN_NAME_ERROR|11031|The length of token name must between 1 and 1024
INVALID_TOKEN_SIMBOL_ERROR|11032|The length of symbol must between 1 and 1024
INVALID_TOKEN_DECIMALS_ERROR|11033|Decimals must less than 8
INVALID_TOKEN_TOTALSUPPLY_ERROR|11034|TotalSupply must between 1 and max(int64)
INVALID_TOKENOWNER_ERRPR|11035|Invalid token owner
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR|11038|contractAddress is not a contract account
INVALID_TOKEN_AMOUNT_ERROR|11039|Amount must between 1 and max(int64)
SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR|11040|SourceAddress cannot be equal to contractAddress
INVALID_FROMADDRESS_ERROR|11041|Invalid fromAddress
FROMADDRESS_EQUAL_DESTADDRESS_ERROR|11042|FromAddress cannot be equal to destAddress
INVALID_SPENDER_ERROR|11043|Invalid spender
INVALID_LOG_TOPIC_ERROR|11045|The length of log topic must between 1 and 128
INVALID_LOG_DATA_ERROR|11046|The length of one of log data must between 1 and 1024
INVALID_NONCE_ERROR|11048|Nonce must between 1 and max(int64)
INVALID_GASPRICE_ERROR|11049|Amount must between gasPrice in block and max(int64)
INVALID_FEELIMIT_ERROR|11050|FeeLimit must between 1 and max(int64)
INVALID_CEILLEDGERSEQ_ERROR|11052|CeilLedgerSeq must be equal or bigger than 0
OPERATIONS_ONE_ERROR|11053|One of operations error
INVALID_SIGNATURENUMBER_ERROR|11054|SignagureNumber must between 1 and max(int32)
INVALID_HASH_ERROR|11055|Invalid transaction hash
INVALID_BLOB_ERROR|11056|Invalid blob
PRIVATEKEY_NULL_ERROR|11057|PrivateKeys cannot be empty
PRIVATEKEY_ONE_ERROR|11058|One of privateKeys error
URL_EMPTY_ERROR|11062|Url cannot be empty
CONTRACTADDRESS_CODE_BOTH_NULL_ERROR|11063|ContractAddress and code cannot be empty at the same time
INVALID_OPTTYPE_ERROR|11064|OptType must between 0 and 2
GET_ALLOWANCE_ERROR|11065|Get allowance failed
GET_TOKEN_INFO_ERROR|11066|Get token info failed
CONNECTN_BLOCKCHAIN_ERROR|19999|Connect blockchain failed
SYSTEM_ERROR|20000|System error
