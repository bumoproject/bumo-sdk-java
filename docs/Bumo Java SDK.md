English | [中文](Bumo Java SDK_CN.md)

# Bumo Java SDK
This document introduces some of the regular APIs for Bumo Java SDK

- [Configure](#configure)
	- [Simple Configure](#simple-configure)
	- [Advanced Configure](#advanced-configure)
- [How to use](#how-to-use)
    - [Generate Account](#generate-account)
    - [Initiate TX](#initiate-tx)
    - [Query](#query)
- [Operation instructions](#operation-instructions)
	- [Create account](#create-account)
	- [Issue assets](#issue-assets)
	- [Transfer Assets](#transfer-assets)
	- [Send BU](#send-bu)
	
- [Query instructions](#query-instructions)
    - [Query the latest ledger](#query-the-latest-ledger)
    - [Query account](#query-account)
	- [Query transaction by hash](#query-transaction-by-hash)
	- [Query transaction by block serial number](#query-transaction-by-block-serial-number)
- [Example](#example)
   - [-Generate Account](#-generate-account)
   - [-Create Account](#-create-account)
   - [-Issue Assets](#-issue-assets)
   - [-Transfer Assets](#-transfer-assets)
   - [-Send BU](#-send-bu)
   - [-Query Account](#-query-account)
   - [-Query transaction by hash](#-query-transaction-by-hash)
   - [-Query transaction by block serial number](#-query-transaction-by-block-serial-number)
- [Error Codes](#error-codes)

### Configure
Bumo Java SDK is independent from Frameworks, load deployment while using it, which is: 

### Simple Configure

Operated standalone without distributed services, the specific configuration is as follows::

```

    SDKConfig config = new SDKConfig();
    
    SDKProperties sdkProperties = new SDKProperties();
    
    // For high-capacity of SDK while visiting Blockchain Nodes, SDK supports several nodes To enable SDK-accessed blockchain nodes to be highly usable, SDK provides support of connection of multi-node load
    
    String ips = "seed1.bumotest.io:26002,seed2.bumotest.io:26002,seed3.bumotest.io:26002";
    sdkProperties.setIps(ips); // Set http protocol node IP list
    
    String eventUtis = "ws://seed1.bumotest.io:26003,ws://seed2.bumotest.io:26003,ws://seed3.bumotest.io:26003";
    sdkProperties.setEventUtis(eventUtis);// Set tcp protocol node IP list
    
    
    config.configSdk(sdkProperties);

    // Complete configuration and get spi
    config.getOperationService();
    config.getQueryService();
```
### Advanced Configure
SDK has distributed capabilities and requires the introduction of redis services, the specific configuration is as follows:

```

    SDKConfig config = new SDKConfig();
    
    SDKProperties sdkProperties = new SDKProperties();
    
    // To enable SDK-accessed blockchain nodes to be highly usable, SDK provides support of connection of multi-node load
    
    String ips = "seed1.bumotest.io:26002,seed2.bumotest.io:26002,seed3.bumotest.io:26002";
    sdkProperties.setIps(ips); // Set http protocol node IP list
    
    String eventUtis = "ws://seed1.bumotest.io:26003,ws://seed2.bumotest.io:26003,ws://seed3.bumotest.io:26003";
    sdkProperties.setEventUtis(eventUtis);// Set tcp protocol node IP list
    
    // ###### When distributional service is needed, the following configuration shall be added --- Start ###### //
    
    sdkProperties.setRedisSeqManagerEnable(true);// Enable redis
    sdkProperties.setRedisHost("192.168.10.73"); // redis service IP ,please use redis cluster as possible as you could
    sdkProperties.setRedisPort(10129); // redis Port No.
    sdkProperties.setRedisPassword("xxxxxx"); // redis Verification code
    
    // ###### When distributional service is needed, the following configuration shall be added --- End ###### //
    
    config.configSdk(sdkProperties);
    
    

    // Complete configuration and get spi
    OperationService operationService = config.getOperationService();
    QueryService queryService = config.getQueryService();
```

### How to use
#### Generate account
Generate account by calling SecureKeyGenerator's generateBumoKeyPair, for example:
```
BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();
```
For more information, please see [Generate account ](#-generate-account)

#### Initiate TX
> The functions of creating accounts, issuing assets, transferring assets, sending BU and so on can be accomplished in the following four steps

1. Create operation
   
By way of calling OperationFactory's method, the operation with specified functions is created, taking creating account as an example:
   
```
BcOperation operation = OperationFactory.newCreateAccountOperation(targetAddress, initBalance);
```
For more information, please see [Operation instructions ](#operation-instructions)

2. Build transaction

   First create transaction object, bind operation to transaction object, and set information such as gasPrice, feeLimit, and signer and so on, for example:
> Note: The unit for gasPrice and feeLimit is MO and 1 BU = 10^8 MO
   
```
Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);
transaction
	.buildAddOperation(bcOperation)
	.buildAddGasPrice(1000) // 【required】 Gas price, currently minimum 1, 000MO
	.buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】 fee (1, 000, 000MO = 0.01BU)
	.buildAddSigner(txSubmitAccountPublicKey, txSubmitAccountPrivateKey);
```

3. Submit transaction

Submit transaction, and await the result of confirmation of the blockchain network, generally the confirmation takes 10 seconds, while confirmation expires after 500 seconds.
 
```
TransactionCommittedResult result = transaction.commit();
```

4. Get transaction hash

Hash is the only identifier of transaction, the developers can query about the final status of transaction via hash. A successful and partially failed transaction will return a hash, while a transaction with a failed validation of a basic parameter (such as: serial number error, insufficient balance, insufficient fees, insufficient weight, etc.), will not return a hash, and will throw an exception.
  
```
result.getHash();
```

#### Query
Call the corresponding interface of QueryService, for example, query account information
```
Account account = queryService.getAccount(address);
```
For more information, please see [Query instructions](#query-instructions)
	
### Operation instructions

#### Create account

###### Call method

```
OperationFactory.newCreateAccountOperation(destAddress, initBalance);

```
###### Entry parameters
   Parameter     |     Type     |     Description                    |
----------- | ------------ | --------------------------- |
destAddress |    String    | Blockchain account address              |
initBalance |    long      | Initial balance of the account that will be crated, mininum 1, 0000, 000MO (Note: 1 BU = 10^8 MO) |

#### Issue assets

> Through this method, digitalized assets can be registered to the blockchain network. Asset code and blockchain address of the asset issuer jointly determine the unique asset. To this end, when re-issuing the same asset, the amount of such asset will be accumulated (superaddition).

###### How to call

```
OperationFactory.newIssueAssetOperation(assetCode, amount);

```

###### Entry parameters
| Parameter           |    Type    |   Description                |
| :------------- | ---------- | --------------------- |
| assetCode      |   String   | Asset code, e.g. "HNC"   |
| amount         |   long     | Asset amount              |

#### Transfer assets

> Transfer the assets owned by the current account (asset sender) to a specified account (asset recipient)

###### How to call

```
OperationFactory.newPayAssetOperation(targetAddress, issuerAddress, assetCode, amount);

```

###### Entry parameters
| Parameter           |    Type    |   Description            |
| :------------- | ---------- | ----------------- |
| targetAddress  |   String   | Target account address      |
| issuerAddress  |   String   | Asset issuer account address  |
| assetCode      |   String   | Asset code          |
| amount    |   long     | Asset amount          |

#### Send BU
> Transfer the BUs owned by the current account (sender) to a specified account (recipient)

###### How to call

```
OperationFactory.newPayCoinOperation(targetAddress, amount);

```

###### Entry parameters
Parameter              |      Type     |     Description      |
---------------- | ------------ |  ------------  |
targetAddress    |   String     | Target blockchain address | 
sendTokenAmount  |    long      |    Sending amount    | 

### Query instructions

#### Query the latest ledger

###### How to call
```
Ledger ledger = queryService.getLastestLedger();

```

###### Return Parameter
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
header      |  [Header](#header-object)  |  Block header    |

###### Header Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
accountTreeHash    |  String    |  Account tree hash    |
closeTime          |  long      |  Block closing time    |
consensusValueHash |  String    |  Consensus content hash    |
previousHash       |  String    |  Previous block header's hash  |
seq                |  long      |  Block sequence      |
txCount            |  long      |  Number of transactions    |
validatorsHash     |  String    |  Verification node list hash |
version            |  long      |  Version number    |
consensusValue     |  [ConsensusValue](#consensusvalue-object)  |  Consensus value    |

###### ConsensusValue Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
closeTime    |  long    |  Block closing time    |
previousLedgerHash |  String      |  Previous block header's hash    |
txset |  [Txset](#txset-object)    |  Transaction set    |

###### Txset Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
txs    |  [Transaction](#transaction-object)[]    |  Block closing time    |


#### Query account

###### How to call

```
Account account = queryService.getAccount(address);

```
###### Entry parameters
Parameter    |   Type    |      Description      |
------- | --------- | -------------- |
address |  String   | Account blockchain address |

###### Return Parameter
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
address     |  String        |  Account blockchain address     |
balance     |  Long          |  Account balance (Unit: MO　Note:1 BU = 10^8 MO)           |
assetsHash  |  String        |  Account assets hash       |
storageHash |  String        |  Account blockchain storage hash |
nonce       |  long          |  Number of times of transaction of the account       |
assets      |  [Asset](#asset-object)[]       |  Assets list           |
contract    |  [Contract](#contract-object)      |  Contract account           |
metadatas   |  [SetMetadata](#setmetadata-object)[] |  Remarks           |
priv        |  [Priv](#priv-object)          |  Limits of authority               |

###### Asset Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
amount      |  Long        |  Account assets amount       |
key         |  [Key](#key-object)           |  Asset identification  |

###### Key Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
code        |  String        |  Asset code           |
issuer      |  String        |  Asset issuer         |

###### Contract Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
payload     |  String        |  Contract entry parameter           |

###### SetMetadata Object
Parameter        |   Type         |       Description          |       
----------- | -------------- | ------------------- |
key         |  String        |  Key words index         |
value       |  String        |  Content               |
version     |  long          |  Version               |

###### Priv Object
Parameter         |   Type         |       Description          |       
------------ | -------------- | ------------------- |
masterWeight |  long          |  Account self-weight       |
signers      |  List<[Signer](#signer-object)>     |  Signer list         |
threshold    |  [Threshold](#threshold-object)     |  Threshold weight           |

###### Signer Object
Parameter         |   Type         |       Description          |       
------------ | -------------- | ------------------- |
address      |  String        |  Signatory account address     |
weight       |  long          |  signer's weight         |

###### Threshold Object
Parameter            |   Type                |       Description            |       
--------------- | --------------------- | --------------------- |
txThreshold     |  long                 |  Transaction default threshold weight     |
type_thresholds |  List<[TypeThreshold](#typethreshold-object)>  |  Specified operation type’s threshold weight |

###### TypeThreshold Object
Parameter            |   Type                |       Description            |       
--------------- | --------------------- | --------------------- |
type            |  long                 |  Specified operation type         |
threshold       |  long                 |  Threshold weight, default -1     |



#### Query transaction by hash

> Query the final status information of transaction by hash

###### How to call

```
TransactionHistory tx = queryService.getTransactionResultByHash(txHash);

```

###### Entry parameters
| Parameter           |    Type    |   Description     |
| :------------- | ---------- | ---------- |
| txHash         |   String   | Transaction hash   |

###### Return Parameter (TransactionHistory)
| Parameter    |        Type                |    Description        |
| :------ | -------------------------- | -------------- |
| tx      | [TransactionHistory](#transactionhistory-object)         |  Historical transactions result  |

###### TransactionHistory Object
| Parameter          |   Type            |    Description              |
| :------------ | ----------------- | -------------------- |
| totalCount    |   long            | Total number of the transactions queried |
| transactions  |   [Transaction](#transaction-object)[]   | transactions list         |

###### Transaction Object
| Parameter          |   Type            |    Description              |
| :------------ | ----------------- | -------------------- |
| closeTime     |   long            |   Closing time of historical transactions   |
| errorCode     |   long            |   Error code             |
| errorDesc     |   String          |   Error description           |
| ledgerSeq     |   long            |   ledger sequence |
| hash          |   String          |  transactions hash        |
| signatures    |   [Signature](#signature-object)[]     |  transaction signatures list  |
| transaction   |   [SubTransaction](#subtransaction-object)  |  sub-transactions              |

###### Signature Object
| Parameter          |   Type            |    Description              |
| :------------ | ----------------- | -------------------- |
| publicKey     |   String          |   Signer's public key         |
| signData      |   String          |   Signature data           |

###### SubTransaction Object
| Parameter          |   Type            |    Description              |
| :------------ | ----------------- | -------------------- |
| sourceAddress |   String          |   account address of initiating Transaction     |
| nonce         |   long            |   Transaction serial number  |
| metadata      |   String          |   Additional information  |
| feeLimit      |   long            |   Transaction fee         |
| gasPrice      |   long            |   Packaging fee           |
| operations    |   [Operation](#operation-object)[]     |   Operation list           |

###### Operation Object
| Parameter            |   Type            |    Decription              |
| :-------------- | ----------------- | -------------------- |
| sourceAddress   |   String          |   Source account address         |
| type            |   int             |   Operation type           |
| metadata        |   String          |   Additional information           |
| createAccount   |   [CreateAccount](#createaccount-object)   |   Create account operation       |
| issueAsset      |   [IssueAsset](#issueasset-object)      |   Issue asset operation       |
| payAsset        |   [PayAsset](#payasset-object)        |   Transfer asset operation       |
| payCoin         |   [PayCoin](#paycoin-object)         |   Transfer BU operation         |

###### CreateAccount Object
| Parameter            |   Type            |    Decription              |
| :-------------- | ----------------- | -------------------- |
| metadata   |   String          |   Additional information         |
| destAddress        |   String          |   Target account address   |
| initBalance   |   CreateAccount   |   Initial balance (Unit: MO　Note:1 BU = 10^8 MO)      |
| initInput      |   String      |   Contract input parameter       |
| contract        |   [Contract](#contract-object)        |   Contract  |
| metadatas         |   List<[SetMetadata](#setmetadata-object)>         |   Additional information         |
| priv         |   [Priv](#Priv-object)         |   Priveledge         |

###### IssueAsset Object
| Parameter            |   Type            |    Decription              |
| :-------------- | ----------------- | -------------------- |
| metadata   |   String          |   Additional information         |
| amount   |   CreateAccount   |   Asset amount      |
| code      |   String      |   Asset code       |

###### PayAsset Object
| Parameter            |   Type            |    Decription              |
| :-------------- | ----------------- | -------------------- |
| metadata   |   String          |   Additional information         |
| destAddress   |   String          |   Target address         |
| asset   |   [Asset](#asset-object)   |   Asset amount      |
| input      |   String      |   Contract input parameter       |

###### PayCoin Object
| Parameter            |   Type            |    Decription              |
| :-------------- | ----------------- | -------------------- |
| destAddress   |   String          |   Target address         |
| amount   |   long   |   BU amount (Unit: MO　Note:1 BU = 10^8 MO)     |
| input      |   String      |   Contract input parameter       |

##### Query transaction by block serial number

> Query the final status information of transaction by block serial number

###### How to call

```
TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(ledgerSeq);

```
###### Entry parameters
| Parameter           |    Type    |   Description     |
| :------------- | ---------- | ---------- |
| txHash         |   String   | Transaction hash   |

The parameters to return, please refer the instructions of “[Query transaction by hash](#query-transaction-by-hash)”

### Example

#### -Generate account
```
// Randomly generate a public key, private key and blockchain address of Bumo block account
BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

// Note: The developer system is required to record the public key, private key and address of such account

String accountAddress = keyPair.getBumoAddress(); // Block chain account address
String accountSk = keyPair.getPriKey(); // Block chain account private key
String accountPk = keyPair.getPubKey(); // Block chain account public key
```

#### -Create account

> An account creation operator (blockchain account already available) is required, when creating an account, to spend a transaction fee of 0.01BU, and at least 0.1BU initial amount (to be provided by account creation operator) shall be given to such new account.

```
// Randomly generate a public key, private key and blockchain address of Bumo block account
BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

// Note: The developer system is required to record the public key, private key and address of such account

String accountAddress = keyPair.getBumoAddress(); // Blockchain account address 
String accountSk = keyPair.getPriKey(); // Blockchain account private key
String accountSk = keyPair.getPriKey(); // Blockchain account public key

try {
    // Transaction submitter’s blockchain account address
	String txSubmitAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// Transaction submitter’s account public key
	String txSubmitAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// Transaction submitter’s account private key
	String txSubmitAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);

	// Currently, the minimum initial balance of an account created is 0.1BU
	BcOperation bcOperation = OperationFactory.newCreateAccountOperation(accountAddress, ToBaseUnit.BU2MO("0.1")); // Create create account operation
	
	TransactionCommittedResult result = transaction
			.buildAddOperation(bcOperation)
			.buildAddGasPrice(1000) // 【required】 Gas price, currently minimum 1, 000MO
		    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】 fee (1, 000, 000MO = 0.01BU)
		    .buildAddSigner(txSubmitAccountPublicKey, txSubmitAccountPrivateKey)
			.commit();
	
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```
#### -Issue assets

```
try {
	// Asset issuer’s blockchain account address
	String issueAssetsAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// Asset issuer’s account public key
	String issueAssetsAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// Asset issuer’s account private key
	String issueAssetsAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	
	Transaction transaction = operationService.newTransaction(issueAssetsAccountAddress);
	
	String assetCode = "HNC";
	Long issueAmount = 1000000000L; // Issue 1.0 bn HNC
	BcOperation bcOperation = OperationFactory.newIssueAssetOperation(assetCode,issueAmount ); // Create asset issuance operation
	
	TransactionCommittedResult result = transaction
			.buildAddOperation(bcOperation)
			.buildAddGasPrice(1000) // 【required】 Gas price, currently minimum 1, 000MO
		    .buildAddFeeLimit(ToBaseUnit.BU2MO("50.01")) // 【required】handling fee + operating fee (1, 000, 000MO + 5, 000, 000, 000MO = 50.01BU)
		    .buildAddSigner(issueAssetsAccountPublicKey, issueAssetsAccountPrivateKey)
			.commit();
	
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```

#### -Transfer assets

```
try {
	// Asset owner's blockchain account address
	String assetOwnerAccountAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";
	// Asset owner’s account public key
	String assetOwnerAccountPublicKey = "b00127e8dbf046a533d2e4cf2bd55f3466410c1d6f76f09dd5ca721c996c691e72d8e5a1bb17";
	// Asset owner’s account private key
	String assetOwnerAccountPrivateKey = "privbwMpfEK1i4jkReGmApwkHbY8ggMKCML9mJSx2DW9ddCutkCBx7j4";
	
	Transaction transaction = operationService.newTransaction(assetOwnerAccountAddress);
	
	String destAccountAddress = "buQjM8zQV3VXiUETCaJCogKbFoofnSWufgXo"; // Asset recipient’s account address
	String issuerAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";// Asset issuer
	String assetCode = "HNC"; // Asset ID
	Long sendAmount = 1000000000L; // Issue 1.0 bn HNC
	BcOperation bcOperation = OperationFactory.newPayAssetOperation(destAccountAddress,issuerAddress,assetCode,sendAmount); // Create asset issuance operation
	
	TransactionCommittedResult result = transaction
			.buildAddOperation(bcOperation)
			.buildAddGasPrice(1000) // 【required】 Gas price, currently minimum 1, 000MO
		    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】 fee (1, 000, 000MO = 0.01BU)
		    .buildAddSigner(assetOwnerAccountPublicKey, assetOwnerAccountPrivateKey)
			.commit();
	
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}
```

#### -Send BU

```
private static String address = "buQgNykdqpT652bVGp513iXUP2tXY5h4CGC1";
private static String publicKey = "b00180b8cc9291f29636446b068fa0b496443efbc95ded6bc62fb8b5b4f6282e6fc9cbee8fb4";
private static String privateKey = "privbzy1hgypHK8fiew6kpwG5VNYjNDg4QhGjpBJvRWo4L86yJpKFoSL";

try {
    String txSubmitAccountAddress = address;// Trade author block chain account address
    String targetAddress = "buQchyqkRdJeyfrRwQVCEMdxEV2BPSoeQsGx";
	Long sendTokenAmount = ToBaseUnit.BU2MO("0.6");
	Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);
	
	PayCoinOperation payCoinOperation = OperationFactory.newPayCoinOperation(targetAddress, sendTokenAmount);
	TransactionCommittedResult result = transaction.buildAddOperation(payCoinOperation)
		.buildTxMetadata("send BU token")
		.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
	    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
	    .buildAddSigner(publicKey, privateKey)
		.commit();
	System.out.println(result.getHash());
} catch (SdkException e) {
	e.printStackTrace();
}

```

#### -Query the latest block query

```
Ledger ledger = queryService.getLastestLedger();
System.out.println(ledger);

```

#### -Query account

```
String address = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT"; 
Account account = queryService.getAccount(address);

```

#### -Query transaction by hash

```
String txHash = "";
TransactionHistory tx = queryService.getTransactionResultByHash(txHash);
```

#### -Query transaction by block serial number

```
Long ledgerSeq = 1L;
TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(ledgerSeq);
```
### Error code

> The error code information to call interface

Parameter | Description
-----|-----
0     | Success
1 | Service internal error
2 | Parameter error
3 | Objects already exist, such as repeated submission of transactions
4 | Object does not exist, such as failure to query account, TX, block
5 | TX timeout means that the TX has been removed from the TX cache queue by the current node, but it does not mean that this must not be executed
20 | The result of the expression is false, which means that the TX is not executing successfully at the moment, but that does not mean that the block will not succeed in the future block
21 | An error in expression syntax analysis, which means that the TX will fail
90 | Illegal public key
91 | Illegal private key
92 | Illegal asset issuing address
93 | The signature weight is not enough to reach the threshold value of the operation
94 | Illegal address
95 | Not in the time range
96 | No consensus
97 | Lack operation in transaction
98 | The number of operations contained in the transaction exceeds the limit
99 | Illegal transaction serial number
100 | Insufficient balance of available token
101 | he target address is equal to the source address
102 | The target account has already existed
103 | The target account does not exist
104 | Insufficient balance of available assets
105 | The amount of assets is too large, beyond the scope of Int64
111 | Insufficient handling fee provided
112 | Too early submission of transaction
113 | Too late submission of transaction
114 | The number of transactions received by the server is too much and is being processed
120 | Invalid weight value
130 | Input does not exist
131 | Illegal input
132 | Non-supply-chain transaction
144 | Incorrect metadata version of account
151 | Failure of contract execution
152 | Failure of contract syntax analysis
10001 | Target address required to be set for operation
10002 | Abnormal running status
10003 | The contract address can not be empty
10004 | The asset issuer cannot be empty
10005 | Asset code can not be empty
10006 | The amount of the transfer must be more than 0
10007 | Setting metadata can not be empty
10008 | You must set up masterWeight or signer
10009 | You must set up txThreshold or typeThresholds
10010 | The amount of assets issued should not be less than 0
10011 | TxThreshold can't be less than 0
10012 | MasterWeight can't be less than 0
10013 | The weight of the signer should not be less than 0
10014 | The specific operation threshold should not be less than 0
10015 | The added signer's address can not be empty
10016 | Specific threshold operation type can not be empty
10017 | Payment of BU Token must be more than 0
10018 | Evaluation operation cannot be empty
10019 | InitInput cannot be empty
10020 | InitBalance value is illegal
10021 | Input cannot be empty
10022 | The key or value of metadata cannot be empty
10100 | The initiator of the transaction cannot be empty
10101 | Transaction signature list cannot be empty
10102 | Abnormal transaction status
10103 | The transaction failed! No notice was received
10104 | Blob can not be empty
10105 | The public key can not be empty
10106 | Do not generate Blob repeatedly
10107 | The operation list cannot be empty
10108 | The private key cannot be empty
10109 | The fee value is illegal
10110 | The value of gas price is illegal
90001 | Parsing URI error, please check
90002 | Parsing IP error, please check
90003 | Initializing the node manager failed! Please confirm that at least one node can be accessed
90004 | Accessing bottom time out!
90005 | Verification of local signature failed
90006 | Event handlers that was registered are not found
90007 | Signature exception! Please confirm the correctness of the public key or private key
90008 | Routing node failure!Please confirm that the monitor configuration and access configuration can match
90009 | Timeout exception: Not notified over 24 hours, so Transaction synchronizer automatically removed
90010 | Get redis lock timeout
99999 | Internal error
