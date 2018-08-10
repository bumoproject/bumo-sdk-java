package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.TransactionBuildBlobRequest;
import io.bumo.model.request.TransactionGetInfoRequest;
import io.bumo.model.request.TransactionSignRequest;
import io.bumo.model.request.TransactionSubmitRequest;
import io.bumo.model.request.operation.AssetIssueOperation;
import io.bumo.model.request.operation.AssetSendOperation;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.response.TransactionBuildBlobResponse;
import io.bumo.model.response.TransactionGetInfoResponse;
import io.bumo.model.response.TransactionSignResponse;
import io.bumo.model.response.TransactionSubmitResponse;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/8/7 14:26
 */
public class Atp10TokenDemo {
    SDK sdk = SDK.getInstance("http://127.0.0.1:36002");

    /**
     * Issue the unlimited apt1.0 token successfully
     * Unlimited requirement: The totalSupply must be smaller than and equal to 0
     */
    @Test
    public void IssueUnlimitedAtp10Token() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The token code
        String code = "TXT";
        // The token total supply number
        Long totalSupply = 0L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 10L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);

        // 2. Build asset operation
        AssetIssueOperation operation = new AssetIssueOperation();
        operation.setSourceAddress(issuerAddresss);
        operation.setCode(code);
        operation.setAmount(nowSupply);
        operation.setMetadata(metadata);

        // 3. If this is a atp 1.0 token, you must set transaction metadata like this
        JSONObject atp10Json = new JSONObject();
        atp10Json.put("atp", "1.0");
        atp10Json.put("code", code);
        atp10Json.put("issuer", issuerAddresss);
        atp10Json.put("totalSupply", totalSupply);
        atp10Json.put("description", description);
        String tranMetadata = atp10Json.toJSONString();

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(issuerPrivateKey, issuerAddresss, operation, nonce, gasPrice, feeLimit, tranMetadata);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Issue the limited apt1.0 token successfully
     * Limited requirement: The totalSupply must be bigger than 0
     */
    @Test
    public void IssuelimitedAtp10Token() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The token code
        String code = "TXT";
        // The token total supply number, and must cannot be smaller than nowSupply
        Long totalSupply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 50.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.01");
        // Transaction initiation account's Nonce + 1
        Long nonce = 3L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);

        // 2. Build asset operation
        AssetIssueOperation operation = new AssetIssueOperation();
        operation.setSourceAddress(issuerAddresss);
        operation.setCode(code);
        operation.setAmount(nowSupply);
        operation.setMetadata(metadata);

        // 3. If this is a atp 1.0 token, you must set transaction metadata like this
        JSONObject atp10Json = new JSONObject();
        atp10Json.put("atp", "1.0");
        atp10Json.put("code", code);
        atp10Json.put("issuer", issuerAddresss);
        atp10Json.put("totalSupply", totalSupply);
        atp10Json.put("description", description);
        String tranMetadata = atp10Json.toJSONString();

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(issuerPrivateKey, issuerAddresss, operation, nonce, gasPrice, feeLimit, tranMetadata);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Send apt 1.0 token to other account
     */
    @Test
    public void SendAtp10Token() {
        // The account private key to send atp1.0 token
        String senderPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account that issued the atp 1.0 token
        String issuerAddress = "";
        // The account to receive atp 1.0 token
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        // The token code
        String code = "TXT";
        // The token amount to be sent
        Long amount = 1000000000L;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // Transaction initiation account's Nonce + 1
        Long nonce = 3L;

        // 1. Get the account address to send this transaction
        String senderAddresss = getAddressByPrivateKey(senderPrivateKey);

        // 2. Build asset operation
        AssetSendOperation operation = new AssetSendOperation();
        operation.setSourceAddress(senderAddresss);
        operation.setDestAddress(destAddress);
        operation.setCode(code);
        operation.setAmount(amount);
        operation.setIssuer(senderAddresss);
        operation.setMetadata(metadata);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(senderPrivateKey, senderAddresss, operation, nonce, gasPrice, feeLimit, "");
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Get apt 1.0 token info
     */
    @Test
    public void GetAtp10Info() {
        // Init request
        String IssueAtp10TokenHash = "8d0bf9585d19d059aefbe93ade4f4a108c7dc97fc65e98fcdb2175f7514e4fa1";
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(IssueAtp10TokenHash);

        // Call getInfo
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        if (response.getErrorCode() == 0) {
            String metadata = response.getResult().getTransactions()[0].getTransaction().getMetadata();
            System.out.println(JSON.toJSONString(JSON.parse(metadata), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * @param senderPrivateKey The account private key to start transaction
     * @param senderAddresss   The account address to start transaction
     * @param operation        operation
     * @param senderNonce      Transaction initiation account's Nonce
     * @param gasPrice         Gas price
     * @param feeLimit         fee limit
     * @return java.lang.String transaction hash
     * @author riven
     */
    private String submitTransaction(String senderPrivateKey, String senderAddresss, BaseOperation operation, Long senderNonce, Long gasPrice, Long feeLimit, String transMetadata) {
        // 1. Build transaction
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(operation);
        transactionBuildBlobRequest.setMetadata(transMetadata);

        // 2. Build transaction BLob
        String transactionBlob;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        String txHash = transactionBuildBlobResult.getHash();
        transactionBlob = transactionBuildBlobResult.getTransactionBlob();

        // 3. Sign transaction BLob
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

        // 4. Broadcast transaction
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            System.out.println("Success，hash=" + transactionSubmitResponse.getResult().getHash());
        } else {
            System.out.println("Failure，hash=" + transactionSubmitResponse.getResult().getHash() + "");
            System.out.println(JSON.toJSONString(transactionSubmitResponse, true));
        }
        return txHash;
    }

    /**
     * @param privatekey private key
     * @return java.lang.String Account address
     * @author riven
     */
    private String getAddressByPrivateKey(String privatekey) {
        String publicKey = PrivateKey.getEncPublicKey(privatekey);
        String address = PrivateKey.getEncAddress(publicKey);
        return address;
    }
}
