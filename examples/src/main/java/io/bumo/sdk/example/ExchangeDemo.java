package io.bumo.sdk.example;

import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import io.bumo.sdk.core.adapter.bc.response.ledger.Ledger;
import io.bumo.sdk.core.config.SDKConfig;
import io.bumo.sdk.core.config.SDKEngine;
import io.bumo.sdk.core.config.SDKProperties;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.OperationFactory;
import io.bumo.sdk.core.operation.impl.PayCoinOperation;
import io.bumo.sdk.core.spi.OperationService;
import io.bumo.sdk.core.spi.QueryService;
import io.bumo.sdk.core.transaction.Transaction;
import io.bumo.sdk.core.transaction.model.TransactionCommittedResult;
import io.bumo.sdk.core.utils.ToBaseUnit;
import io.bumo.sdk.core.utils.blockchain.BlockchainKeyPair;
import io.bumo.sdk.core.utils.blockchain.SecureKeyGenerator;

public class ExchangeDemo {
	private static String address = "buQdBdkvmAhnRrhLp4dmeCc2ft7RNE51c9EK";
    private static String publicKey = "b001b6d3120599d19cae7adb6c5e2674ede8629c871cb8b93bd05bb34d203cd974c3f0bc07e5";
    private static String privateKey = "privbtGQELqNswoyqgnQ9tcfpkuH8P1Q6quvoybqZ9oTVwWhS6Z2hi1B";
	public static void main(String[] args) throws SdkException, InterruptedException {
        // config in codes
//		String eventUtis = "ws://127.0.0.1:26003";
//        String ips = "127.0.0.1:26002";
//
//        SDKConfig config = new SDKConfig();
//        SDKProperties sdkProperties = new SDKProperties();
//        sdkProperties.setEventUtis(eventUtis);
//        sdkProperties.setIps(ips);
//        sdkProperties.setRedisSeqManagerEnable(true);
//        sdkProperties.setRedisHost("192.168.100.33");
//        sdkProperties.setRedisPort(36009);
//        sdkProperties.setRedisPassword("xxxxxx");
//        config.configSdk(sdkProperties);
//        OperationService operationService = config.getOperationService();
//        QueryService queryService = config.getQueryService();

        // config in config.properties
		SDKEngine sdkEngine = SDKEngine.getInstance();
        OperationService operationService = sdkEngine.getOperationService();
        QueryService queryService = sdkEngine.getQueryService();

        // create simple account
        createBuChainAccount();

        // send BU token
        sendBuToken(operationService);

        // query account
        queryAccount(queryService);
	}
	
	/**
	 *
	 * create exchange wallet or the user's Buchain account
	 *
	 * @return the account address and public-private key pairs
	 */

	public static BlockchainKeyPair createBuChainAccount(){
		// Public private key pair and block chain address of a random Bumo block account
		BlockchainKeyPair keyPair = SecureKeyGenerator.generateBumoKeyPair();
		// Note: the system needs to record the public and private key and address of the account
		String accountAddress = keyPair.getBumoAddress(); // Block chain account address
		String accountSk = keyPair.getPriKey(); // Block chain account private key
		String accountPk = keyPair.getPubKey(); // Block chain account public key
		
		return keyPair;
	}
	
	public static void sendBuToken(OperationService operationService) {
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
	
	
	public static void queryAccount(QueryService queryService) {
		String address = "buQYBzc87B71wDp4TyikrSkvti8YTMjYN8LT"; 
		Account account = queryService.getAccount(address);
		System.out.println(account);
	}
	
	public static void queryTransactionByHash(QueryService queryService) {
		String txHash = "";
		TransactionHistory tx = queryService.getTransactionHistoryByHash(txHash);
		System.out.println(tx);
	}
	
	public static void queryTransactionBySeq(QueryService queryService) {
		Long seq = 1L;
		TransactionHistory tx = queryService.getTransactionHistoryByLedgerSeq(seq);
		System.out.println(tx);
	}
	
	public static void queryLatestLedger(QueryService queryService) {
		Ledger ledger = queryService.getLastestLedger();
		System.out.println(ledger);
	}
}
