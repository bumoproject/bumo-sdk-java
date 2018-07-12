package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.crypto.Keypair;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.AccountActiviateOperation;
import io.bumo.model.request.Operation.BUSendOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.ValidatorRewardInfo;
import org.junit.Test;

public class ExchangeDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");

    /**
     * 检测连接的节点是否区块同步正常
     */
    @Test
    public void checkBlockStatus(){
        BlockCheckStatusResponse response = sdk.getBlockService().checkStatus();
        System.out.println(response.getResult().getSynchronous());
    }

    /**
     * 生成账户私钥，公钥及地址
     */
    @Test
    public void createAccount(){
        Keypair keypair = Keypair.generator();
        System.out.println(JSON.toJSONString(keypair,true));
    }

    /**
     * 校验账户地址
     */
    @Test
    public void checkAccountAddress(){
        String address = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
        AccountCheckValidRequest accountCheckValidRequest = new AccountCheckValidRequest();
        accountCheckValidRequest.setAddress(address);
        AccountCheckValidResponse accountCheckValidResponse = sdk.getAccountService().checkValid(accountCheckValidRequest);
        if(0 == accountCheckValidResponse.getErrorCode()){
            System.out.println(accountCheckValidResponse.getResult().isValid());
        }else{
            System.out.println(JSON.toJSONString(accountCheckValidResponse,true));
        }
    }

    /**
     * 查询账户信息
     */
    @Test
    public void getAccountInfo(){
        String accountAddress = "buQemmMwmRQY1JkcU7w3nhruo%X5N3j6C29uo";
        AccountGetInfoRequest request = new AccountGetInfoRequest();
        request.setAddress(accountAddress);

        AccountGetInfoResponse response =  sdk.getAccountService().getInfo(request);
        System.out.println(JSON.toJSONString(response,true));

    }

    /**
     * 获取账户BU余额
     */
    @Test
    public void getAccountBalance(){
        String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
        AccountGetBalanceRequest request = new AccountGetBalanceRequest();
        request.setAddress(accountAddress);

        AccountGetBalanceResponse response = sdk.getAccountService().getBalance(request);

        System.out.println(JSON.toJSONString(response,true));
        if(0 == response.getErrorCode()){
            System.out.println("BU余额：" + ToBaseUnit.MO2BU(response.getResult().getBalance().toString()) + "BU");
        }
    }

    /**
     * 获取账户Nonce
     */
    @Test
    public void getAccountNonce(){
        String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
        AccountGetNonceRequest request = new AccountGetNonceRequest();
        request.setAddress(accountAddress);

        AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
        if(0 == response.getErrorCode()){
            System.out.println("账户Nonce:" + response.getResult().getNonce());
        }
    }

    /**
     * 发送一笔BU交易
     * @throws Exception
     */
    @Test
    public void sendBu() throws Exception {
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq"; // 发送方私钥
        String destAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauE";// 接收方账户地址
        Long amount = ToBaseUnit.BU2MO("10.9"); // 发送转出10.9BU给接收方（目标账户）
        Long gasPrice = 1000L; // 固定写 1000L ，单位是MO
        Long feeLimit = ToBaseUnit.BU2MO("0.01");//设置最多费用 0.01BU ，固定填写
        Long nonce = 1L; // 参考getAccountNonce()获取账户Nonce + 1;

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txhash = sendBu(senderPrivateKey,destAddress,amount,nonce,gasPrice,feeLimit);

    }

    /**
     * 根据交易Hash获取交易信息
     */
    @Test
    public void getTxByHash(){
        String txHash = "fba9c3f73705ca3eb865c7ec2959c30bd27534509796fd5b208b0576ab155d95";
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        if(0 == response.getErrorCode()){
            System.out.println(JSON.toJSONString(response,true));
        }else{
            System.out.println("失败\n"+ JSON.toJSONString(response,true));
        }
    }

    /**
     * 探测用户充值
     *
     * 通过解析区块下的交易，来探测用户的充值动作
     *
     */
    @Test
    public void getTransactionOfBolck(){
        Long blockNumber = 617247L;// 第617247区块
        BlockGetTransactionsRequest request = new BlockGetTransactionsRequest();
        request.setBlockNumber(blockNumber);
        BlockGetTransactionsResponse response = sdk.getBlockService().getTransactions(request);
        if(0 == response.getErrorCode()){
            System.out.println(JSON.toJSONString(response,true));
        }else{
            System.out.println("失败\n"+ JSON.toJSONString(response,true));
        }
        // 探测某账户是否有充值BU
        // 解析 transactions[n].transaction.operations[n].pay_coin.dest_address 即可

        // 注意！！！！！
        // operations是数组，有可能有多笔转账操作
    }

    /**
     * 查询最新的区块高度
     */
    @Test
    public void getLastBlockNumber(){
        BlockGetNumberResponse response = sdk.getBlockService().getNumber();
        System.out.println(response.getResult().getHeader());
    }

    /**
     * 调用合约
     */
    @Test
    public void callContract() {
        ContractCallRequest contractCallRequest = new ContractCallRequest();
        contractCallRequest.setCode("\"use strict\";log(undefined);function query() { getBalance(thisAddress); }");
        contractCallRequest.setFeeLimit(1000000000L);
        contractCallRequest.setOptType(2);
        ContractCallResponse contractCallResponse = sdk.getContractService().call(contractCallRequest);
        if (contractCallResponse.getErrorCode() == 0) {
            ContractCallResult contractCallResult = contractCallResponse.getResult();
            JSONObject resultJson = (JSONObject)JSON.toJSON(contractCallResult);
            System.out.println(resultJson);
        } else {
            System.out.println(contractCallResponse.getErrorDesc());
        }
    }

    /**
     * 查询合约信息
     */
    @Test
    public void getContractInfo() {
        ContractGetInfoRequest contractGetInfoRequest = new ContractGetInfoRequest();
        contractGetInfoRequest.setContractAddress("");
    }

    /**
     * 查询账户下合约Token的余额
     */
    @Test
    public void getTokenBalance() {
        TokenGetBalanceRequest tokenGetBalanceRequest = new TokenGetBalanceRequest();
        tokenGetBalanceRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        tokenGetBalanceRequest.setTokenOwner("buQYsbybSh7BNckshKU22Pbx8tgk3FDTda4k");
        TokenGetBalanceResponse tokenGetBalanceResponse = sdk.getTokenService().getBalance(tokenGetBalanceRequest);
        if (tokenGetBalanceResponse.getErrorCode() == 0) {
            TokenGetBalanceResult tokenGetBalanceResult = tokenGetBalanceResponse.getResult();
            System.out.println(tokenGetBalanceResult.getBalance());
        } else {
            System.out.println(tokenGetBalanceResponse.getErrorDesc());
        }
    }

    /**
     * 获取合约Token的精度
     */
    @Test
    public void getTokenDecimals() {
        TokenGetDecimalsRequest tokenGetDecimalsRequest = new TokenGetDecimalsRequest();
        tokenGetDecimalsRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        TokenGetDecimalsResponse tokenGetDecimalsResponse = sdk.getTokenService().getDecimals(tokenGetDecimalsRequest);
        if (tokenGetDecimalsResponse.getErrorCode() == 0) {
            TokenGetDecimalsResult tokenGetDecimalsResult = tokenGetDecimalsResponse.getResult();
            System.out.println(tokenGetDecimalsResult.getDecimals());
        } else {
            System.out.println(tokenGetDecimalsResponse.getErrorDesc());
        }
    }

    /**
     * 获取合约Token总供应量
     */
    @Test
    public void getTokenTotalSupply() {
        TokenGetTotalSupplyRequest tokenGetTotalSupplyRequest = new TokenGetTotalSupplyRequest();
        tokenGetTotalSupplyRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        TokenGetTotalSupplyResponse tokenGetTotalSupplyResponse = sdk.getTokenService().getTotalSupply(tokenGetTotalSupplyRequest);
        if (tokenGetTotalSupplyResponse.getErrorCode() == 0) {
            TokenGetTotalSupplyResult tokenGetTotalSupplyResult = tokenGetTotalSupplyResponse.getResult();
            System.out.println(tokenGetTotalSupplyResult.getTotalSupply());
        } else {
            System.out.println(tokenGetTotalSupplyResponse.getErrorDesc());
        }
    }

    /**
     * 查询合约Token的符号名
     */
    @Test
    public void getTokenSymbol() {
        TokenGetSymbolRequest tokenGetSymbolRequest = new TokenGetSymbolRequest();
        tokenGetSymbolRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        TokenGetSymbolResponse tokenGetSymbolResponse = sdk.getTokenService().getSymbol(tokenGetSymbolRequest);
        if (tokenGetSymbolResponse.getErrorCode() == 0) {
            TokenGetSymbolResult tokenGetSymbolResult = tokenGetSymbolResponse.getResult();
            System.out.println(tokenGetSymbolResult.getSymbol());
        } else {
            System.out.println(tokenGetSymbolResponse.getErrorDesc());
        }
    }

    /**
     * 查询合约Token的名称
     */
    @Test
    public void getTokenName() {
        TokenGetNameRequest tokenGetNameRequest = new TokenGetNameRequest();
        tokenGetNameRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        TokenGetNameResponse tokenGetNameResponse = sdk.getTokenService().getName(tokenGetNameRequest);
        if (tokenGetNameResponse.getErrorCode() == 0) {
            TokenGetNameResult tokenGetNameResult = tokenGetNameResponse.getResult();
            System.out.println(tokenGetNameResult.getName());
        } else {
            System.out.println(tokenGetNameResponse.getErrorDesc());
        }
    }

    /**
     * 查询合约Token的信息
     */
    @Test
    public void getTokenInfo() {
        TokenGetInfoRequest tokenGetInfoRequest = new TokenGetInfoRequest();
        tokenGetInfoRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        TokenGetInfoResponse tokenGetInfoResponse = sdk.getTokenService().getInfo(tokenGetInfoRequest);
        if (tokenGetInfoResponse.getErrorCode() == 0) {
            TokenGetInfoResult tokenGetInfoResult = tokenGetInfoResponse.getResult();
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(tokenGetInfoResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(tokenGetInfoResponse.getErrorDesc());
        }
    }

    /**
     * 查询授权用户的可用的合约Token数量
     */
    @Test
    public void getTokenAllowance() {
        TokenAllowanceRequest tokenAllowanceRequest = new TokenAllowanceRequest();
        tokenAllowanceRequest.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");
        tokenAllowanceRequest.setTokenOwner("buQVU86Jm4FeRW4JcQTD9Rx9NkUkHikYGp6z");
        tokenAllowanceRequest.setSpender("buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo");
        TokenAllowanceResponse tokenAllowanceResponse = sdk.getTokenService().allowance(tokenAllowanceRequest);
        if (tokenAllowanceResponse.getErrorCode() == 0) {
            TokenAllowanceResult tokenAllowanceResult = tokenAllowanceResponse.getResult();
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(tokenAllowanceResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(tokenAllowanceResponse.getErrorDesc());
        }
    }

    /**
     * 查询指定区块高度的区块信息
     */
    @Test
    public void getBlockInfo() {
        BlockGetInfoRequest blockGetInfoRequest = new BlockGetInfoRequest();
        blockGetInfoRequest.setBlockNumber(629743L);
        BlockGetInfoResponse lockGetInfoResponse = sdk.getBlockService().getInfo(blockGetInfoRequest);
        if (lockGetInfoResponse.getErrorCode() == 0) {
            BlockGetInfoResult lockGetInfoResult = lockGetInfoResponse.getResult();
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(lockGetInfoResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(lockGetInfoResponse.getErrorDesc());
        }
    }

    /**
     * 查询最新的区块信息
     */
    @Test
    public void getBlockLatestInfo() {
        BlockGetLatestInfoResponse lockGetLatestInfoResponse = sdk.getBlockService().getLatestInfo();
        if (lockGetLatestInfoResponse.getErrorCode() == 0) {
            BlockGetLatestInfoResult lockGetLatestInfoResult = lockGetLatestInfoResponse.getResult();
            JSONObject tokenLatestInfoJson = (JSONObject)JSON.toJSON(lockGetLatestInfoResult);
            System.out.println(JSON.toJSONString(tokenLatestInfoJson, true));
        } else {
            System.out.println(lockGetLatestInfoResponse.getErrorDesc());
        }
    }

    /**
     * 查询指定区块高度的验证节点信息
     */
    @Test
    public void getBlockValidators() {
        BlockGetValidatorsRequest blockGetValidatorsRequest = new BlockGetValidatorsRequest();
        blockGetValidatorsRequest.setBlockNumber(629743L);
        BlockGetValidatorsResponse lockGetValidatorsResponse = sdk.getBlockService().getValidators(blockGetValidatorsRequest);
        if (lockGetValidatorsResponse.getErrorCode() == 0) {
            BlockGetValidatorsResult lockGetValidatorsResult = lockGetValidatorsResponse.getResult();
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(lockGetValidatorsResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(lockGetValidatorsResponse.getErrorDesc());
        }
    }

    /**
     * 查询最新区块的验证节点信息
     */
    @Test
    public void getLatestBlockValidators() {
        BlockGetLatestValidatorsResponse lockGetLatestValidatorsResponse = sdk.getBlockService().getLatestValidators();
        if (lockGetLatestValidatorsResponse.getErrorCode() == 0) {
            BlockGetLatestValidatorsResult lockGetLatestValidatorsResult = lockGetLatestValidatorsResponse.getResult();
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(lockGetLatestValidatorsResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(lockGetLatestValidatorsResponse.getErrorDesc());
        }
    }

    /**
     * 查询指定区块高度的区块奖励和验证节点奖励
     */
    @Test
    public void getBlockReward() {
        BlockGetRewardRequest blockGetRewardRequest = new BlockGetRewardRequest();
        blockGetRewardRequest.setBlockNumber(629743L);
        BlockGetRewardResponse lockGetRewardResponse = sdk.getBlockService().getReward(blockGetRewardRequest);
        if (lockGetRewardResponse.getErrorCode() == 0) {
            BlockGetRewardResult lockGetRewardResult = lockGetRewardResponse.getResult();
            ValidatorRewardInfo[] validatorRewardInfos = lockGetRewardResult.getRewardResults();
            for (int i = 0; i < validatorRewardInfos.length; i++) {
                System.out.println(validatorRewardInfos[i].getValidator() + ", " + validatorRewardInfos[i].getReward());
            }
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(lockGetRewardResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(lockGetRewardResponse.getErrorDesc());
        }
    }

    /**
     * 查询最新区块奖励和验证节点奖励
     */
    @Test
    public void getLatestBlockReward() {
        BlockGetLatestRewardResponse lockGetLatestRewardResponse = sdk.getBlockService().getLatestReward();
        if (lockGetLatestRewardResponse.getErrorCode() == 0) {
            BlockGetLatestRewardResult lockGetLatestRewardResult = lockGetLatestRewardResponse.getResult();
            ValidatorRewardInfo[] validatorLatestRewardInfos = lockGetLatestRewardResult.getRewardResults();
            for (int i = 0; i < validatorLatestRewardInfos.length; i++) {
                System.out.println(validatorLatestRewardInfos[i].getValidator() + ", " + validatorLatestRewardInfos[i].getReward());
            }
            JSONObject tokenInfoJson = (JSONObject)JSON.toJSON(lockGetLatestRewardResult);
            System.out.println(JSON.toJSONString(tokenInfoJson, true));
        } else {
            System.out.println(lockGetLatestRewardResponse.getErrorDesc());
        }
    }
    /**
     *
     *
     * @param senderPrivateKey 转出方账户私钥
     * @param destAddress 接收方账户地址
     * @param amount 转出BU数量
     * @param senderNonce 转出方nonce ，通过sdk.getAccountService().getNonce(request)获取到的Nonce 加 1；
     * @param gasPrice 燃料单价
     * @param feeLimit 最多支付交易费用
     * @throws Exception
     */
    private String sendBu(String senderPrivateKey,String destAddress,Long amount,Long senderNonce,Long gasPrice,Long feeLimit) throws Exception {

        // 1. 获取交易发送账户地址
        String senderAddresss = getAddressByPrivateKey(senderPrivateKey); // BU发送者账户地址

        // 2. 构建sendBU操作
       BUSendOperation buSendOperation = new BUSendOperation();
       buSendOperation.setSourceAddress(senderAddresss);
       buSendOperation.setDestAddress(destAddress);
       buSendOperation.setAmount(amount);

        // 3. 构建交易

        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(buSendOperation);

        // 4. 获取交易BLob串
        String transactionBlob = null;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        String txHash = transactionBuildBlobResult.getHash();
        transactionBlob = transactionBuildBlobResult.getTransactionBlob();

        // 5. 签名交易BLob(交易发送账户签名交易Blob串)

        String []signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            transactionSignRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);

        // 6. 广播交易

        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) { // 交易广播成功
            System.out.println("交易广播成功，hash=" + transactionSubmitResponse.getResult().getHash());
        }else{
            System.out.println("交易广播失败，hash=" + transactionSubmitResponse.getResult().getHash() + "");
            System.out.println(JSON.toJSONString(transactionSubmitResponse, true));
        }
        return txHash;
    }


    private Long getNonceOfAccount(String senderAddresss){
        AccountGetNonceRequest accountGetNonceRequest = new AccountGetNonceRequest();
        accountGetNonceRequest.setAddress(senderAddresss);

        AccountGetNonceResponse accountGetNonceResponse =  sdk.getAccountService().getNonce(accountGetNonceRequest);
        if (accountGetNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult accountGetNonceResult = accountGetNonceResponse.getResult();
            return accountGetNonceResult.getNonce();
        }
        else {
            return null;
        }
    }

    private String getAddressByPrivateKey(String privatekey) throws Exception {
        String publicKey = PrivateKey.getEncPublicKey(privatekey);
        String address = PrivateKey.getEncAddress(publicKey);
        return address;
    }


}
