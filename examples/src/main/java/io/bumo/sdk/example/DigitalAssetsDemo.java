package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.crypto.Keypair;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.Signature;
import io.bumo.model.response.result.data.Signer;
import io.bumo.model.response.result.data.TransactionFees;
import org.junit.Test;

/**
 * @author riven
 * @date 2018/7/15 14:32
 */

public class DigitalAssetsDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");

    /**
     * 检测连接的节点是否区块同步正常
     */
    @Test
    public void checkBlockStatus() {
        BlockCheckStatusResponse response = sdk.getBlockService().checkStatus();
        System.out.println(response.getResult().getSynchronous());
    }

    /**
     * 生成账户私钥，公钥及地址
     */
    @Test
    public void createAccount() {
        Keypair keypair = Keypair.generator();
        System.out.println(JSON.toJSONString(keypair, true));
    }

    /**
     * 校验账户地址
     */
    @Test
    public void checkAccountAddress() {
        // 初始化请求参数
        String address = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
        AccountCheckValidRequest request = new AccountCheckValidRequest();
        request.setAddress(address);

        // 调用checkValid
        AccountCheckValidResponse response = sdk.getAccountService().checkValid(request);
        if (0 == response.getErrorCode()) {
            System.out.println(response.getResult().isValid());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询账户信息
     */
    @Test
    public void getAccountInfo() {
        // 初始化请求参数
        String accountAddress = "buQemmMwmRQY1JkcU7w3nhruoX5N3j6C29uo";
        AccountGetInfoRequest request = new AccountGetInfoRequest();
        request.setAddress(accountAddress);

        // 调用getInfo接口
        AccountGetInfoResponse response = sdk.getAccountService().getInfo(request);
        if (response.getErrorCode() == 0) {
            AccountGetInfoResult result = response.getResult();
            System.out.println("账户信息: \n" + JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 获取账户Nonce
     */
    @Test
    public void getAccountNonce() {
        // 初始化请求参数
        String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
        AccountGetNonceRequest request = new AccountGetNonceRequest();
        request.setAddress(accountAddress);

        // 调用getNonce接口
        AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
        if (0 == response.getErrorCode()) {
            System.out.println("账户nonce:" + response.getResult().getNonce());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 获取账户BU余额
     */
    @Test
    public void getAccountBalance() {
        // 初始化请求参数
        String accountAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauEjf";
        AccountGetBalanceRequest request = new AccountGetBalanceRequest();
        request.setAddress(accountAddress);

        // 调用getBalance接口
        AccountGetBalanceResponse response = sdk.getAccountService().getBalance(request);
        if (0 == response.getErrorCode()) {
            AccountGetBalanceResult result = response.getResult();
            System.out.println("BU余额：" + ToBaseUnit.MO2BU(result.getBalance().toString()) + " BU");
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询指定账户下的所有资产
     */
    @Test
    public void getAccountAssets() {
        // 初始化请求参数
        AccountGetAssetsRequest request = new AccountGetAssetsRequest();
        request.setAddress("buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw");

        // 调用getAssets接口
        AccountGetAssetsResponse response = sdk.getAccountService().getAssets(request);
        if (response.getErrorCode() == 0) {
            AccountGetAssetsResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询账户Metadata
     */
    @Test
    public void getAccountMetadata() {
        // 初始化请求参数
        String accountAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        AccountGetMetadataRequest request = new AccountGetMetadataRequest();
        request.setAddress(accountAddress);
        request.setKey("20180704");

        // 调用getMetadata接口
        AccountGetMetadataResponse response = sdk.getAccountService().getMetadata(request);
        if (response.getErrorCode() == 0) {
            AccountGetMetadataResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询指定账户的指定资产
     */
    @Test
    public void getAssetInfo() {
        // 初始化请求参数
        AssetGetInfoRequest request = new AssetGetInfoRequest();
        request.setAddress("buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw");
        request.setIssuer("buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH");
        request.setCode("HNC");

        // 调用getInfo消息
        AssetGetInfoResponse response = sdk.getAssetService().getInfo(request);
        if (response.getErrorCode() == 0) {
            AssetGetInfoResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 验证合约Token的有效性
     */
    @Test
    public void checkTokenValid() {
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
    }

    /**
     * 查询授权用户的可用的合约Token数量
     */
    @Test
    public void getTokenAllowance() {
        // 初始化请求参数
        TokenAllowanceRequest request = new TokenAllowanceRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");
        request.setTokenOwner("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");
        request.setSpender("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");

        // 调用allowance接口
        TokenAllowanceResponse response = sdk.getTokenService().allowance(request);
        if (response.getErrorCode() == 0) {
            TokenAllowanceResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询合约Token的信息
     */
    @Test
    public void getTokenInfo() {
        // 初始化请求参数
        TokenGetInfoRequest request = new TokenGetInfoRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // 调用getInfo接口
        TokenGetInfoResponse response = sdk.getTokenService().getInfo(request);
        if (response.getErrorCode() == 0) {
            TokenGetInfoResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询合约Token的名称
     */
    @Test
    public void getTokenName() {
        // 初始化请求参数
        TokenGetNameRequest request = new TokenGetNameRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // 调用getName接口
        TokenGetNameResponse response = sdk.getTokenService().getName(request);
        if (response.getErrorCode() == 0) {
            TokenGetNameResult result = response.getResult();
            System.out.println(result.getName());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询合约Token的符号名
     */
    @Test
    public void getTokenSymbol() {
        // 初始化请求参数
        TokenGetSymbolRequest request = new TokenGetSymbolRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // 调用getSymbol接口
        TokenGetSymbolResponse response = sdk.getTokenService().getSymbol(request);
        if (response.getErrorCode() == 0) {
            TokenGetSymbolResult result = response.getResult();
            System.out.println(result.getSymbol());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 获取合约Token的精度
     */
    @Test
    public void getTokenDecimals() {
        // 初始化请求参数
        TokenGetDecimalsRequest request = new TokenGetDecimalsRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // 调用getDecimals接口
        TokenGetDecimalsResponse response = sdk.getTokenService().getDecimals(request);
        if (response.getErrorCode() == 0) {
            TokenGetDecimalsResult result = response.getResult();
            System.out.println(result.getDecimals());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 获取合约Token总供应量
     */
    @Test
    public void getTokenTotalSupply() {
        // 初始化请求参数
        TokenGetTotalSupplyRequest request = new TokenGetTotalSupplyRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // 调用getTotalSupply接口
        TokenGetTotalSupplyResponse response = sdk.getTokenService().getTotalSupply(request);
        if (response.getErrorCode() == 0) {
            TokenGetTotalSupplyResult result = response.getResult();
            System.out.println(result.getTotalSupply());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询账户下合约Token的余额
     */
    @Test
    public void getTokenBalance() {
        // 初始化请求参数
        TokenGetBalanceRequest request = new TokenGetBalanceRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");
        request.setTokenOwner("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");

        // 调用getBalance接口
        TokenGetBalanceResponse response = sdk.getTokenService().getBalance(request);
        if (response.getErrorCode() == 0) {
            TokenGetBalanceResult result = response.getResult();
            System.out.println(result.getBalance());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 验证合约账户的有效性
     */
    @Test
    public void checkContractValid() {
        // 初始化请求参数
        ContractCheckValidRequest request = new ContractCheckValidRequest();
        request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

        // 调用checkValid接口
        ContractCheckValidResponse response = sdk.getContractService().checkValid(request);
        if (response.getErrorCode() == 0) {
            ContractCheckValidResult result = response.getResult();
            System.out.println(result.getValid());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询合约信息
     */
    @Test
    public void getContractInfo() {
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
    }

    /**
     * 调用合约
     */
    @Test
    public void callContract() {
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
    }

    /**
     * 序列化交易，生成交易Blob
     */
    @Test
    public void buildTransactionBlob() {
        // 初始化变量
        String senderAddresss = "buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea";
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
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
        request.setNonce(nonce);
        request.setFeeLimit(feeLimit);
        request.setGasPrice(gasPrice);
        request.addOperation(operation);

        // 调用buildBlob接口
        TransactionBuildBlobResponse response = sdk.getTransactionService().buildBlob(request);
        if (response.getErrorCode() == 0) {
            TransactionBuildBlobResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 评估交易费用
     */
    @Test
    public void evaluateTxFees() throws Exception {
        // 初始化变量
        // 发送方私钥
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 接收方账户地址
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        // 发送转出10.9BU给接收方（目标账户）
        Long amount = ToBaseUnit.BU2MO("10.9");
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        //设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1;
        Long nonce = 42L;

        // 评估费用
        TransactionFees transactionFees = evaluateFees(senderPrivateKey, destAddress, amount, nonce, gasPrice, feeLimit);
        System.out.println(JSON.toJSONString(transactionFees, true));
    }

    /**
     * 签名交易
     */
    @Test
    public void signTransaction() {
        // 初始化请求参数
        String issuePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        String[] signerPrivateKeyArr = {issuePrivateKey};
        String transactionBlob = "123";
        TransactionSignRequest request = new TransactionSignRequest();
        request.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            request.addPrivateKey(signerPrivateKeyArr[i]);
        }
        TransactionSignResponse response = sdk.getTransactionService().sign(request);
        if (0 == response.getErrorCode()) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 提交交易
     */
    @Test
    public void submitTransaction() {
        // 初始化请求参数
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
        Signature signature = new Signature();
        signature.setSignData("D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A");
        signature.setPublicKey("b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477");
        TransactionSubmitRequest request = new TransactionSubmitRequest();
        request.setTransactionBlob(transactionBlob);
        request.addSignature(signature);

        // 调用submit接口
        TransactionSubmitResponse response = sdk.getTransactionService().submit(request);
        if (0 == response.getErrorCode()) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 根据交易Hash获取交易信息
     */
    @Test
    public void getTxByHash() {
        String txHash = "1653f54fbba1134f7e35acee49592a7c29384da10f2f629c9a214f6e54747705";
        // 初始化请求参数
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);

        // 调用getInfo接口
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        if (response.getErrorCode() == 0) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 探测用户充值
     * <p>
     * 通过解析区块下的交易，来探测用户的充值动作
     */
    @Test
    public void getTransactionOfBolck() {
        // 初始化请求参数
        Long blockNumber = 617247L;
        BlockGetTransactionsRequest request = new BlockGetTransactionsRequest();
        request.setBlockNumber(blockNumber);

        // 调用getTransactions接口
        BlockGetTransactionsResponse response = sdk.getBlockService().getTransactions(request);
        if (0 == response.getErrorCode()) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询最新的区块高度
     */
    @Test
    public void getLastBlockNumber() {
        // 调用getNumber接口
        BlockGetNumberResponse response = sdk.getBlockService().getNumber();
        if (0 == response.getErrorCode()) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 查询指定区块高度的区块信息
     */
    @Test
    public void getBlockInfo() {
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
    }

    /**
     * 查询最新的区块信息
     */
    @Test
    public void getBlockLatestInfo() {
        // 调用getLatestInfo接口
        BlockGetLatestInfoResponse response = sdk.getBlockService().getLatestInfo();
        if (response.getErrorCode() == 0) {
            BlockGetLatestInfoResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * R
     * 查询指定区块高度的验证节点信息
     */
    @Test
    public void getBlockValidators() {
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
    }

    /**
     * 查询最新区块的验证节点信息
     */
    @Test
    public void getLatestBlockValidators() {
        BlockGetLatestValidatorsResponse lockGetLatestValidatorsResponse = sdk.getBlockService().getLatestValidators();
        if (lockGetLatestValidatorsResponse.getErrorCode() == 0) {
            BlockGetLatestValidatorsResult lockGetLatestValidatorsResult = lockGetLatestValidatorsResponse.getResult();
            System.out.println(JSON.toJSONString(lockGetLatestValidatorsResult, true));
        } else {
            System.out.println("error: " + lockGetLatestValidatorsResponse.getErrorDesc());
        }
    }

    /**
     * 查询指定区块高度的区块奖励和验证节点奖励
     */
    @Test
    public void getBlockReward() {
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
    }

    /**
     * 查询最新区块奖励和验证节点奖励
     */
    @Test
    public void getLatestBlockReward() {
        // 调用getLatestReward接口
        BlockGetLatestRewardResponse response = sdk.getBlockService().getLatestReward();
        if (response.getErrorCode() == 0) {
            BlockGetLatestRewardResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 获取指定区块的费用标准
     */
    @Test
    public void getBlockFees() {
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
    }

    /**
     * 获取最新区块的费用标准
     */
    @Test
    public void getBlockLatestFees() {
        // 调用getLatestFees接口
        BlockGetLatestFeesResponse response = sdk.getBlockService().getLatestFees();
        if (response.getErrorCode() == 0) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 激活账户
     */
    @Test
    public void activateAccount() {
        // 发起激活操作的账户
        String activatePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        Long initBalance = ToBaseUnit.BU2MO("0.1");
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 1L;

        Keypair keypair = Keypair.generator();
        // 待激活账户地址
        String destAccount = keypair.getAddress();

        // 1. 获取交易发送账户地址
        String activateAddresss = getAddressByPrivateKey(activatePrivateKey);

        // 2. 构建activateAccount操作
        AccountActivateOperation operation = new AccountActivateOperation();
        operation.setSourceAddress(activateAddresss);
        operation.setDestAddress(destAccount);
        operation.setInitBalance(initBalance);
        operation.setMetadata("activate account");

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(activatePrivateKey, activateAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }


    /**
     * 设置metadata
     */
    @Test
    public void setAccountMetadata() {
        // 初始化变量
        // 账户私钥
        String accountPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 发送转出10.9BU给接收方（目标账户）
        String key = "test  ";
        String value = "asdfasdfa";
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        //设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 45L;

        // 1. 获取交易发送账户地址
        String accountAddresss = getAddressByPrivateKey(accountPrivateKey);

        // 2. 构建setMetadata操作
        AccountSetMetadataOperation operation = new AccountSetMetadataOperation();
        operation.setSourceAddress(accountAddresss);
        //operation.setKey(key);
        operation.setValue(value);
        operation.setValue("你是外国人吧？");

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(accountPrivateKey, accountAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 设置权限
     */
    @Test
    public void setAccountPrivilege() {
        // 初始化变量
        // 设置权限的账户私钥
        String accountPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        //设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 61L;

        // 1. 获取交易发送账户地址
        String accountAddresss = getAddressByPrivateKey(accountPrivateKey);

        // 2. 构建setPrivilege操作
        AccountSetPrivilegeOperation operation = new AccountSetPrivilegeOperation();
        operation.setSourceAddress(accountAddresss);
        Signer signer = new Signer();
        signer.setAddress("buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw");
        signer.setWeight(0L);
        operation.addSigner(signer);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(accountPrivateKey, accountAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 发行资产
     */
    @Test
    public void issueAsset() {
        // 初始化变量
        // 资产发行方私钥
        String issuePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 待发行的资产编号
        String assetCode = "TST";
        // 待发行的资产数量
        Long assetAmount = 10000000000000L;
        // 备注，必须是16进制字符串
        String metadata = HexFormat.byteToHex("issue TST".getBytes());
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用 50.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("50.01");
        // 交易发起账户Nonce + 1
        Long nonce = 1L;

        // 1. 获取交易发送账户地址
        String issueAddresss = getAddressByPrivateKey(issuePrivateKey);

        // 2. 构建issueAsset操作
        AssetIssueOperation assetIssueOperation = new AssetIssueOperation();
        assetIssueOperation.setSourceAddress(issueAddresss);
        assetIssueOperation.setCode(assetCode);
        assetIssueOperation.setAmount(assetAmount);
        assetIssueOperation.setMetadata(metadata);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(issuePrivateKey, issueAddresss, assetIssueOperation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 转移资产
     */
    @Test
    public void sendAsset() {
        // 初始化变量
        // 发送方私钥
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 接收方账户地址
        String destAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauE";
        // 待发送和资产编号
        String assetCode = "TST";
        // 资产发行方地址
        String assetIssuer = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 发送转出10.9BU给接收方（目标账户）
        Long amount = ToBaseUnit.BU2MO("10.9");
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 1L;

        // 1. 获取交易发送账户地址
        String senderAddresss = getAddressByPrivateKey(senderPrivateKey);

        // 2. 构建sendAsset操作
        AssetSendOperation assetSendOperation = new AssetSendOperation();
        assetSendOperation.setSourceAddress(senderAddresss);
        assetSendOperation.setDestAddress(destAddress);
        assetSendOperation.setCode(assetCode);
        assetSendOperation.setIssuer(assetIssuer);
        assetSendOperation.setAmount(amount);
        assetSendOperation.setMetadata("send asset");

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(senderPrivateKey, senderAddresss, assetSendOperation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 发送一笔BU交易
     */
    @Test
    public void sendBu() {
        // 初始化变量
        // 发送方私钥
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 接收方账户地址
        String destAddress = "buQswSaKDACkrFsnP1wcVsLAUzXQsemauE";
        // 发送转出10.9BU给接收方（目标账户）
        Long amount = ToBaseUnit.BU2MO("0.01");
        Long gasPrice = 1000L; // 固定写 1000L ，单位是MO
        //设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 1L;

        // 1. 获取交易发送账户地址
        String senderAddresss = getAddressByPrivateKey(senderPrivateKey);

        // 2. 构建sendBU操作
        BUSendOperation buSendOperation = new BUSendOperation();
        buSendOperation.setSourceAddress(senderAddresss);
        buSendOperation.setDestAddress(destAddress);
        buSendOperation.setAmount(amount);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(senderPrivateKey, senderAddresss, buSendOperation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }

    }

    /**
     * 发行Token
     */
    @Test
    public void issueToken() {
        // 初始化变量
        // 创建合约Token方私钥
        String sourcePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 待创建合约Token的初始化资产
        Long initBalance = 100000000L;
        // token数值的精度
        Integer decimals = 8;
        // token名称
        String name = "TST";
        // token的供应量，不带精度，实际是1000000000 * (10 ^ decimals)
        String supply = "10000000000";
        // token的符号
        String symbol = "TST";
        // 交易发起账户Nonce + 1
        Long nonce = 36L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用10.08BU，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("10.08");

        // 1. 获取创建合约Token方地址
        String sourceAddress = getAddressByPrivateKey(sourcePrivateKey);

        // 2. 构建issueToken操作
        TokenIssueOperation operation = new TokenIssueOperation();
        operation.setSourceAddress(sourceAddress);
        operation.setDecimals(decimals);
        operation.setInitBalance(initBalance);
        operation.setName(name);
        operation.setSupply(supply);
        operation.setSymbol(symbol);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(sourcePrivateKey, sourceAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 分配Token
     */
    @Test
    public void assignToken() {
        // 初始化变量
        // 触发合约方私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 合约token代码所在的合约账户地址
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // 待分配token的账户
        String destAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 待分配token的数量
        String amount = "1000000";
        // 交易发起账户Nonce + 1
        Long nonce = 38L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用0.02BU，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. 触发合约方地址
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. 构建assignToken操作
        TokenAssignOperation operation = new TokenAssignOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setDestAddress(destAddress);
        operation.setTokenAmount(amount);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 转移Token
     */
    @Test
    public void transferToken() {
        // 初始化变量
        // 触发合约方私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 合约token代码所在的合约账户地址
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // 待转移token的账户
        String destAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // 待转移token的数量
        String amount = "1000000";
        // 交易发起账户Nonce + 1
        Long nonce = 38L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用0.02BU，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. 触发合约方地址
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. 构建transferToken操作
        TokenTransferOperation operation = new TokenTransferOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setDestAddress(destAddress);
        operation.setTokenAmount(amount);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 从指定账户转移Token
     */
    @Test
    public void transferTokenFrom() {
        // 初始化变量
        // 触发合约方私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 合约token代码所在的合约账户地址
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // 待发送token的账户
        String fromAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 待接收token的账户
        String destAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // 待发送token的数量
        String amount = "100000";
        // 交易发起账户Nonce + 1
        Long nonce = 54L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用0.02BU，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. 触发合约方地址
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. 构建transferToken操作
        TokenTransferFromOperation operation = new TokenTransferFromOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setFromAddress(fromAddress);
        operation.setDestAddress(destAddress);
        operation.setTokenAmount(amount);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 授权转移指定数量的Token
     */
    @Test
    public void approveToken() {
        // 初始化变量
        // 触发合约方私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 合约token代码所在的合约账户地址
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // 待分配token的账户
        String spender = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 授权的数量
        String amount = "1000000";
        // 交易发起账户Nonce + 1
        Long nonce = 50L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用0.02BU，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. 触发合约方地址
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. 构建transferToken操作
        TokenApproveOperation operation = new TokenApproveOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setSpender(spender);
        operation.setTokenAmount(amount);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 修改Token的拥有者
     */
    @Test
    public void changeTokenOwner() {
        // 初始化变量
        // 触发合约方私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 合约token代码所在的合约账户地址
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // token的拥有者账户
        String tokenOwner = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 交易发起账户Nonce + 1
        Long nonce = 55L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用0.02BU，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. 触发合约方地址
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. 构建changeOwner操作
        TokenChangeOwnerOperation operation = new TokenChangeOwnerOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setTokenOwner(tokenOwner);
        operation.setMetadata("");

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 创建合约
     */
    @Test
    public void createContract() {
        // 待创建合约的账户私钥
        String createPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 合约账户初始化资产，单位MO，1 BU = 10^8 MO
        Long initBalance = ToBaseUnit.BU2MO("0.1");
        // 合约代码
        String payload = "\"use strict\";function init(initInput){return;} function main(input){getBalance(thisAddress);} function query(input) {return;}";
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用 10.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("10.01");
        // 交易发起账户Nonce + 1
        Long nonce = 56L;
        // 合约init函数入参
        String initInput = "";

        // 1. 获取交易发送账户地址
        String createAddresss = getAddressByPrivateKey(createPrivateKey);

        // 2. 构建activateAccount操作
        ContractCreateOperation operation = new ContractCreateOperation();
        operation.setSourceAddress(createAddresss);
        operation.setInitBalance(initBalance);
        operation.setPayload(payload);
        operation.setInitInput(initInput);
        operation.setMetadata("create contract");

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(createPrivateKey, createAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 转移资产，并触发合约
     */
    @Test
    public void invokeContractByAsset() {
        // 初始化变量
        // 触发合约账户私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 接收方账户地址
        String destAddress = "buQd7e8E5XMa7Yg6FJe4BeLWfqpGmurxzZ5F";
        // 待发送和资产编号
        String assetCode = "TST";
        // 资产发行方地址
        String assetIssuer = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 0表示仅触发合约
        Long amount = 0L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 57L;
        // 合约main函数入参
        String input = "";

        // 1. 获取交易发送账户地址
        String invokeAddresss = getAddressByPrivateKey(invokePrivateKey);

        // 2. 构建sendAsset操作
        ContractInvokeByAssetOperation operation = new ContractInvokeByAssetOperation();
        operation.setSourceAddress(invokeAddresss);
        operation.setContractAddress(destAddress);
        operation.setCode(assetCode);
        operation.setIssuer(assetIssuer);
        operation.setAssetAmount(amount);
        operation.setInput(input);
        operation.setMetadata("send asset");

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 发送BU，并触发合约
     */
    @Test
    public void invokeContractByBU() {
        // 初始化变量
        // 发送方私钥
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 接收方账户地址
        String destAddress = "buQd7e8E5XMa7Yg6FJe4BeLWfqpGmurxzZ5F";
        // 0表示仅触发合约
        Long amount = 0L;
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        //设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 58L;
        String input = "";

        // 1. 获取交易发送账户地址
        String invokeAddresss = getAddressByPrivateKey(invokePrivateKey);

        // 2. 发送BU，并触发交易
        ContractInvokeByBUOperation operation = new ContractInvokeByBUOperation();
        operation.setSourceAddress(invokeAddresss);
        operation.setContractAddress(destAddress);
        operation.setBuAmount(amount);
        operation.setInput(input);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(invokePrivateKey, invokeAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 向BU区块链写日志
     */
    @Test
    public void createLog() {
        // 初始化变量
        // 创建日志方私钥
        String createPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // 日志标题
        String topic = "test";
        // 日志内容
        String data = "this is not a error";
        // 必须是16进制字符串
        String metadata = HexFormat.byteToHex("create log".getBytes());
        // 固定写 1000L ，单位是MO
        Long gasPrice = 1000L;
        // 设置最多费用 0.01BU ，固定填写
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 交易发起账户Nonce + 1
        Long nonce = 59L;

        // 1. 获取交易发送账户地址
        String createAddresss = getAddressByPrivateKey(createPrivateKey);

        // 构建createLog操作
        LogCreateOperation operation = new LogCreateOperation();
        operation.setSourceAddress(createAddresss);
        operation.setTopic(topic);
        operation.addData(data);
        operation.setMetadata(metadata);

        // 记录txhash ，以便后续再次确认交易真实结果
        // 推荐5个区块后再次通过txhash再次调用`根据交易Hash获取交易信息`(参考示例：getTxByHash()）来确认交易终态结果
        String txHash = submitTransaction(createPrivateKey, createAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * @param senderPrivateKey 交易发送方私钥
     * @param senderAddresss   交易发送方地址
     * @param operation        操作
     * @param senderNonce      交易发送方的账户nonce
     * @param gasPrice         交易燃料单价
     * @param feeLimit         交易最低手续费
     * @return java.lang.String 交易hash
     * @author riven
     */
    private String submitTransaction(String senderPrivateKey, String senderAddresss, BaseOperation operation, Long senderNonce, Long gasPrice, Long feeLimit) {
        // 3. 构建交易
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(operation);
        transactionBuildBlobRequest.setMetadata("abc");

        // 4. 获取交易BLob串
        String transactionBlob;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        String txHash = transactionBuildBlobResult.getHash();
        transactionBlob = transactionBuildBlobResult.getTransactionBlob();

        // 5. 签名交易BLob(交易发送账户签名交易Blob串)
        String[] signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            transactionSignRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionSignResponse.getErrorDesc());
            return null;
        }

        // 6. 广播交易
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            System.out.println("交易广播成功，hash=" + transactionSubmitResponse.getResult().getHash());
        } else {
            System.out.println("交易广播失败，hash=" + transactionSubmitResponse.getResult().getHash() + "");
            System.out.println(JSON.toJSONString(transactionSubmitResponse, true));
        }
        return txHash;
    }

    /**
     * @param senderPrivateKey 交易发送方私钥
     * @param destAddress      接收方地址
     * @param amount           BU数量
     * @param nonce            交易发送方nonce
     * @param gasPrice         交易燃料单价
     * @param feeLimit         交易最低手续费
     * @return io.bumo.model.response.result.data.TransactionFees 交易费用
     * @author riven
     */
    private TransactionFees evaluateFees(String senderPrivateKey, String destAddress, Long amount, Long nonce, Long gasPrice, Long feeLimit) throws Exception {
        // 1. 获取交易发送账户地址
        String senderAddresss = getAddressByPrivateKey(senderPrivateKey);

        // 2. 构建sendBU操作
        BUSendOperation buSendOperation = new BUSendOperation();
        buSendOperation.setSourceAddress(senderAddresss);
        buSendOperation.setDestAddress(destAddress);
        buSendOperation.setAmount(amount);
        buSendOperation.setMetadata("616263");

        // 3. 评估交易费用
        TransactionEvaluateFeeRequest request = new TransactionEvaluateFeeRequest();
        request.addOperation(buSendOperation);
        request.setSourceAddress(senderAddresss);
        request.setNonce(nonce);
        request.setSignatureNumber(1);
        request.setMetadata("616263");

        TransactionEvaluateFeeResponse response = sdk.getTransactionService().evaluateFee(request);
        if (response.getErrorCode() == 0) {
            return response.getResult().getTxs()[0].getTransactionEnv().getTransactionFees();
        } else {
            System.out.println("error: " + response.getErrorDesc());
            return null;
        }
    }

    /**
     * @param privatekey 私钥
     * @return java.lang.String 账户地址
     * @author riven
     */
    private String getAddressByPrivateKey(String privatekey) {
        String publicKey = PrivateKey.getEncPublicKey(privatekey);
        String address = PrivateKey.getEncAddress(publicKey);
        return address;
    }
}
