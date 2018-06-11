package io.bumo.sdk.example;

import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import io.bumo.sdk.core.adapter.bc.response.ledger.Ledger;
import io.bumo.sdk.core.config.SDKConfig;
import io.bumo.sdk.core.config.SDKEngine;
import io.bumo.sdk.core.config.SDKProperties;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.operation.BcOperation;
import io.bumo.sdk.core.operation.OperationFactory;
import io.bumo.sdk.core.operation.impl.*;
import io.bumo.sdk.core.spi.OperationService;
import io.bumo.sdk.core.spi.QueryService;
import io.bumo.sdk.core.transaction.Transaction;
import io.bumo.sdk.core.transaction.model.TransactionCommittedResult;
import io.bumo.sdk.core.utils.ToBaseUnit;
import io.bumo.sdk.core.utils.blockchain.BlockchainKeyPair;
import io.bumo.sdk.core.utils.blockchain.SecureKeyGenerator;

public class DigitalAssetsDemo {
	private static String address = "buQgQ3s2qY5DTFLezXzqf7NWLcVXufCyN93L";
    private static String publicKey = "b001a8d29c772472953b51358ae05aa082c2de6af5b585e909bdb6078ae013d39bb41644d4a7";
    private static String privateKey = "privbxpwDNRMe7xAshHChrnUdbLK5GpxgvqwhNcMMXA6byaX6VM85ThD";
	public static void main(String[] args) throws SdkException, InterruptedException {

		// config in codes
		String eventUtis = "ws://127.0.0.1:26003";
		String ips = "127.0.0.1:26002";

		SDKConfig config = new SDKConfig();
		SDKProperties sdkProperties = new SDKProperties();
		sdkProperties.setEventUtis(eventUtis);
		sdkProperties.setIps(ips);
		sdkProperties.setRedisSeqManagerEnable(false);
		sdkProperties.setRedisHost("192.168.100.33");
		sdkProperties.setRedisPort(10379);
		sdkProperties.setRedisPassword("xxxxxx");
		config.configSdk(sdkProperties);
		OperationService operationService = config.getOperationService();
		QueryService queryService = config.getQueryService();

		queryTransactionByHash(queryService);

		// config in config.properties
//		SDKEngine sdkEngine = SDKEngine.getInstance();
//		OperationService operationService = sdkEngine.getOperationService();
//		QueryService queryService = sdkEngine.getQueryService();

        // create simple account
        createSimpleAccount(operationService);

        // query account
        queryAccount(queryService);

        // issue assets
        issueAssets(operationService);
	}
	
	/**
	 * create simple account
	 */
	@SuppressWarnings("unused")
	public static void createSimpleAccount(OperationService operationService) {
		// Public private key pair and block chain address of a random Bumo block account
		BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

		// Note: the developer system needs to record the public and private key and address of the account

		String accountAddress = keyPair.getBumoAddress(); // Block chain account address
		String accountSk = keyPair.getPriKey(); // Block chain account private key
		String accountPk = keyPair.getPubKey(); // Block chain account public key

		try {
			// Create an account operation
			CreateAccountOperation operation = OperationFactory.newCreateAccountOperation(address, accountAddress, ToBaseUnit.BU2MO("0.1"));

			// Get start Tx
			String txSubmitAccountAddress = address; // Transaction sender block chain account address
			Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);

			// Bind operation
			transaction.buildTxMetadata("build simple account")
					.buildAddOperation(operation)
					.buildAddGasPrice(100) // 【required】 the price of Gas, at least 1000MO
				    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
				    .buildAddSigner(publicKey, privateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}

	/**
	 * create simple account
	 */
	@SuppressWarnings("unused")
	public static void createContractAccount(OperationService operationService) {
		// Public private key pair and block chain address of a random Bumo block account
		BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

		// Note: the developer system needs to record the public and private key and address of the account
		String accountAddress = keyPair.getBumoAddress(); // Block chain account address
		String accountSk = keyPair.getPriKey(); // Block chain account private key
		String accountPk = keyPair.getPubKey(); // Block chain account public key

		try {
			// Create an account operation
			CreateAccountOperation operation = OperationFactory.newCreateContractOperation(address, accountAddress, ToBaseUnit.BU2MO("0.1"),
					"\"use strict\";function init(bar){/*init whatever you want*/return;}function main(input){issueAsset(\"FMR\", \"10000\");}function query(input){ let sender_balance = getBalance(sender);let this_balance = getBalance(thisAddress);log(sender_balance);log(this_balance);return input;}",
					"");

			// Get start Tx
			String txSubmitAccountAddress = address; // Transaction sender block chain account address
			Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);

			// Bind operation
			transaction.buildTxMetadata("build simple account")
					.buildAddOperation(operation)
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
					.buildAddFeeLimit(ToBaseUnit.BU2MO("10.01")) // 【required】Service Charge (1000000MO = 0.01BU)
					.buildAddSigner(publicKey, privateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			System.out.println("new address:" + accountAddress);
			System.out.println("new publickey :" + accountPk);
			System.out.println("new private key:" + accountSk);

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}

	public static void invokeContractByAsset(OperationService operationService) {
		try {
			String contractAccount = "buQh4tAWb7pAnDrWhXMRHioojMi4zUf9ZDKD";

			// Creating asset distribution operations
			BcOperation operation = OperationFactory.newInvokeContractByAssetOperation(address, contractAccount, "", "", 0, "issue");

			// Get start Tx
			Transaction transaction = operationService.newTransaction(address);

			// Bind operation
			transaction.buildAddOperation(operation)
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
					.buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Fee + Operation Fee (1000000MO + 5000000000MO = 50.01BU)
					.buildAddSigner(publicKey, privateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}

	public static void invokeContractByBU(OperationService operationService) {
		try {
			String contractAccount = "buQh4tAWb7pAnDrWhXMRHioojMi4zUf9ZDKD";

			// Creating asset distribution operations
			BcOperation operation = OperationFactory.newInvokeContractByBUOperation(address, contractAccount, 0, "pay_asset");

			// Get start Tx
			Transaction transaction = operationService.newTransaction(address);

			// Bind operation
			transaction.buildAddOperation(operation)
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
					.buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Fee + Operation Fee (1000000MO + 5000000000MO = 50.01BU)
					.buildAddSigner(publicKey, privateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void issueAssets(OperationService operationService) {
		try {
			// Asset issuer block chain account address
			String issueAssetsAccountAddress = "buQgQ3s2qY5DTFLezXzqf7NWLcVXufCyN93L";
			// Public key of asset issuer account
			String issueAssetsAccountPublicKey = "b001a8d29c772472953b51358ae05aa082c2de6af5b585e909bdb6078ae013d39bb41644d4a7";
			// Asset issuer's private key
			String issueAssetsAccountPrivateKey = "privbxpwDNRMe7xAshHChrnUdbLK5GpxgvqwhNcMMXA6byaX6VM85ThD";

			// Creating asset distribution operations
			String assetCode = "HNC";
			Long issueAmount = 1000000000L; // Issue 1 billion HNC
			IssueAssetOperation operation = OperationFactory.newIssueAssetOperation(address, assetCode,issueAmount );

			// Get start Tx
			Transaction transaction = operationService.newTransaction(issueAssetsAccountAddress);

			// Bind operation
			 transaction.buildAddOperation(operation)
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
				    .buildAddFeeLimit(ToBaseUnit.BU2MO("50.01")) // 【required】Service Fee + Operation Fee (1000000MO + 5000000000MO = 50.01BU)
				    .buildAddSigner(issueAssetsAccountPublicKey, issueAssetsAccountPrivateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendBuToken(OperationService operationService) {
		String txSubmitAccountAddress = address;// Trade author block chain account address
		String targetAddress = "buQchyqkRdJeyfrRwQVCEMdxEV2BPSoeQsGx";
		Long sendTokenAmount = ToBaseUnit.BU2MO("0.6");
		try {
			// Creating asset distribution operations
			PayCoinOperation operation = OperationFactory.newPayCoinOperation(address, targetAddress, sendTokenAmount);

			// Get start Tx
			Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);

			// Bind operation
			transaction.buildAddOperation(operation)
				.buildTxMetadata("send BU token")
				.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
			    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
			    .buildAddSigner(publicKey, privateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void PayAsset(OperationService operationService) {
		try {
			// Asset owner block chain account address
			String assetOwnerAccountAddress = "buQgQ3s2qY5DTFLezXzqf7NWLcVXufCyN93L";
			// Asset owner account public key
			String assetOwnerAccountPublicKey = "b001a8d29c772472953b51358ae05aa082c2de6af5b585e909bdb6078ae013d39bb41644d4a7";
			// Asset owner's private key
			String assetOwnerAccountPrivateKey = "privbxpwDNRMe7xAshHChrnUdbLK5GpxgvqwhNcMMXA6byaX6VM85ThD";

			// Creating asset distribution operations
			String destAccountAddress = "buQjM8zQV3VXiUETCaJCogKbFoofnSWufgXo"; // Account address of the asset recipient
			String issuerAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";// Asset issuer
			String assetCode = "HNC"; // Asset ID
			Long sendAmount = 1000000000L; // Issue 1 billion HNC
			PayAssetOperation operation = OperationFactory.newPayAssetOperation(address, destAccountAddress,issuerAddress,assetCode,sendAmount); // 创建资产发行操作

			// Get start Tx
			Transaction transaction = operationService.newTransaction(assetOwnerAccountAddress);

			// Bind operation
			transaction
					.buildAddOperation(operation)
					.buildTxMetadata("payment")
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
				    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
				    .buildAddSigner(assetOwnerAccountPublicKey, assetOwnerAccountPrivateKey);

			// Commit Tx
			TransactionCommittedResult result = transaction.commit();

			// Get the hash of Tx
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void queryAccount(QueryService queryService) {
		String address = "buQgQ3s2qY5DTFLezXzqf7NWLcVXufCyN93L";
		Account account = queryService.getAccount(address);
		System.out.println(account.getAddress());
	}
	
	public static void queryTransactionByHash(QueryService queryService) {
		String txHash = "bc0b59554bb14709c24f5a5fd5725a0e2cb49ad6f836cb646802df87293218b6";
		TransactionHistory tx = queryService.getTransactionHistoryByHash(txHash);
		System.out.println(new String(HexFormat.hexToByte(tx.getTransactions()[0].getTransaction().getMetadata())));
	}
	
	public static void queryTransactionBySeq(QueryService queryService) {
		Long seq = 1L;
		TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(seq);
		System.out.println(tx.getTotalCount());
	}
	
	public static void queryLatestLedger(QueryService queryService) {
		Ledger ledger = queryService.getLatestLedger();
		System.out.println(ledger.getHeader().getHash());
	}
}
