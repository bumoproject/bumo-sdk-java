English | [中文](SDK_CN.md) 

# Bumo Java SDK

## Overview
This document details the common interfaces of the Bumo Java SDK, making it easier for developers to operate and query the BU blockchain.

- [Terminology](#terminology)
- [Format of Request Parameters and Response Data](#format-of-request-parameters-and-response-data)
	- [Request parameters](#request-parameters)
	- [Response data](#response-data)
- [Usage](#usage)
    - [Generating SDK Instances](#generating-sdk-instances)
    - [Generating Public-Private Keys and Addresses](#generating-public-private-keys-and-addresses)
    - [Checking Validity](#checking-validity)
    - [Querying](#querying)
	- [Broadcasting Transactions](#broadcasting-transactions)
		- [Obtaining the Nonce Value of the Account Initiating the Transaction](#obtaining-the-nonce-value-of-the-account-initiating-the-transaction)
		- [Building Operations](#building-operations)
		- [Serializing Transactions](#serializing-transactions)
		- [Signing Transactions](#signing-transactions)
		- [commiting-transactions](#commiting-transactions)
- [Account Services](#account-services)
	- [checkValid](#checkvalid-account)
	- [getInfo](#getinfo-account)
	- [getNonce](#getnonce)
	- [getBalance](#getbalance-account)
	- [getAssets](#getassets)
	- [getMetadata](#getmetadata)
- [Asset Services](#asset-services)
    - [getInfo](#getinfo-asset)
- [Contract Services](#contract-services)
    - [checkValid](#checkvalid-contract)
	- [getInfo](#getinfo-contract)
    - [getAddress](#getaddress)
	- [call](#call)
- [Transaction Services](#transaction-services)
    - [Operation description](#operation-description)
	- [buildBlob](#buildblob)
	- [evaluateFee](#evaluatefee)
	- [sign](#sign)
	- [submit](#submit)
	- [getInfo](#getinfo-transaction)
- [Block Services](#block-services)
    - [getNumber](#getnumber)
	- [checkStatus](#checkstatus)
	- [getTransactions](#gettransactions)
	- [getInfo](#getinfo-block)
	- [getLatestInfo](#getlatestinfo)
	- [getValidators](#getvalidators)
	- [getLatestValidators](#getlatestvalidators)
	- [getReward](#getreward)
	- [getLatestReward](#getlatestreward)
	- [getFees](#getfees)
	- [getLatestFees](#getlatestfees)
- [Error Code](#error-code)

## Terminology

This section gives details about the terms used in this document.

Operate the BU Blockchain: Operate the BU Blockchain refers to writing data to or modifying data in the BU blockchain.

Commit Transactions: Commit Transactions refers to sending a request to write data to or modify data in the BU blockchain.

Query the BU Blockchain: Query the BU Blockchain refers to querying data in the BU blockchain.

Account Services: Account Services provide account validity checking and query interfaces.

Asset Services: Asset Services provide an asset-related query interface that follows the ATP 1.0 protocol.

Ctp10Token Services: Ctp10Token Services provide a validity check and query interfaces related to contract assets, which follows the CTP 1.0 protocol.

Contract Services: Contract Services provide a contract-related validity checking and query interfaces.

Transaction Services: Transaction Services provide a build transaction Blob interface, a signature interface, a query and a commit transaction interface.

Block Services: Block Services provide an interface to query the block.

Account Nonce Value: Account Nonce Value is used to identify the order in which the transaction is executed when the user submits the transaction.

## Format of Request Parameters and Response Data

This section details the format of the request parameters and response data.

### Request Parameters

The class name of the request parameter of the interface is composed of [Service Name][Method Name]Request. For example, the request parameter format of the getInfo interface in Account Services is AccountGetInfoRequest.

The member of the request parameter is the member of the input parameter of each interface. For example, if the input parameter of the getInfo interface in Account Services is address, the complete structure of the request parameters of the interface is as follows:

```
Class AccountGetInfoRequest {
	String address;
}
```

### Response Data

The class name of the response data of the interface is composed of [Service Name][Method Name]Response. For example, the response data format of the getNonce interface in Account Services is AccountGetNonceResponse.

The members of the response data include error codes, error descriptions, and return results. For example, the members of the response data of the getInfo interface in Assets Services are as follows:

```
Class AccountGetNonceResponse {
	Integer errorCode;
	String errorDesc;
	AccountGetNonceResult result;
}
```

Note：
1. errorCode: error code. 0 means no error, greater than 0 means there is an error
2. errorDesc: error description
3. result: result: returns the result. A structure whose class name is [Service Name][Method Name]Result, whose members are members of the return value of each interface. For example, the result class name of the getNonce interface in Account Services is AccountGetNonceResult, and the member has a nonce. The complete structure is as follows:

```
Class AccountGetNonceResult {
	Long nonce;
}
```

## Usage

This section describes the process of using the SDK. First you need to generate the SDK implementation and then call the interface of the corresponding service. Services include account services, asset services, Ctp1.0Token services, contract services, transaction services, and block services. Interfaces are classified into public-private key address interfaces, validity check interfaces, query interfaces, and broadcast transaction-related interfaces.

### Generating SDK Instances

The SDK instance is generated by calling the getInstance interface of the SDK. The specific call is as follows:

```
String url = "http://seed1.bumotest.io";
SDK sdk = SDK.getInstance(url);
```

### Generating Public-Private Keys and Addresses

The public-private key address interface is used to generate the public key, private key, and address for the account on the BU blockchain. This can be achieved by directly calling the Keypair.generator interface. The specific call is as follows:

```
Keypair keypair = Keypair.generator();
System.out.println(keypair.getPrivateKey());
System.out.println(keypair.getPublicKey());
System.out.println(keypair.getAddress());
```

### Checking Validity
The validity check interface is used to verify the validity of the information, and the information validity check can be achieved by directly invoking the corresponding interface. For example, to verify the validity of the account address, the specific call is as follows:

```
// Initialize request parameters
String address = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
AccountCheckValidRequest request = new AccountCheckValidRequest();
request.setAddress(address);

// Call the checkValid interface
AccountCheckValidResponse response = 
sdk.getAccountService().checkValid(request);
if(0 == response.getErrorCode()) {
	System.out.println(response.getResult().isValid());
} else {
	System.out.println("error: " + response.getErrorDesc());
}
```

### Querying
The query interface is used to query data on the BU blockchain, and data query can be implemented by directly invoking the corresponding interface. For example, to query the account information, the specific call is as follows:

```
// Initialize request parameters
String accountAddress = "buQemmMwmRQY1JkcU7w3nhruo%X5N3j6C29uo";
AccountGetInfoRequest request = new AccountGetInfoRequest();
request.setAddress(accountAddress);

// Call the getInfo interface
AccountGetInfoResponse response =  sdk.getAccountService().getInfo(request);
if (response.getErrorCode() == 0) {
	AccountGetInfoResult result = response.getResult();
	System.out.println(JSON.toJSONString(result,true));
}
else {
	System.out.println("error: " + response.getErrorDesc());
}
```

### Broadcasting Transactions
Broadcasting transactions refers to the initiation of a transaction by means of broadcasting. The broadcast transaction consists of the following steps：Obtaining the Nonce Value of the Account Initiating the Transaction -> Building Operations -> Serializing Transactions -> Signing Transactions -> Commiting Transactions.

#### Obtaining the Nonce Value of the Account Initiating the Transaction

The developer can maintain the nonce value of each account, and automatically increments by 1 for the nounce value after submitting a transaction, so that multiple transactions can be sent in a short time; otherwise, the nonce value of the account must be added 1 after the execution of the previous transaction is completed. The specific interface call is as follows:

```
// Initialize request parameters
String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
getNonceRequest.setAddress(senderAddress);

// Call the getNonce interface
AccountGetNonceResponse getNonceResponse =  sdk.getAccountService().getNonce(getNonceRequest);

// 赋值nonce
if (getNonceResponse.getErrorCode() == 0) {
   AccountGetNonceResult result = getNonceResponse.getResult();
   System.out.println("nonce: " + result.getNonce());
}
else {
    System.out.println("error" + getNonceResponse.getErrorDesc());
}
```

#### Building Operations

The operations refer to some of the actions that are done in the transaction to facilitate serialization of transactions and evaluation of fees. For example, to build an operation to send BU (BUSendOperation), the specific interface call is as follows:

```
String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
Long buAmount = ToBaseUnit.BU2MO("10.9");

BUSendOperation operation = new BUSendOperation();
operation.setSourceAddress(senderAddress);
operation.setDestAddress(destAddress);
operation.setAmount(buAmount);
```

#### Serializing Transactions

The transaction serialization interface is used to serialize transactions and generate transaction blob strings for network transmission. The nonce value and operation are obtained from the interface called, and the specific interface call is as follows:

```
// Initialize variables
String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
Long gasPrice = 1000L;
Long feeLimit = ToBaseUnit.BU2MO("0.01");

// Initialize request parameters
TransactionBuildBlobRequest buildBlobRequest = new TransactionBuildBlobRequest();
buildBlobRequest.setSourceAddress(senderAddress);
buildBlobRequest.setNonce(nonce + 1);
buildBlobRequest.setFeeLimit(feeLimit);
buildBlobRequest.setGasPrice(gasPrice);
buildBlobRequest.addOperation(operation);

// Call the buildBlob interface
TransactionBuildBlobResponse buildBlobResponse = sdk.getTransactionService().buildBlob(buildBlobRequest);
if (buildBlobResponse.getErrorCode() == 0) {
    TransactionBuildBlobResult result = buildBlobResponse.getResult();
    System.out.println("txHash: " + result.getHash() + ", blob: " + result.getTransactionBlob());
} else {
    System.out.println("error: " + buildBlobResponse.getErrorDesc());
}
```

#### Signing Transactions

The signature transaction interface is used by the transaction initiator to sign the transaction using the private key of the account. The transactionBlob is obtained from the interface called. The specific interface call is as follows:

```
// Initialize request parameters
String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
String []signerPrivateKeyArr = {senderPrivateKey};
TransactionSignRequest signRequest = new TransactionSignRequest();
signRequest.setBlob(transactionBlob);
for (int i = 0; i < signerPrivateKeyArr.length; i++) {
    signRequest.addPrivateKey(signerPrivateKeyArr[i]);
}

// Call the sign interface
TransactionSignResponse signResponse = sdk.getTransactionService().sign(signRequest);
if (signResponse.getErrorCode() == 0) {
    TransactionSignResult result = signResponse.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + signResponse.getErrorDesc());
}
```

#### Committing Transactions

The commit interface is used to send a transaction request to the BU blockchain, triggering the execution of the transaction. transactionBlob and signResult are obtained from the interfaces called. The specific interface call is as follows:

```
// Initialize request parameters
TransactionSubmitRequest submitRequest = new TransactionSubmitRequest();
submitRequest.setTransactionBlob(transactionBlob);
submitRequest.setSignatures(signResult.getSignatures());

// Call the submit interface
TransactionSubmitResponse response = sdk.getTransactionService().submit(submitRequest);
if (0 == response.getErrorCode()) {
    System.out.println("success，hash=" + response.getResult().getHash());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Account Services

Account Services provide account-related interfaces, which include six interfaces: checkValid, getInfo, getNonce, getBalance, getAssets and getMetadata.

### checkValid-Account

> Method Description

   The checkValid interface is used to check the validity of the account address on the blockchain.

> Method

AccounCheckValidResponse checkValid(AccountCheckValidRequest)

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
address     |   String     |  Required, the account address to be checked on the blockchain   

> Response data

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
isValid     |   String     |  Whether the response data is valid

> Error

   Exception       |     Error Code   |   Description   
-----------  | ----------- | -------- 
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR |   20000     |  System error 

> Example

```
// Initialize request parameters
String address = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
AccountCheckValidRequest request = new AccountCheckValidRequest();
request.setAddress(address);

// Call the checkValid interface
AccountCheckValidResponse response = sdk.getAccountService().checkValid(request);
if(0 == response.getErrorCode()) {
	System.out.println(response.getResult().isValid());
} else {
	System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-Account

> Method Description

   The getInfo interface is used to obtain the specified account information.

> Method

AccountGetInfoResponse GetInfo(AccountGetInfoRequest);

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
address     |   String     |  Required, the account address to be queried on the blockchain

> Response data

   Parameter    |     Type      |        Description      
--------- | ------------- | ---------------- 
address	  |    String     |    Account address       
balance	  |    Long       |    Account balance, unit is MO, 1 BU = 10^8 MO, the account balance must be > 0
nonce	  |    Long       |    Account transaction serial number must be greater than 0
priv	  | [Priv](#priv) |    Account privilege

#### Priv
   Member       |     Type     |        Description      
-----------  | ------------ | ---------------- 
masterWeight |	 Long	    |   Account weight, size limit[0, (Integer.MAX_VALUE * 2L + 1)]
signers	     |[Signer](#signer)[]|   Signer weight list
threshold	 |[Threshold](#Threshold)|	Threshold

#### Signer
   Member       |     Type     |        Description      
-----------  | ------------ | ---------------- 
address	     |   String	    |   The account address of the signer on the blockchain
weight	     |   Long	    |   Signer weight, size limit[0, (Integer.MAX_VALUE * 2L + 1)]

#### Threshold
   Member       |     Type     |        Description      
-----------  | ------------ | ---------------- 
txThreshold	 |    Long	    |   Transaction default threshold, size limit[0, Long.MAX_VALUE]
typeThresholds|[TypeThreshold](#typethreshold)[]|Thresholds for different types of transactions

#### TypeThreshold
   Member       |     Type     |        Description      
-----------  | ------------ | ---------------- 
type         |    Long	    |    The operation type must be greater than 0
threshold    |    Long      |    Threshold, size limit[0, Long.MAX_VALUE]

> Error Code

   Exception       |     Error Code   |   Description  
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR| 11007| Failed to connect to the network
SYSTEM_ERROR |   20000     |  System error 

> Example

```
// Initialize request parameters
String accountAddress = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
AccountGetInfoRequest request = new AccountGetInfoRequest();
request.setAddress(accountAddress);

// Call the getInfo interface
AccountGetInfoResponse response =  sdk.getAccountService().getInfo(request);
if (response.getErrorCode() == 0) {
    AccountGetInfoResult result = response.getResult();
    System.out.println("Account info: \n" + JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getNonce

> Method Description

   The getNonce interface is used to obtain the nonce value of the specified account.

> Method

AccountGetNonceResponse getNonce(AccountGetNonceRequest);

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
address     |   String     |  Required, the account address to be queried on the blockchain

> Response data

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
nonce       |   Long       |  Account transaction serial number

> Error Code

   Exception       |     Error Code   |   Description  
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR| 11007| Failed to connect to the network
SYSTEM_ERROR |   20000     |  System error 

> Example

```
// Initialize request parameters
String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
AccountGetNonceRequest request = new AccountGetNonceRequest();
request.setAddress(accountAddress);

// Call the getNonce interface
AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
if(0 == response.getErrorCode()){
    System.out.println("Account nonce:" + response.getResult().getNonce());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getBalance-Account

> Method Description

   The getBalance interface is used to obtain the BU balance of the specified account.

> Method

AccountGetBalanceResponse getBalance(AccountGetBalanceRequest);

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
address     |   String     |  Required, the account address to be queried on the blockchain

> Response data

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
balance     |   Long       |  BU balance, unit MO, 1 BU = 10^8 MO

> Error Code

   Exception       |     Error Code   |   Description  
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR| 11007| Failed to connect to the network
SYSTEM_ERROR |   20000     |  System error 

> Example

```
// Initialize request parameters
String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
AccountGetBalanceRequest request = new AccountGetBalanceRequest();
request.setAddress(accountAddress);

// Call the getBalance interface
AccountGetBalanceResponse response = sdk.getAccountService().getBalance(request);
if(0 == response.getErrorCode()){
    AccountGetBalanceResult result = response.getResult();
    System.out.println("BU balance：" + ToBaseUnit.MO2BU(result.getBalance().toString()) + " BU");
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getAssets

> Method Description

   The getAssets interface is used to get all the asset information of the specified account.

> Method

AccountGetAssets getAssets(AccountGetAssetsRequest);

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
address     |   String     |  Required, the account address to be queried

> Response data

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
asset	    | [AssetInfo](#AssetInfo)[] |Account asset

#### AssetInfo

   Member      |     Type     |        Description      
----------- | ------------ | ---------------- 
  key       | [Key](#Key)  | Unique identifier for asse
  assetAmount    | Long        | Amount of assets
 
 #### Key

   Member   |     Type    |     Description      
-------- | ----------- | -----------
code     |   String    |   Asset code
issuer   |   String    |   The account address for issuing assets

> Error Code

   Exception       |     Error Code   |   Description  
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR| 11006 | Invalid address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR| 11007| Failed to connect to the network
NO_ASSET_ERROR|11009|The account does not have the asset
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
AccountGetAssetsRequest request = new AccountGetAssetsRequest();
request.setAddress("buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw");

// Call the getAssets interface
AccountGetAssetsResponse response = sdk.getAccountService().getAssets(request);
if (response.getErrorCode() == 0) {
    AccountGetAssetsResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getMetadata

> Method Description

   The getMetadata interface is used to obtain the metadata information of the specified account.

> Method

AccountGetMetadataResponse getMetadata(AccountGetMetadataRequest);

> Request parameters

   Parameter   |   Type   |        Description      
-------- | -------- | ---------------- 
address  |  String  |  Required, the account address to be queried
key      |  String  |  Optional, metadata keyword, length limit [1, 1024]

> Response data

   Parameter      |     Type    |        Description      
----------- | ----------- | ---------------- 
metadata    |[MetadataInfo](#MetadataInfo)   |  Account

#### MetadataInfo
   Member      |     Type    |        Description      
----------- | ----------- | ---------------- 
key         |  String     |  	metadata keyword
value       |  String     |  metadata content
version     |  Long      |  metadata version
   

> Error Code

   Exception       |     Error Code   |   Description  
-----------  | ----------- | -------- 
INVALID_ADDRESS_ERROR | 11006 | Invalid address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR | 11007 | Failed to connect to the network
NO_METADATA_ERROR|11010|The account does not have the metadata
INVALID_DATAKEY_ERROR | 11011 | The length of key must be between 1 and 1024
SYSTEM_ERROR | 20000| System error


> Example

```
// Initialize request parameters
String accountAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
AccountGetMetadataRequest request = new AccountGetMetadataRequest();
request.setAddress(accountAddress);
request.setKey("20180704");

// Call the getMetadata interface
AccountGetMetadataResponse response =  sdk.getAccountService().getMetadata(request);
if (response.getErrorCode() == 0) {
    AccountGetMetadataResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Asset Services

Asset Services follow the ATP 1.0 protocol, and Account Services provide an asset-related interface. Currently there is one interface: getInfo.

### getInfo-Asset

> Method Description

   The getInfo interface is used to obtain the specified asset information of the specified account.

> Method

AssetGetInfoResponse getInfo(AssetGetInfoRequest);

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
address     |   String    |  Required, the account address to be queried
code        |   String    |  Required, asset code, length limit [1, 64]
issuer      |   String    |  Required, the account address for issuing assets

> Response data

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
asset	    | [AssetInfo](#AssetInfo)[] |Account asset

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_ADDRESS_ERROR|11006|Invalid address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
INVALID_ASSET_CODE_ERROR|11023|The length of asset code must be between 1 and 64
INVALID_ISSUER_ADDRESS_ERROR|11027|Invalid issuer address
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
AssetGetInfoRequest request = new AssetGetInfoRequest();
request.setAddress("buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw");
request.setIssuer("buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH");
request.setCode("HNC");

// Call the getInfo interface
AssetGetInfoResponse response = sdk.getAssetService().getInfo(request);
if (response.getErrorCode() == 0) {
    AssetGetInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Contract Services

Contract Services provide contract-related interfaces and currently have four interfaces: checkValid, getInfo, getAddress, and call.

### checkValid-Contract

> Method Description

   The checkValid interface is used to check the validity of the contract account.

> Method

ContractCheckValidResponse checkValid(ContractCheckValidRequest)

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
contractAddress     |   String     |  Required, contract account address to be tested

> Response data

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
isValid     |   Boolean     |  Whether the response data is valid 

> Error Code

   Exception       |     Error Code   |   Description  
-----------  | ----------- | -------- 
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR |   20000     |  System error 

> Example

```
// Initialize request parameters
ContractCheckValidRequest request = new ContractCheckValidRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// Call the getDecimals interface
ContractCheckValidResponse response = sdk.getContractService().checkValid(request);
if (response.getErrorCode() == 0) {
    ContractCheckValidResult result = response.getResult();
    System.out.println(result.getValid());
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-Contract

> Method Description

   The getInfo interface is used to query the contract code.

> Method

ContractGetInfoResponse getInfo (ContractGetInfoRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
contractAddress     |   String     |  Required, contract account address to be queried   |

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
contract|[ContractInfo](#contractinfo)|Contract info

#### ContractInfo

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
type|Integer|Contract type, default is 0
payload|String|Contract code

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR|11038|contractAddress is not a contract account
NO_SUCH_TOKEN_ERROR|11030|No such token
GET_TOKEN_INFO_ERROR|11066|Failed to get token info
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
ContractGetInfoRequest request = new ContractGetInfoRequest();
request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

// Call the getInfo interface
ContractGetInfoResponse response = sdk.getContractService().getInfo(request);
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getAddress

> Method Description

The getAddress interface is used to query the contract address.

> Method

ContractGetAddressResponse getInfo (ContractGetAddressRequest);

> Request parameters

Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
hash     |   String     |  Required, the hash used to create a contract transaction   |

> Response data

Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
contractAddressList|List<[ContractAddressInfo](#contractaddressinfo)>|Contract address list

#### ContractAddressInfo

Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
contractAddress|String|Contract address
operationIndex|Integer|The subscript of the operation

> Error Code

Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_HASH_ERROR|11055|Invalid transaction hash
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
ContractGetAddressRequest request = new ContractGetAddressRequest();
request.setHash("44246c5ba1b8b835a5cbc29bdc9454cdb9a9d049870e41227f2dcfbcf7a07689");

// Call the getAddress interface
ContractGetAddressResponse response = sdk.getContractService().getAddress(request);
if (response.getErrorCode() == 0) {
System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
System.out.println("error: " + response.getErrorDesc());
}
```

### call 

> Method Description

   The call interface is used to debug the contract code.

> Method

ContractCallesponse call(ContractCallRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
sourceAddress|String|Optional, the account address to trigger the contract
contractAddress|String|	Optional, the contract account address and code cannot be empty at the same time
code|String|Optional, the contract code and contractAddress cannot be empty at the same time, length limit [1, 64]
input|String|Optional, input parameter for the contract
contractBalance|String|Optional, the initial BU balance given to the contract, unit MO, 1 BU = 10^8 MO, size limit [1, Long.MAX_VALUE]
optType|Integer|Required, 0: Call the read/write interface of the contract init, 1: Call the read/write interface of the contract main, 2: Call the read-only interface query
feeLimit|Long|Minimum fee required for the transaction, size limit [1, Long.MAX_VALUE]
gasPrice|Long|Transaction fuel price, size limit [1000, Long.MAX_VALUE]


> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
logs|JSONObject|Log information
queryRets|JSONArray|Query the result set
stat|[ContractStat](#ContractStat)|Contract resource occupancy
txs|[TransactionEnvs](#TransactionEnvs)[]|Transaction set

#### ContractStat

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
  applyTime|Long|Receipt time
  memoryUsage|Long|Memory footprint
  stackUsage|Long|Stack occupancy
  step|Long|Steps needed

#### TransactionEnvs

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
  transactionEnv|[TransactionEnv](#transactionenv)|Transaction

#### TransactionEnv

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
transaction|[TransactionInfo](#transactioninfo)|Transaction content
trigger|[ContractTrigger](#contracttrigger)|Contract trigger

#### TransactionInfo

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
sourceAddress|String|The source account address initiating the transaction
feeLimit|Long|Minimum fees required for the transaction
gasPrice|Long|Transaction fuel price
nonce|Long|Transaction serial number
operations|[Operation](#operation)[]|Operation list

#### ContractTrigger
   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
transaction|[TriggerTransaction](#triggertransaction)|Trigger transactions

#### Operation

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
type|Integer|Operation type
sourceAddress|String|The source account address initiating operations
metadata|String|Note
createAccount|[OperationCreateAccount](#operationcreateaccount)|Operation of creating accounts
issueAsset|[OperationIssueAsset](#operationissueasset)|Operation of issuing assets
payAsset|[OperationPayAsset](#operationpayasset)|Operation of transferring assets
payCoin|[OperationPayCoin](#operationpaycoin)|Operation of sending BU
setMetadata|[OperationSetMetadata](#operationsetmetadata)|Operation of setting metadata
setPrivilege|[OperationSetPrivilege](#operationsetprivilege)|Operation of setting account privilege
log|[OperationLog](#operationlog)|Record logs

#### TriggerTransaction

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
hash|String|交易hash

#### OperationCreateAccount

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
destAddress|String|Target account address
contract|[Contract](#contract)|Contract info
priv|[Priv](#priv)|Account privilege
metadata|[MetadataInfo](#metadatainfo)[]|Account metadata
initBalance|Long|Account assets, unit MO, 1 BU = 10^8 MO
initInput|String|The input parameter for the init function of the contract

#### Contract

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
type|Integer|The contract language is not assigned value by default
payload|String|The contract code for the corresponding language

#### MetadataInfo

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
key|String|metadata key
value|String|metadata value
version|Long|metadata version

#### OperationIssueAsset

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
code|String|Asset code
assetAmount|Long|Asset amount

#### OperationPayAsset

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
destAddress|String|The target account address to which the asset is transferred
asset|[AssetInfo](#assetinfo)|Account asset
input|String|Input parameters for the main function of the contract

#### OperationPayCoin

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
destAddress|String|The target account address to which the asset is transferred
buAmount|Long|BU amount to be transferred
input|String|Input parameters for the main function of the contract

#### OperationSetMetadata

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
key|String|metadata key
value|String|metadata value
version|Long|metadata version
deleteFlag|boolean|Whether to delete metadata

#### OperationSetPrivilege

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
masterWeight|String|Account weight, size limit[0, (Integer.MAX_VALUE * 2L + 1)]
signers|[Signer](#signer)[]|Signer weight list
txThreshold|String|Transaction threshold, size limit[0, Long.MAX_VALUE]
typeThreshold|[TypeThreshold](#typethreshold)|Threshold for specified transaction type

#### OperationLog

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
topic|String|Log theme
data|String[]|Log content

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_CODE_BOTH_NULL_ERROR|11063|ContractAddress and code cannot be empty at the same time
INVALID_OPTTYPE_ERROR|11064|OptType must be between 0 and 2
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
ContractCallRequest request = new ContractCallRequest();
request.setCode("\"use strict\";log(undefined);function query() { getBalance(thisAddress); }");
request.setFeeLimit(1000000000L);
request.setOptType(2);

// Call the call interface
ContractCallResponse response = sdk.getContractService().call(request);
if (response.getErrorCode() == 0) {
    ContractCallResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Transaction Services

Transaction Services provide transaction-related interfaces and currently have five interfaces: buildBlob, evaluateFee, sign, submit, and getInfo.

Description: Before you can call buildBlob, you need to build some operations. There are 16 operations: AccountActivateOperation, AccountSetMetadataOperation, AccountSetPrivilegeOperation, AssetIssueOperation, AssetSendOperation, BUSendOperation, TokenIssueOperation, TokenTransferOperation, TokenTransferFromOperation, TokenApproveOperation, TokenAssignOperation, TokenChangeOwnerOperation, ContractCreateOperation, ContractInvokeByAssetOperation, ContractInvokeByBUOperation, and LogCreateOperation.

### Operation description

> BaseOperation

   Member    |     Type  |        Description                          
------------- | -------- | ----------------------------------   
sourceAddress |   String |  Optional, source account address of the operation
metadata      |   String |  Optional, note

>  AccountActivateOperation

AccountActivateOperation inherits from BaseOperation, and feeLimit is currently fixed at 0.01 BU (2018.07.26).

   Member    |     Type  |        Description                          
------------- | -------- | ---------------------------------- 
sourceAddress |   String |  Optional, source account address of the operation
destAddress   |   String |  Required, target account address            
initBalance   |   Long   |  Required, initialize the asset, unit MO, 1 BU = 10^8 MO, size (0, Long.MAX_VALUE]
metadata|String|Optional, note

> AccountSetMetadataOperation

AccountSetMetadataOperation is inherited from BaseOperation, and feeLimit is currently fixed at 0.01 BU (2018.07.26).

   Member    |     Type   |        Description                        
------------- | --------- | ------------------------------- 
sourceAddress |   String |  Optional, source account address of the operation
key           |   String  |  Required, metadata keyword, length limit [1, 1024]
value         |   String  |  Required, metadata content, length limit [0, 256000]
version       |   Long    |  Optional, metadata version
deleteFlag    |   Boolean |  Optional, whether to delete metadata
metadata|String|Optional, note

> AccountSetPrivilegeOperation

AccountSetPrivilegeOperation inherits from BaseOperation, and feeLimit is currently fixed at 0.01 BU (2018.07.26).

   Member    |     Type   |        Description              
------------- | --------- | --------------------------
sourceAddress |   String |  Optional, source account address of the operation
masterWeight|String|Optional, account weight, size limit [0, (Integer.MAX_VALUE * 2L + 1)]
signers|[Signer](#signer)[]|Optional, signer weight list
txThreshold|String|Optional, transaction threshold, size limit [0, Long.MAX_VALUE]
typeThreshold|[TypeThreshold](#typethreshold)[]|Optional, specify transaction threshold
metadata|String|Optional, note

> AssetIssueOperation

AssetIssueOperation inherits from BaseOperation, and feeLimit is currently fixed at 50.01 BU (2018.07.26).

   Member    |     Type   |        Description            
------------- | --------- | ------------------------
sourceAddress|String|Optional, source account address of the operation
code|String|Required, asset code, length limit [1, 64]
assetAmount|Long|Required, number of asset issues, size limit [0, Long.MAX_VALUE]
metadata|String|Optional, note

> AssetSendOperation

AssetSendOperation inherits from BaseOperation, and feeLimit is currently fixed at 0.01 BU (2018.07.26).
If the target account is not activated, the activation account operation must be activated first.

   Member    |     Type   |        Description           
------------- | --------- | ----------------------
sourceAddress|String|Optional, source account address of the operation
destAddress|String|Required, target account address
code|String|Required, asset code, length limit [1, 64]
issuer|String|Required, account address issuing assets
assetAmount|Long|Required, asset quantity, size limit [0, Long.MAX_VALUE]
metadata|String|Optional, note

> BUSendOperation

BUSendOperation inherits from BaseOperation, feeLimit is currently (2018.07.26) fixed at 0.01 BU.
If the target account is not activated, the operation can also activate the target account.

   Member    |     Type   |        Description         
------------- | --------- | ---------------------
sourceAddress|String|Optional, source account address of the operation
destAddress|String|Required, target account address
buAmount|Long|Required, amount of asset issued, size limit [0, Long.MAX_VALUE]
metadata|String|Optional, note

> ContractCreateOperation

ContractCreateOperation inherits from BaseOperation, and feeLimit is currently fixed at 10.01 BU (2018.07.26).

   Member    |     Type   |        Description         
------------- | --------- | ---------------------
sourceAddress|String|Optional, source account address of the operation
initBalance|Long|Required, initial asset for contract account, unit MO, 1 BU = 10^8 MO, size limit [1, Long.MAX_VALUE]
type|Integer|Optional, the language of the contract, the default is 0
payload|String|Required, contract code for the corresponding language
initInput|String|Optional, the input parameters of the init method in the contract code
metadata|String|Optional, note

> ContractInvokeByAssetOperation

ContractInvokeByAssetOperation inherits from BaseOperation. FeeLimit requires to add fees according to the execution of the transaction in the contract. First, the transaction fee is initiated. At present the fee (2018.07.26) is 0.01BU, and then the transaction in the contract also requires the transaction initiator to add the transaction fees.
If the contract account does not exist, the contract account must be created first.

   Member    |     Type   |        Description         
------------- | --------- | ---------------------
sourceAddress|String|Optional, source account address of the operation
contractAddress|String|Required, contract account address
code|String|Optional, asset code, length limit [0, 1024]; when it is empty, only the contract is triggered
issuer|String|Optional, the account address issuing assets; when it is null, only trigger the contract
assetAmount|Long|Optional, asset quantity, size limit [0, Long.MAX_VALUE], when it is 0, only trigger the contract
input|String|Optional, the input parameter of the main() method for the contract to be triggered
metadata|String|Optional, note

> ContractInvokeByBUOperation

ContractInvokeByBUOperation inherits from BaseOperation. FeeLimit requires to add fees according to the execution of the transaction in the contract. First, the transaction fee is initiated. At present the fee (2018.07.26) is 0.01BU, and then the transaction in the contract also requires the transaction initiator to add the transaction fees.

   Member    |     Type   |        Description         
------------- | --------- | ---------------------
sourceAddress|String|Optional, source account address of the operation
contractAddress|String|Required, contract account address
buAmount|Long|Optional, number of asset issues, size limit [0, Long.MAX_VALUE], when it is 0 only triggers the contract
input|String|Optional, the input parameter of the main() method for the contract to be triggered
metadata|String|Optional, note

> LogCreateOperation

LogCreateOperation inherits from BaseOperation, feeLimit is currently (2018.07.26) fixed at 0.01 BU.

   Member    |     Type   |        Description         
------------- | --------- | ---------------------
sourceAddress|String|Optional, source account address of the operation
topic|String|Required, Log theme，length limit [1, 128]
datas|List<String>|Required, Log content，length limit of each string [1, 1024]
metadata|String|Optional, notes

### buildBlob

> Method Description

The buildBlob interface is used to serialize transactions and generate transaction blob strings for network transmission.

> Method

TransactionBuildBlobResponse buildBlob(TransactionBuildBlobRequest);

> Request parameters

   Parameter      |     Type     |        Description      
----------- | ------------ | ---------------- 
sourceAddress|String|Required, the source account address initiating the operation
nonce|Long|Required, the transaction serial number to be initiated, add 1 in the function, size limit [1, Long.MAX_VALUE]
gasPrice|Long|Required, transaction gas price, unit MO, 1 BU = 10^8 MO, size limit [1000, Long.MAX_VALUE]
feeLimit|Long|Required, the minimum fees required for the transaction, unit MO, 1 BU = 10^8 MO, size limit [1, Long.MAX_VALUE]
operation|BaseOperation[]|Required, list of operations to be committed which cannot be empty
ceilLedgerSeq|long|Optional, set a value which will be combined with the current block height to restrict transactions. If transactions do not complete within the set value plus the current block height, the transactions fail. The value you set must be greater than 0. If the value is set to 0, no limit is set.
metadata|String|Optional, note

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
transactionBlob|String|Serialized transaction hex string
hash|String|Transaction hash

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_NONCE_ERROR|11048|Nonce must be between 1 and Long.MAX_VALUE
INVALID_DESTADDRESS_ERROR|11003|Invalid destAddress
INVALID_INITBALANCE_ERROR|11004|InitBalance must be between 1 and Long.MAX_VALUE 
SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR|11005|SourceAddress cannot be equal to destAddress
INVALID_ISSUE_AMMOUNT_ERROR|11008|AssetAmount this will be issued must be between 1 and Long.MAX_VALUE
INVALID_DATAKEY_ERROR|11011|The length of key must be between 1 and 1024
INVALID_DATAVALUE_ERROR|11012|The length of value must be between 0 and 256000
INVALID_DATAVERSION_ERROR|11013|The version must be equal to or greater than 0 
INVALID_MASTERWEIGHT _ERROR|11015|MasterWeight must be between 0 and (Integer.MAX_VALUE * 2L + 1)
INVALID_SIGNER_ADDRESS_ERROR|11016|Invalid signer address
INVALID_SIGNER_WEIGHT _ERROR|11017|Signer weight must be between 0 and (Integer.MAX_VALUE * 2L + 1)
INVALID_TX_THRESHOLD_ERROR|11018|TxThreshold must be between 0 and Long.MAX_VALUE
INVALID_OPERATION_TYPE_ERROR|11019|Operation type must be between 1 and 100
INVALID_TYPE_THRESHOLD_ERROR|11020|TypeThreshold must be between 0 and Long.MAX_VALUE
INVALID_ASSET_CODE _ERROR|11023|The length of key must be between 1 and 64
INVALID_ASSET_AMOUNT_ERROR|11024|AssetAmount must be between 0 and Long.MAX_VALUE
INVALID_BU_AMOUNT_ERROR|11026|BuAmount must be between 0 and Long.MAX_VALUE
INVALID_ISSUER_ADDRESS_ERROR|11027|Invalid issuer address
NO_SUCH_TOKEN_ERROR|11030|No such token
INVALID_TOKEN_NAME_ERROR|11031|The length of token name must be between 1 and 1024
INVALID_TOKEN_SYMBOL_ERROR|11032|The length of symbol must be between 1 and 1024
INVALID_TOKEN_DECIMALS_ERROR|11033|Decimals must be between 0 and 8
INVALID_TOKEN_TOTALSUPPLY_ERROR|11034|TotalSupply must be between 1 and Long.MAX_VALUE
INVALID_TOKENOWNER_ERRPR|11035|Invalid token owner
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR|11038|ContractAddress is not a contract account
INVALID_TOKEN_AMOUNT_ERROR|11039|Token amount must be between 1 and Long.MAX_VALUE
SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR|11040|SourceAddress cannot be equal to contractAddress
INVALID_FROMADDRESS_ERROR|11041|Invalid fromAddress
FROMADDRESS_EQUAL_DESTADDRESS_ERROR|11042|FromAddress cannot be equal to destAddress
INVALID_SPENDER_ERROR|11043|Invalid spender
PAYLOAD_EMPTY_ERROR|11044|Payload cannot be empty
INVALID_LOG_TOPIC_ERROR|11045|The length of a log topic must be between 1 and 128
INVALID_LOG_DATA_ERROR|11046|The length of one piece of log data must be between 1 and1024
INVALID_CONTRACT_TYPE_ERROR|11047|Type must be equal or bigger than 0 
INVALID_NONCE_ERROR|11048|Nonce must be between 1 and Long.MAX_VALUE
INVALID_ GASPRICE_ERROR|11049|GasPrice must be between 1000 and Long.MAX_VALUE
INVALID_FEELIMIT_ERROR|11050|FeeLimit must be between 1 and Long.MAX_VALUE
OPERATIONS_EMPTY_ERROR|11051|Operations cannot be empty
INVALID_CEILLEDGERSEQ_ERROR|11052|CeilLedgerSeq must be equal to or greater than 0
OPERATIONS_ONE_ERROR|11053|One of the operations cannot be resolved
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize variables
String senderAddresss = "buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea";
String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
Long buAmount = ToBaseUnit.BU2MO("10.9");
Long gasPrice = 1000L;
Long feeLimit = ToBaseUnit.BU2MO("0.01");
Long nonce = 1L;

// Build the sendBU operation
BUSendOperation operation = new BUSendOperation();
operation.setSourceAddress(senderAddresss);
operation.setDestAddress(destAddress);
operation.setAmount(buAmount);

// Initialize request parameters
TransactionBuildBlobRequest request = new TransactionBuildBlobRequest();
request.setSourceAddress(senderAddresss);
request.setNonce(nonce);
request.setFeeLimit(feeLimit);
request.setGasPrice(gasPrice);
request.addOperation(operation);

// Call the buildBlob interface
String transactionBlob = null;
TransactionBuildBlobResponse response = sdk.getTransactionService().buildBlob(request);
if (response.getErrorCode() == 0) {
    TransactionBuildBlobResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### evaluateFee

> Method Description

The evaluateFee interface implements the cost estimate for the transaction.

> Method

TransactionEvaluateFeeResponse evaluateFee (TransactionEvaluateFeeRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
sourceAddress|String|Required, the source account address issuing the operation
nonce|Long|Required, transaction serial number to be initiated, size limit [1, Long.MAX_VALUE]
operation|BaseOperation[]|Required, list of operations to be committed which cannot be empty
signtureNumber|Integer|Optional, the number of people to sign, the default is 1, size limit [1, Integer.MAX_VALUE]
ceilLedgerSeq|Long|Optional, set a value which will be combined with the current block height to restrict transactions. If transactions do not complete within the set value plus the current block height, the transactions fail. The value you set must be greater than 0. If the value is set to 0, no limit is set.
metadata|String|Optional, note

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
txs     |   [TestTx](#testtx)[]     |  Evaluation transaction set   |

#### TestTx

   Member      |     Type     |        Description      |
----------- | ------------ | ---------------- |
transactionEnv| [TestTransactionFees](#testtransactionfees)| Assess transaction costs

#### TestTransactionFees

   Member      |     Type     |      Description     |
----------- | ------------ | ---------------- |
transactionFees|[TransactionFees](#transactionfees)|Transaction fees

#### TransactionFees
   Member      |     Type     |        Description   |
----------- | ------------ | ---------------- |
feeLimit|Long|Minimum fees required for the transaction
gasPrice|Long|Transaction gas price

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_NONCE_ERROR|11045|Nonce must be between 1 and Long.MAX_VALUE
OPERATIONS_EMPTY_ERROR|11051|Operations cannot be empty
OPERATIONS_ONE_ERROR|11053|One of the operations cannot be resolved
INVALID_SIGNATURENUMBER_ERROR|11054|SignagureNumber must be between 1 and Integer.MAX_VALUE
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize variables
String senderAddresss = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
String destAddress = "buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea";
Long buAmount = ToBaseUnit.BU2MO("10.9");
Long gasPrice = 1000L;
Long feeLimit = ToBaseUnit.BU2MO("0.01");
Long nonce = 51L;

// Build the sendBU operation
BUSendOperation buSendOperation = new BUSendOperation();
buSendOperation.setSourceAddress(senderAddresss);
buSendOperation.setDestAddress(destAddress);
buSendOperation.setAmount(buAmount);

// Initialize request parameters for transaction evaluation 
TransactionEvaluateFeeRequest request = new TransactionEvaluateFeeRequest();
request.addOperation(buSendOperation);
request.setSourceAddress(senderAddresss);
request.setNonce(nonce);
request.setSignatureNumber(1);
request.setMetadata(HexFormat.byteToHex("evaluate fees".getBytes()));

// Call the evaluateFee interface
TransactionEvaluateFeeResponse response = sdk.getTransactionService().evaluateFee(request);
if (response.getErrorCode() == 0) {
    TransactionEvaluateFeeResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### sign

> Method Description

   The sign interface is used to implement the signature of the transaction.

> Method

TransactionSignResponse sign(TransactionSignRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blob|String|Required, pending transaction blob to be signed
privateKeys|String[]|Required, private key list


> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
signatures|[Signature](#signature)|Signed data list

#### Signature

   Member变量      |     Type     |        Description      |
----------- | ------------ | ---------------- |
  signData|Long|Signed data
  publicKey|Long|Public key

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOB_ERROR|11056|Invalid blob
PRIVATEKEY_NULL_ERROR|11057|PrivateKeys cannot be empty
PRIVATEKEY_ONE_ERROR|11058|One of privateKeys is invalid
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
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

> Method Description

   The submit interface is used to implement the submission of the transaction.

> Method

TransactionSubmitResponse submit(TransactionSubmitRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
  blob|String|Required, transaction blob
  signature|[Signature](#signature)[]|Required, signature list

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
hash|String|Transaction hash

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOB_ERROR|11056|Invalid blob
SIGNATURE_EMPTY_ERROR|11067|The signatures cannot be empty
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
Signature signature = new Signature();
signature.setSignData("D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A");
signature.setPublicKey("b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477");
TransactionSubmitRequest request = new TransactionSubmitRequest();
request.setTransactionBlob(transactionBlob);
request.addSignature(signature);

// Call the submit interface
TransactionSubmitResponse response = sdk.getTransactionService().submit(request);
if (0 == response.getErrorCode()) { // 交易提交成功
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else{
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-Transaction

> Method Description

   The getInfo interface is used to implement query transactions based on transaction hashes.

> Method

TransactionGetInfoResponse getInfo (TransactionGetInfoRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
hash|String|Transaction hash

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
totalCount|Long|Total number of transactions returned
transactions|[TransactionHistory](#transactionhistory)[]|Transaction content

#### TransactionHistory

   Member变量  |     Type     |        Description      |
----------- | ------------ | ---------------- |
actualFee|String|Actual transaction cost
closeTime|Long|Transaction closure time
errorCode|Long|Transaction error code
errorDesc|String|Transaction description
hash|String|Transaction hash
ledgerSeq|Long|Block serial number
transaction|[TransactionInfo](#transactioninfo)|List of transaction contents
signatures|[Signature](#signature)[]|Signature list
txSize|Long|Transaction size

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_HASH_ERROR|11055|Invalid transaction hash
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
String txHash = "1653f54fbba1134f7e35acee49592a7c29384da10f2f629c9a214f6e54747705";
TransactionGetInfoRequest request = new TransactionGetInfoRequest();
request.setHash(txHash);

// Call the getInfo interface
TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Block Services

Block services provide block-related interfaces. There are currently 11 interfaces: getNumber, checkStatus, getTransactions, getInfo, getLatestInfo, getValidators, getLatestValidators, getReward, getLatestReward, getFees and getLatestFees.

### getNumber

> Method Description

   The getNumber interface is used to query the latest block height.

> Method

BlockGetNumberResponse getNumber();

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
header|BlockHeader|Block head
blockNumber|Long|The latest block height,corresponding to the underlying field sequence

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Call the getNumber interface
BlockGetNumberResponse response = sdk.getBlockService().getNumber();
if(0 == response.getErrorCode()){
	System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
	System.out.println("error: " + response.getErrorDesc());
}
```

### checkStatus

> Method Description

   The checkStatus interface is used to check if the local node block is synchronized.

> Method

BlockCheckStatusResponse checkStatus();

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
isSynchronous    |   Boolean     |  Whether the block is synchronized   |

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Call the checkStatus interface
BlockCheckStatusResponse response = sdk.getBlockService().checkStatus();
if(0 == response.getErrorCode()){
	System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
	System.out.println("error: " + response.getErrorDesc());
}
```

### getTransactions

> Method Description

   The getTransactions interface is used to query all transactions at the specified block height.

> Method

   BlockGetTransactionsResponse getTransactions(BlockGetTransactionsRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockNumber|Long|Required, the height of the block to be queried must be greater than 0

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
totalCount|Long|Total number of transactions returned
transactions|[TransactionHistory](#transactionhistory)[]|Transaction content

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
Long blockNumber = 617247L;// 第617247区块
BlockGetTransactionsRequest request = new BlockGetTransactionsRequest();
request.setBlockNumber(blockNumber);

// Call the getTransactions interface
BlockGetTransactionsResponse response = sdk.getBlockService().getTransactions(request);
if(0 == response.getErrorCode()){
    System.out.println(JSON.toJSONString(response.getResult(), true));
}else{
    System.out.println("error: " + response.getErrorDesc());
}
```

### getInfo-Block

> Method Description

   The getInfo interface is used to obtain block information.

> Method

BlockGetInfoResponse getInfo(BlockGetInfoRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockNumber|Long|Required, the height of the block to be queried

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
closeTime|Long|Block closure time
number|Long|Block height
txCount|Long|Total transactions amount
version|String|Block version

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
BlockGetInfoRequest request = new BlockGetInfoRequest();
request.setBlockNumber(629743L);

// Call the getInfo interface
BlockGetInfoResponse response = sdk.getBlockService().getInfo(request);
if (response.getErrorCode() == 0) {
    BlockGetInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestInfo

> Method Description

   The getLatestInfo interface is used to get the latest block information.

> Method

BlockGetLatestInfoResponse getLatestInfo();

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
closeTime|Long|Block closure time
number|Long|Block height,corresponding to the underlying field seq
txCount|Long|Total transactions amount
version|String|Block version


> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Call the getLatestInfo interface
BlockGetLatestInfoResponse response = sdk.getBlockService().getLatestInfo();
if (response.getErrorCode() == 0) {
    BlockGetLatestInfoResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getValidators

> Method Description

   The getValidators interface is used to get the number of all the authentication nodes in the specified block.

> Method

BlockGetValidatorsResponse getValidators(BlockGetValidatorsRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockNumber|Long|Required, the height of the block to be queried must be greater than 0

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
validators|[ValidatorInfo](#validatorinfo)[]|Validators list

#### ValidatorInfo

   Member变量  |     Type     |        Description      |
----------- | ------------ | ---------------- |
address|String|Consensus node address
plegeCoinAmount|Long|Validators' deposit

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
BlockGetValidatorsRequest request = new BlockGetValidatorsRequest();
request.setBlockNumber(629743L);

// Call the getValidators interface
BlockGetValidatorsResponse response = sdk.getBlockService().getValidators(request);
if (response.getErrorCode() == 0) {
    BlockGetValidatorsResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestValidators

> Method Description

   The getLatestValidators interface is used to get the number of all validators in the latest block.

> Method

BlockGetLatestValidatorsResponse getLatestValidators();

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
validators|[ValidatorInfo](#validatorinfo)[]|	Validators list

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Call the getLatestValidators interface
BlockGetLatestValidatorsResponse response = sdk.getBlockService().getLatestValidators();
if (response.getErrorCode() == 0) {
    BlockGetLatestValidatorsResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getReward

> Method Description

   The getReward interface is used to retrieve the block reward and valicator node rewards in the specified block.

> Method

BlockGetRewardResponse getReward(BlockGetRewardRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockNumber|Long|Required, the height of the block to be queried must be greater than 0

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockReward|Long|Block rewards
validatorsReward|[ValidatorReward](#validatorreward)[]|Validators rewards

#### ValidatorReward

   Member变量  |     Type     |        Description      |
----------- | ------------ | ---------------- |
  validator|String|Validator address
  reward|Long|Validator reward


> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
BlockGetRewardRequest request = new BlockGetRewardRequest();
request.setBlockNumber(629743L);

// Call the getReward interface
BlockGetRewardResponse response = sdk.getBlockService().getReward(request);
if (response.getErrorCode() == 0) {
    BlockGetRewardResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestReward

> Method Description

   The getLatestReward interface gets the block rewards and validator rewards in the latest block.

> Method

BlockGetLatestRewardResponse getLatestReward();

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockReward|Long|Block rewards
validatorsReward|[ValidatorReward](#validatorreward)[]|Validator rewards

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Call the getLatestReward interface
BlockGetLatestRewardResponse response = sdk.getBlockService().getLatestReward();
if (response.getErrorCode() == 0) {
    BlockGetLatestRewardResult result = response.getResult();
    System.out.println(JSON.toJSONString(result, true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getFees

> Method Description

   The getFees interface gets the minimum asset limit and fuel price of the account in the specified block.

> Method

BlockGetFeesResponse getFees(BlockGetFeesRequest);

> Request parameters

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
blockNumber|Long|Required, the height of the block to be queried must be greater than 0

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
fees|[Fees](#fees)|Fees

#### Fees

   Member变量  |     Type     |        Description      |
----------- | ------------ | ---------------- |
baseReserve|Long|Minimum asset limit for the account
gasPrice|Long|Transaction fuel price, unit MO, 1 BU = 10^8 MO

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must bigger than 0
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Initialize request parameters
BlockGetFeesRequest request = new BlockGetFeesRequest();
request.setBlockNumber(629743L);

// Call the getFees interface
BlockGetFeesResponse response = sdk.getBlockService().getFees(request);
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

### getLatestFees

> Method Description

   The getLatestFees interface is used to obtain the minimum asset limit and fuel price of the account in the latest block.

> Method

BlockGetLatestFeesResponse getLatestFees();

> Response data

   Parameter      |     Type     |        Description      |
----------- | ------------ | ---------------- |
fees|[Fees](#fees)|Fees

> Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
SYSTEM_ERROR|20000|System error

> Example

```
// Call the getLatestFees interface
BlockGetLatestFeesResponse response = sdk.getBlockService().getLatestFees();
if (response.getErrorCode() == 0) {
    System.out.println(JSON.toJSONString(response.getResult(), true));
} else {
    System.out.println("error: " + response.getErrorDesc());
}
```

## Error Code

   Exception       |     Error Code   |   Description  |
-----------  | ----------- | -------- |
ACCOUNT_CREATE_ERROR|11001|Failed to create the account 
INVALID_SOURCEADDRESS_ERROR|11002|Invalid sourceAddress
INVALID_DESTADDRESS_ERROR|11003|Invalid destAddress
INVALID_INITBALANCE_ERROR|11004|InitBalance must be between 1 and Long.MAX_VALUE 
SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR|11005|SourceAddress cannot be equal to destAddress
INVALID_ADDRESS_ERROR|11006|Invalid address
CONNECTNETWORK_ERROR|11007|Failed to connect to the network
INVALID_ISSUE_AMOUNT_ERROR|11008|Amount of the token to be issued must be between 1 and Long.MAX_VALUE
NO_ASSET_ERROR|11009|The account does not have the asset
NO_METADATA_ERROR|11010|The account does not have the metadata
INVALID_DATAKEY_ERROR|11011|The length of key must be between 1 and 1024
INVALID_DATAVALUE_ERROR|11012|The length of value must be between 0 and 256000
INVALID_DATAVERSION_ERROR|11013|The version must be equal to or greater than 0 
INVALID_MASTERWEIGHT_ERROR|11015|MasterWeight must be between 0 and (Integer.MAX_VALUE * 2L + 1)
INVALID_SIGNER_ADDRESS_ERROR|11016|Invalid signer address
INVALID_SIGNER_WEIGHT_ERROR|11017|Signer weight must be between 0 and (Integer.MAX_VALUE * 2L + 1)
INVALID_TX_THRESHOLD_ERROR|11018|TxThreshold must be between 0 and Long.MAX_VALUE
INVALID_OPERATION_TYPE_ERROR|11019|Operation type must be between 1 and 100
INVALID_TYPE_THRESHOLD_ERROR|11020|TypeThreshold must be between 0 and Long.MAX_VALUE
INVALID_ASSET_CODE_ERROR|11023|The length of key must be between 1 and 64
INVALID_ASSET_AMOUNT_ERROR|11024|AssetAmount must be between 0 and Long.MAX_VALUE
INVALID_BU_AMOUNT_ERROR|11026|BuAmount must be between 0 and Long.MAX_VALUE
INVALID_ISSUER_ADDRESS_ERROR|11027|Invalid issuer address
NO_SUCH_TOKEN_ERROR|11030|No such token
INVALID_TOKEN_NAME_ERROR|11031|The length of token name must be between 1 and 1024
INVALID_TOKEN_SIMBOL_ERROR|11032|The length of symbol must be between 1 and 1024
INVALID_TOKEN_DECIMALS_ERROR|11033|Decimals must be between 0 and 8
INVALID_TOKEN_TOTALSUPPLY_ERROR|11034|TotalSupply must be between 1 and Long.MAX_VALUE
INVALID_TOKENOWNER_ERRPR|11035|Invalid token owner
INVALID_CONTRACTADDRESS_ERROR|11037|Invalid contract address
CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR|11038|contractAddress is not a contract account
INVALID_TOKEN_AMOUNT_ERROR|11039|TokenAmount must be between 1 and Long.MAX_VALUE
SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR|11040|SourceAddress cannot be equal to contractAddress
INVALID_FROMADDRESS_ERROR|11041|Invalid fromAddress
FROMADDRESS_EQUAL_DESTADDRESS_ERROR|11042|FromAddress cannot be equal to destAddress
INVALID_SPENDER_ERROR|11043|Invalid spender
PAYLOAD_EMPTY_ERROR|11044|Payload cannot be empty
INVALID_LOG_TOPIC_ERROR|11045|The length of a log topic must be between 1 and 128
INVALID_LOG_DATA_ERROR|11046|The length of one piece of log data must be between 1 and1024
INVALID_CONTRACT_TYPE_ERROR|11047|Invalid contract type
INVALID_NONCE_ERROR|11048|Nonce must be between 1 and Long.MAX_VALUE
INVALID_GASPRICE_ERROR|11049|GasPrice must be between 1000 and Long.MAX_VALUE
INVALID_FEELIMIT_ERROR|11050|FeeLimit must be between 1 and Long.MAX_VALUE
OPERATIONS_EMPTY_ERROR|11051|Operations cannot be empty
INVALID_CEILLEDGERSEQ_ERROR|11052|CeilLedgerSeq must be equal to or greater than 0
OPERATIONS_ONE_ERROR|11053|One of the operations cannot be resolved
INVALID_SIGNATURENUMBER_ERROR|11054|SignagureNumber must be between 1 and Integer.MAX_VALUE
INVALID_HASH_ERROR|11055|Invalid transaction hash
INVALID_BLOB_ERROR|11056|Invalid blob
PRIVATEKEY_NULL_ERROR|11057|PrivateKeys cannot be empty
PRIVATEKEY_ONE_ERROR|11058|One of privateKeys is invalid
SIGNDATA_NULL_ERROR|11059|SignData cannot be empty
INVALID_BLOCKNUMBER_ERROR|11060|BlockNumber must be bigger than 0
PUBLICKEY_NULL_ERROR|11061|PublicKey cannot be empty
URL_EMPTY_ERROR|11062|Url cannot be empty
CONTRACTADDRESS_CODE_BOTH_NULL_ERROR|11063|ContractAddress and code cannot be empty at the same time
INVALID_OPTTYPE_ERROR|11064|OptType must be between 0 and 2
GET_ALLOWANCE_ERROR|11065|Failed to get allowance
GET_TOKEN_INFO_ERROR|11066|Failed to get token info
SIGNATURE_EMPTY_ERROR|11067|The signatures cannot be empty
REQUEST_NULL_ERROR|12001|Request parameter cannot be null
CONNECTN_BLOCKCHAIN_ERROR|19999|Failed to connect to the blockchain 
SYSTEM_ERROR|20000|System error
