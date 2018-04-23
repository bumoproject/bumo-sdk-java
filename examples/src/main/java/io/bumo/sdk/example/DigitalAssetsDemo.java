package io.bumo.sdk.example;

import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import io.bumo.sdk.core.adapter.bc.response.ledger.Ledger;
import io.bumo.sdk.core.config.SDKConfig;
import io.bumo.sdk.core.config.SDKProperties;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.BcOperation;
import io.bumo.sdk.core.operation.OperationFactory;
import io.bumo.sdk.core.operation.impl.PayCoinOperation;
import io.bumo.sdk.core.spi.BcOperationService;
import io.bumo.sdk.core.spi.BcQueryService;
import io.bumo.sdk.core.transaction.Transaction;
import io.bumo.sdk.core.transaction.model.TransactionCommittedResult;
import io.bumo.sdk.core.utils.ToBaseUnit;
import io.bumo.sdk.core.utils.blockchain.BlockchainKeyPair;
import io.bumo.sdk.core.utils.blockchain.SecureKeyGenerator;

public class DigitalAssetsDemo {
	private static String address = "buQdBdkvmAhnRrhLp4dmeCc2ft7RNE51c9EK";
    private static String publicKey = "b001b6d3120599d19cae7adb6c5e2674ede8629c871cb8b93bd05bb34d203cd974c3f0bc07e5";
    private static String privateKey = "privbtGQELqNswoyqgnQ9tcfpkuH8P1Q6quvoybqZ9oTVwWhS6Z2hi1B";
	public static void main(String[] args) throws SdkException, InterruptedException {
		String eventUtis = "ws://127.0.0.1:36003";
        String ips = "127.0.0.1:36002";

        SDKConfig config = new SDKConfig();
        SDKProperties sdkProperties = new SDKProperties();
        sdkProperties.setEventUtis(eventUtis);
        sdkProperties.setIps(ips);
        sdkProperties.setRedisSeqManagerEnable(false);
        sdkProperties.setRedisHost("192.168.100.33");
        sdkProperties.setRedisPort(10379);
        sdkProperties.setRedisPassword("xxxxxx");
        config.configSdk(sdkProperties);
        
        BcOperationService operationService = config.getOperationService();
        BcQueryService queryService = config.getQueryService();
        
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
	public static void createSimpleAccount(BcOperationService operationService) {
		// Public private key pair and block chain address of a random Bumo block account
		BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();

		// Note: the developer system needs to record the public and private key and address of the account

		String accountAddress = keyPair.getBumoAddress(); // Block chain account address
		String accountSk = keyPair.getPriKey(); // Block chain account private key
		String accountPk = keyPair.getPubKey(); // Block chain account public key

		try {
			String txSubmitAccountAddress = address;// Transaction sender block chain account address
			Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);

			new OperationFactory();
			
			BcOperation bcOperation = OperationFactory.newCreateAccountOperation(accountAddress, ToBaseUnit.BU2MO("0.1")); // Create an account operation
			
			TransactionCommittedResult result = transaction
					.buildTxMetadata("build simple account")
					.buildAddOperation(bcOperation)
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
				    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
				    .buildAddSigner(publicKey, privateKey)
					.commit();
			
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void issueAssets(BcOperationService operationService) {
		try {
			// Asset issuer block chain account address
			String issueAssetsAccountAddress = "buQdBdkvmAhnRrhLp4dmeCc2ft7RNE51c9EK";
			// Public key of asset issuer account
			String issueAssetsAccountPublicKey = "b001b6d3120599d19cae7adb6c5e2674ede8629c871cb8b93bd05bb34d203cd974c3f0bc07e5";
			// Asset issuer's private key
			String issueAssetsAccountPrivateKey = "privbtGQELqNswoyqgnQ9tcfpkuH8P1Q6quvoybqZ9oTVwWhS6Z2hi1B";
			
			Transaction transaction = operationService.newTransaction(issueAssetsAccountAddress);
			
			String assetCode = "HNC";
			Long issueAmount = 1000000000L; // Issue 1 billion HNC
			BcOperation bcOperation = OperationFactory.newIssueAssetOperation(assetCode,issueAmount ); // Creating asset distribution operations
			
			TransactionCommittedResult result = transaction
					.buildAddOperation(bcOperation)
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
				    .buildAddFeeLimit(ToBaseUnit.BU2MO("50.01")) // 【required】Service Fee + Operation Fee (1000000MO + 5000000000MO = 50.01BU)
				    .buildAddSigner(issueAssetsAccountPublicKey, issueAssetsAccountPrivateKey)
					.commit();
			
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendBuToken(BcOperationService operationService) {
		String txSubmitAccountAddress = address;// Trade author block chain account address
		String targetAddress = "buQchyqkRdJeyfrRwQVCEMdxEV2BPSoeQsGx";
		Long sendTokenAmount = ToBaseUnit.BU2MO("0.6");
		Transaction transaction = operationService.newTransaction(txSubmitAccountAddress);
		try {
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
	}
	
	public static void PayAsset(BcOperationService operationService) {
		try {
			// Asset owner block chain account address
			String assetOwnerAccountAddress = "buQdBdkvmAhnRrhLp4dmeCc2ft7RNE51c9EK";
			// Asset owner account public key
			String assetOwnerAccountPublicKey = "b001b6d3120599d19cae7adb6c5e2674ede8629c871cb8b93bd05bb34d203cd974c3f0bc07e5";
			// Asset owner's private key
			String assetOwnerAccountPrivateKey = "privbtGQELqNswoyqgnQ9tcfpkuH8P1Q6quvoybqZ9oTVwWhS6Z2hi1B";
			
			Transaction transaction = operationService.newTransaction(assetOwnerAccountAddress);
			
			String destAccountAddress = "buQjM8zQV3VXiUETCaJCogKbFoofnSWufgXo"; // Account address of the asset recipient
			String issuerAddress = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT";// Asset issuer
			String assetCode = "HNC"; // Asset ID
			Long sendAmount = 1000000000L; // Issue 1 billion HNC
			BcOperation bcOperation = OperationFactory.newPaymentOperation(destAccountAddress,issuerAddress,assetCode,sendAmount); // 创建资产发行操作
			
			TransactionCommittedResult result = transaction
					.buildAddOperation(bcOperation)
					.buildTxMetadata("payment")
					.buildAddGasPrice(1000) // 【required】 the price of Gas, at least 1000MO
				    .buildAddFeeLimit(ToBaseUnit.BU2MO("0.01")) // 【required】Service Charge (1000000MO = 0.01BU)
				    .buildAddSigner(assetOwnerAccountPublicKey, assetOwnerAccountPrivateKey)
					.commit();
			
			System.out.println(result.getHash());
		} catch (SdkException e) {
			e.printStackTrace();
		}
	}
	
	public static void queryAccount(BcQueryService queryService) {
		String address = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT"; 
		Account account = queryService.getAccount(address);
		System.out.println(account);
	}
	
	public static void queryTransactionByHash(BcQueryService queryService) {
		String txHash = "";
		TransactionHistory tx = queryService.getTransactionHistoryByHash(txHash);
		System.out.println(tx);
	}
	
	public static void queryTransactionBySeq(BcQueryService queryService) {
		Long seq = 1L;
		TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(seq);
		System.out.println(tx);
	}
	
	public static void queryLatestLedger(BcQueryService queryService) {
		Ledger ledger = queryService.getLastestLedger();
		System.out.println(ledger);
	}
}
