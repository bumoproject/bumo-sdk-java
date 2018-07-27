package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.exception.EncException;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.utils.hash.HashUtil;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.model.request.AccountGetNonceRequest;
import io.bumo.model.request.Operation.BUSendOperation;
import io.bumo.model.request.TransactionBuildBlobRequest;
import io.bumo.model.request.TransactionParseBlobRequest;
import io.bumo.model.request.TransactionSubmitRequest;
import io.bumo.model.response.AccountGetNonceResponse;
import io.bumo.model.response.TransactionBuildBlobResponse;
import io.bumo.model.response.TransactionParseBlobResponse;
import io.bumo.model.response.TransactionSubmitResponse;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.Signature;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/7/12 11:38
 */
public class OfflineSignatureDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");
    /**
     * @Description 1. 有网环境下，生成交易Blob
     * @Author riven
     * @Method Online_BuildTransactionBlob
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:10
     */
    @Test
    public void Online_BuildTransactionBlob() {
        String senderAddresss = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp"; // 发送方账户地址
        String destAddress = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH"; // 接收方账户地址
        Long amount = ToBaseUnit.BU2MO("10.9"); // 发送转出10.9BU给接收方（目标账户）
        Long gasPrice = 1000L; // 固定写 1000L ，单位是MO
        Long feeLimit = ToBaseUnit.BU2MO("0.01"); //设置最多费用 0.01BU ，固定填写
        Long nonce = 0L; // 发送方账户Nonce

        // 构建交易Blob，返回transactionBlobResult（包含交易Blob和交易hash）
        String transactionBlobResult = buildTransactionBlob(senderAddresss, nonce, destAddress, amount, feeLimit, gasPrice);
        System.out.println(transactionBlobResult);
    }
    
    /**
     * @Description 2. 无网环境下，解析交易Blob
     * @Author riven
     * @Method Offline_ParseBlob
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:11
     */
    @Test
    public void Offline_ParseBlob() {
        // 从 1(有网环境) 中拷贝到的 transactionBlobResult
        String transactionBlobResult = "{\"transaction_blob\":\"0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704\",\"hash\":\"d8fde921219ce265acb51e2cffbe7855e6423f795781e1810595159d9c104522\"}";

        // 解析交易Blob
        JSONObject transaction = parseBlob(transactionBlobResult);
        if (transaction != null) {
            System.out.println("transaction content: " + JSON.toJSONString(transaction, true));
        }
    }

    /**
     * @Description 3. 无网环境下，签名交易Blob
     * @Author riven
     * @Method Offline_SignTransactionBlob
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:12
     */
    @Test
    public void Offline_SignTransactionBlob() {
        // 确认交易Blob无误后，开始进行签名

        // 交易Blob
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
        // 发送方私钥
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";

        // 签名交易
        String signature = signTransaction(transactionBlob, senderPrivateKey);
        if (signature != null) {
            System.out.println("signature: " + JSON.toJSONString(signature, true));
        }
    }

    /**
     * @Description 4. 有网环境下，广播交易
     * @Author riven
     * @Method Online_SubmitTransaction
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:13
     */
    @Test
    public void Online_SubmitTransaction() {
        // 拿到 3（无网环境） 中的signature
        String signature = "{\"signatures\":[{\"public_key\":\"b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477\",\"sign_data\":\"D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A\"}]}";
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";

        // 提交交易，并返回交易hash
        String hash = submitTransaction(transactionBlob, signature);
        if (hash != null) {
            System.out.println("transaction hash: " + hash);
        }
        Signature signature1 = new Signature();
        signature1.setSignData("D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A");
        signature1.setPublicKey("b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477");
    }

    /**
     * @Description 构建生成交易Blob
     * @Author riven
     * @Method buildTransactionBlob
     * @Params senderAddresss: 发送方账户地址
     *         nonce: 发送方账户Nonce
     *         destAddress: 接收方账户地址
     *         amount: 待发送给接收方的BU数量，单位是MO，1 BU = 10^8 MO
     *         feeLimit: 交易费用，默认 0.01 BU
     *         gasPrice: 交易打包费用, 默认 1000 MO
     * @Return java.lang.String 交易Blob信息，包含交易Hash和交易Blob
     * @Date 2018/7/12 16:10
     */
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
    
    /**
     * @Description 解析交易Blob
     * @Author riven
     * @Method parseBlob
     * @Params transactionBlobResult: 交易Blob信息
     * @Return com.alibaba.fastjson.JSONObject 交易Blob内容
     * @Date 2018/7/12 16:19
     */
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

    /**
     * @Description 签名交易
     * @Author riven
     * @Method signTransaction
     * @Params transactionBlob: 交易Blob
     *         senderPrivateKey: 发送方账户私钥
     * @Return java.lang.String 签名结果，包括签名信息和公钥
     * @Date 2018/7/12 16:20
     */
    private String signTransaction(String transactionBlob, String senderPrivateKey) {
        try {
            // 签名交易，并生成公钥
            PrivateKey privateKey = new PrivateKey(senderPrivateKey);
            byte[] signDataBytes = privateKey.sign(HexFormat.hexToByte(transactionBlob));
            String signData = HexFormat.byteToHex(signDataBytes);
            String publicKey = privateKey.getEncPublicKey();

            // 组装signature
            JSONObject signatureJson = new JSONObject();
            JSONArray signatures = new JSONArray();
            JSONObject signature = new JSONObject();
            signature.put("sign_data", signData);
            signature.put("public_key", publicKey);
            signatures.add(signature);
            signatureJson.put("signatures", signatures);

            return signatureJson.toJSONString();
        } catch (EncException encException) {
            encException.printStackTrace();
        }
        return null;
    }

    /**
     * @Description 广播交易
     * @Author riven
     * @Method submitTransaction
     * @Params transactionBlob: 交易Blob
     *         signature: 签名结果，包括签名信息和公钥
     * @Return java.lang.String
     * @Date 2018/7/12 16:21
     */
    private String submitTransaction(String transactionBlob, String signature) {
        TransactionSignResult transactionSignResult = JSONObject.parseObject(signature, TransactionSignResult.class);
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResult.getSignatures());

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
