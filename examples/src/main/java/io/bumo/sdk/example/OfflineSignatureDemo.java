package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.utils.hash.HashUtil;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.BUSendOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/7/12 11:38
 */
public class OfflineSignatureDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");
    /**
     * 1. 有网环境下，生成交易Blob
     * 操作如下： (1). 获取交易发送账户的nonce
     *          (2). 构建sendBU操作
     *          (3). 构建交易
     *          (4). 获取交易BLob串
     */
    @Test
    public void Online_BuildTransactionBlob() {
        String senderAddresss = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";                     // 发送方私钥
        String destAddress = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";                          // 接收方账户地址
        Long amount = ToBaseUnit.BU2MO("10.9");                                      // 发送转出10.9BU给接收方（目标账户）
        Long gasPrice = 1000L;                                                                // 固定写 1000L ，单位是MO
        Long feeLimit = ToBaseUnit.BU2MO("0.01");                                    //设置最多费用 0.01BU ，固定填写
        Long nonce = 32L;                                                                     // 参考getAccountNonce()获取账户Nonce + 1;

        // 构建交易Blob，返回transactionBlobResult（包含交易Blob和交易hash）
        String transactionBlobResult = buildTransactionBlob(senderAddresss, nonce, destAddress, amount, feeLimit, gasPrice);
        System.out.println(transactionBlobResult);
    }

    /**
     * 2. 无网环境下，解析交易Blob
     * 操作如下： 解析出交易内容
     */
    @Test
    public void Offline_ParseBlob() {
        // 从 1（有网环境） 中拷贝到的 transactionBlobResult
        String transactionBlobResult = "{\"transaction_blob\":\"0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102018C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704\",\"hash\":\"de655ab4136af2be9dc4ca345672728d762145722d0446d758bd420e1f4e0787\"}";

        // 解析交易Blob
        JSONObject transaction = parseBlob(transactionBlobResult);
        if (transaction != null) {
            System.out.println("transaction content: " + JSON.toJSONString(transaction, true));
        }
    }

    /**
     * 3. 无网环境下，签名交易Blob
     * 操作如下： 签名交易
     */
    @Test
    public void Offline_SignTransactionBlob() {
        // 确认交易Blob无误后，开始进行签名
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102018C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";

        // 签名交易
        String signature = signTransaction(transactionBlob, senderPrivateKey);
        if (signature != null) {
            System.out.println("signature: " + JSON.toJSONString(signature, true));
        }
    }

    /**
     * 4. 有网环境下，广播交易
     * 操作如下： 广播交易
     */
    @Test
    public void Online_SubmitTransaction() {
        // 拿到 3（无网环境） 中的signature
        String signature = "{\"signatures\":[{\"public_key\":\"b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477\",\"sign_data\":\"20FB5467E4283359B1226B3699993349C58FBBE2ECC8702B56534C355ED650184027EC931038BD49422EF2C4EDF6E4F406DA340D946BF6A79860C2DE19735804\"}]}";
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102018C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";

        // 提交交易，并返回交易hash
        String hash = submitTransaction(transactionBlob, signature);
        if (hash != null) {
            System.out.println("transaction hash: " + hash);
        }
    }

    private String buildTransactionBlob(String senderAddresss, Long nonce, String destAddress, Long amount, Long feeLimit, Long gasPrice) {
        // 1. 获取交易发送账户的nonce
        AccountGetNonceRequest accountGetNonceRequest = new AccountGetNonceRequest();
        accountGetNonceRequest.setAddress(senderAddresss);
        AccountGetNonceResponse accountGetNonceResponse = sdk.getAccountService().getNonce(accountGetNonceRequest);
        if (accountGetNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult accountGetNonceResult = accountGetNonceResponse.getResult();
            nonce = accountGetNonceResult.getNonce() + 1;                                      // 获取账户Nonce + 1
        } else {
            System.out.println(accountGetNonceResponse.getErrorDesc());
            return null;
        }

        // 2. 构建sendBU操作
        BUSendOperation buSendOperation = new BUSendOperation();
        buSendOperation.setSourceAddress(senderAddresss);
        buSendOperation.setDestAddress(destAddress);
        buSendOperation.setAmount(amount);

        // 3. 构建交易
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(nonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(buSendOperation);

        // 4. 获取交易BLob串
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() == 0) {
            TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
            JSONObject BlobResultJson = (JSONObject)JSONObject.toJSON(transactionBuildBlobResult);
            return BlobResultJson.toJSONString();
        } else {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
    }

    private JSONObject parseBlob(String transactionBlobResult) {
        // 验证交易Blob的正确性
        TransactionBuildBlobResult transactionBuildBlobResult = JSONObject.parseObject(transactionBlobResult, TransactionBuildBlobResult.class);
        String transactionHash = transactionBuildBlobResult.getHash();
        String transactionBlob = transactionBuildBlobResult.getTransactionBlob();
        String myGenerateBlobHash = HashUtil.GenerateHashHex(HexFormat.hexToByte(transactionBlob));
        if (!myGenerateBlobHash.equals(transactionHash)) {
            System.out.println("transactionBlob (" + transactionBlob + ") is invalid");
            return null;
        }

        // 构建request参数
        TransactionParseBlobRequest transactionParseBlobRequest = new TransactionParseBlobRequest();
        transactionParseBlobRequest.setBlob(transactionBlob);

        // 解析出交易内容
        TransactionParseBlobResponse transactionParseBlobResponse = sdk.getTransactionService().parseBlob(transactionParseBlobRequest);
        if (transactionParseBlobResponse.getErrorCode() == 0) {
            TransactionParseBlobResult transactionParseBlobResult = transactionParseBlobResponse.getResult();
            JSONObject parseBlobResultJson = (JSONObject) JSON.toJSON(transactionParseBlobResult);
            return parseBlobResultJson;
        } else {
            System.out.println("error: " + transactionParseBlobResponse.getErrorDesc());
            return null;
        }
    }

    private String signTransaction(String transactionBlob, String senderPrivateKey) {
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        transactionSignRequest.addPrivateKey(senderPrivateKey);

        // 签名交易
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() == 0) {
            TransactionSignResult transactionSignResult = transactionSignResponse.getResult();
            JSONObject signResultJson = (JSONObject) JSON.toJSON(transactionSignResult);
            return signResultJson.toJSONString();
        } else {
            System.out.println("error: " + transactionSignResponse.getErrorDesc());
            return null;
        }
    }

    private String submitTransaction(String transactionBlob, String signature) {
        TransactionSignResult transactionSignResult = JSONObject.parseObject(signature, TransactionSignResult.class);
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSignResult.setSignatures(transactionSignResult.getSignatures());

        // 提交交易
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (transactionSubmitResponse.getErrorCode() == 0) {
            TransactionSubmitResult transactionSubmitResult = transactionSubmitResponse.getResult();
            return transactionSubmitResult.getHash();
        } else {
            System.out.println("error: " + transactionSubmitResponse.getErrorDesc());
            return null;
        }
    }
}
