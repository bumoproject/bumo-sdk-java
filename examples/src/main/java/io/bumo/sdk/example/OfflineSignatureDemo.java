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
import io.bumo.model.request.TransactionBuildBlobRequest;
import io.bumo.model.request.TransactionParseBlobRequest;
import io.bumo.model.request.TransactionSubmitRequest;
import io.bumo.model.request.operation.BUSendOperation;
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
     * @Description 1. Generating transaction Blob in a network environment
     * @Author riven
     * @Method Online_BuildTransactionBlob
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:10
     */
    @Test
    public void Online_BuildTransactionBlob() {
        // The account to send BU
        String senderAddresss = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // The account to receive BU
        String destAddress = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The amount to be sent
        Long amount = ToBaseUnit.BU2MO("10.9");
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // Transaction initiation account's nonce + 1
        Long nonce = 0L;

        // Build the transaction Blob and return to transactionBlobResult (including transaction Blob and transaction hash)
        String transactionBlobResult = buildTransactionBlob(senderAddresss, nonce, destAddress, amount, feeLimit, gasPrice);
        System.out.println(transactionBlobResult);
    }

    /**
     * @Description 2. Parsing transaction Blob under no network environment
     * @Author riven
     * @Method Offline_ParseBlob
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:11
     */
    @Test
    public void Offline_ParseBlob() {
        // Get transactionBlobResult from 1 (Network Environment)
        String transactionBlobResult = "{\"transaction_blob\":\"0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704\",\"hash\":\"d8fde921219ce265acb51e2cffbe7855e6423f795781e1810595159d9c104522\"}";

        // Parsing the transaction Blob
        JSONObject transaction = parseBlob(transactionBlobResult);
        if (transaction != null) {
            System.out.println("transaction content: " + JSON.toJSONString(transaction, true));
        }
    }

    /**
     * @Description 3. Blob under no network environment
     * @Author riven
     * @Method Offline_SignTransactionBlob
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:12
     */
    @Test
    public void Offline_SignTransactionBlob() {
        // When the transaction Blob is confirmed, it begins to sign a signature

        // Transaction Blob
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";
        // The account private key to send BU
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";

        // Sign transaction
        String signature = signTransaction(transactionBlob, senderPrivateKey);
        if (signature != null) {
            System.out.println("signature: " + JSON.toJSONString(signature, true));
        }
    }

    /**
     * @Description 4. Broadcast transactions in a network environment
     * @Author riven
     * @Method Online_SubmitTransaction
     * @Params []
     * @Return void
     * @Date 2018/7/12 16:13
     */
    @Test
    public void Online_SubmitTransaction() {
        // Get the signature in 3 (no net environment)
        String signature = "{\"signatures\":[{\"public_key\":\"b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477\",\"sign_data\":\"D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A\"}]}";
        String transactionBlob = "0A246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370102118C0843D20E8073A56080712246275516E6E5545425245773268423670574847507A77616E5837643238786B364B566370522C0A24627551426A4A443142534A376E7A41627A6454656E416870466A6D7852564545746D78481080A9E08704";

        // Submit a transaction and return the transaction hash
        String hash = submitTransaction(transactionBlob, signature);
        if (hash != null) {
            System.out.println("transaction hash: " + hash);
        }
        Signature signature1 = new Signature();
        signature1.setSignData("D2B5E3045F2C1B7D363D4F58C1858C30ABBBB0F41E4B2E18AF680553CA9C3689078E215C097086E47A4393BCA715C7A5D2C180D8750F35C6798944F79CC5000A");
        signature1.setPublicKey("b0011765082a9352e04678ef38d38046dc01306edef676547456c0c23e270aaed7ffe9e31477");
    }

    /**
     * @Description Building a generated transaction Blob
     * @Author riven
     * @Method buildTransactionBlob
     * @Params senderAddress: The account to send BU
     * @Params nonce: The account nonce to send BU
     * @Params destAddress: The account address to receive BU
     * @Params amount: The BU amount to be sent，the unit is MO，1 BU = 10^8 MO
     * @Params feeLimit: Fee limit，default 0.01 BU
     * @Params gasPrice: Gas price, default 1000 MO
     * @Return java.lang.String Blob information, including transaction Hash and transaction Blob
     * @Date 2018/7/12 16:10
     */
    private String buildTransactionBlob(String senderAddress, Long nonce, String destAddress, Long amount, Long feeLimit, Long gasPrice) {
        // 1. Get the account nonce to send BU
        AccountGetNonceRequest accountGetNonceRequest = new AccountGetNonceRequest();
        accountGetNonceRequest.setAddress(senderAddress);
        AccountGetNonceResponse accountGetNonceResponse = sdk.getAccountService().getNonce(accountGetNonceRequest);
        if (accountGetNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult accountGetNonceResult = accountGetNonceResponse.getResult();
            nonce = accountGetNonceResult.getNonce() + 1;
        } else {
            System.out.println(accountGetNonceResponse.getErrorDesc());
            return null;
        }

        // 2. Build sendBU
        BUSendOperation buSendOperation = new BUSendOperation();
        buSendOperation.setSourceAddress(senderAddress);
        buSendOperation.setDestAddress(destAddress);
        buSendOperation.setAmount(amount);

        // 3. Init buildBlob request
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddress);
        transactionBuildBlobRequest.setNonce(nonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(buSendOperation);

        // 4. Get transaction blob
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() == 0) {
            TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
            JSONObject BlobResultJson = (JSONObject) JSONObject.toJSON(transactionBuildBlobResult);
            return BlobResultJson.toJSONString();
        } else {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
    }

    /**
     * @Description Parse the transaction Blob
     * @Author riven
     * @Method parseBlob
     * @Params transactionBlobResult: Transaction blob info
     * @Return com.alibaba.fastjson.JSONObject Transaction blob content
     * @Date 2018/7/12 16:19
     */
    private JSONObject parseBlob(String transactionBlobResult) {
        // Verify the correctness of the transaction Blob
        TransactionBuildBlobResult transactionBuildBlobResult = JSONObject.parseObject(transactionBlobResult, TransactionBuildBlobResult.class);
        String transactionHash = transactionBuildBlobResult.getHash();
        String transactionBlob = transactionBuildBlobResult.getTransactionBlob();
        String myGenerateBlobHash = HashUtil.GenerateHashHex(HexFormat.hexToByte(transactionBlob));
        if (!myGenerateBlobHash.equals(transactionHash)) {
            System.out.println("transactionBlob (" + transactionBlob + ") is invalid");
            return null;
        }

        // Build request parameters
        TransactionParseBlobRequest transactionParseBlobRequest = new TransactionParseBlobRequest();
        transactionParseBlobRequest.setBlob(transactionBlob);

        // Parse the content of the transaction
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
     * @Description Sign transaction
     * @Author riven
     * @Method signTransaction
     * @Params transactionBlob: Transaction blob
     * @Params senderPrivateKey: The account private key to send BU
     * @Return java.lang.String Signature results, including signature information and public key
     * @Date 2018/7/12 16:20
     */
    private String signTransaction(String transactionBlob, String senderPrivateKey) {
        try {
            // Signing a transaction and generating a public key
            PrivateKey privateKey = new PrivateKey(senderPrivateKey);
            byte[] signDataBytes = privateKey.sign(HexFormat.hexToByte(transactionBlob));
            String signData = HexFormat.byteToHex(signDataBytes);
            String publicKey = privateKey.getEncPublicKey();

            // Build signature
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
     * @Description Broadcast transaction
     * @Author riven
     * @Method submitTransaction
     * @Params transactionBlob: transaction Blob
     * @Params signature: Signature results, including signature information and public key
     * @Return java.lang.String
     * @Date 2018/7/12 16:21
     */
    private String submitTransaction(String transactionBlob, String signature) {
        TransactionSignResult transactionSignResult = JSONObject.parseObject(signature, TransactionSignResult.class);
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResult.getSignatures());

        // Broadcast transaction
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
