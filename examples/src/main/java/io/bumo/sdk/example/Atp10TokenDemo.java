package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.crypto.Keypair;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/8/7 14:26
 */
public class Atp10TokenDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");

    /**
     * Issue the unlimited apt1.0 token successfully
     * Unlimited requirement: The totalSupply must be equal to 0
     */
    @Test
    public void issueUnlimitedAtp10Token() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The token name
        String name = "TXT";
        // The token code
        String code = "TXT";
        // The apt token version
        String version = "1.0";
        // The apt token icon
        String icon = "";
        // The token total supply number
        Long totalSupply = 0L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token description
        // The token decimals
        Integer decimals = 0;
        String description = "test unlimited issuance of apt1.0 token";
        // The operation note
        String operationMetadata = "test the unlimited issuance of apt1.0 token";
        // The transaction note
        String transMetadata = "test the unlimited issuance of apt1.0 token";
        // Transaction initiation account's Nonce + 1
        Long nonce = 114L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);

        // 2. Build asset operation
        AssetIssueOperation assetIssueOperation = new AssetIssueOperation();
        assetIssueOperation.setSourceAddress(issuerAddresss);
        assetIssueOperation.setCode(code);
        assetIssueOperation.setAmount(nowSupply);
        assetIssueOperation.setMetadata(operationMetadata);

        // 3. If this is an atp 1.0 token, you must set metadata like this
        JSONObject atp10Json = new JSONObject();
        atp10Json.put("name", name);
        atp10Json.put("code", code);
        atp10Json.put("description", description);
        atp10Json.put("decimals", decimals);
        atp10Json.put("totalSupply", totalSupply);
        atp10Json.put("icon", icon);
        atp10Json.put("version", version);

        String key = "asset_property_" + code;
        String value = atp10Json.toJSONString();

        // 4. Build setMetadata
        AccountSetMetadataOperation accountSetMetadataOperation = new AccountSetMetadataOperation();
        accountSetMetadataOperation.setSourceAddress(issuerAddresss);
        accountSetMetadataOperation.setKey(key);
        accountSetMetadataOperation.setValue(value);
        accountSetMetadataOperation.setMetadata(operationMetadata);

        BaseOperation[] operations = {assetIssueOperation, accountSetMetadataOperation};
        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(issuerPrivateKey, issuerAddresss, operations, nonce, gasPrice, feeLimit, transMetadata);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Issue the limited apt1.0 token successfully
     * Limited requirement: The totalSupply must be bigger than 0
     */
    @Test
    public void issuelimitedAtp10Token() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The token name
        String name = "TXT";
        // The token code
        String code = "TXT";
        // The apt token version
        String version = "1.0";
        // The apt token icon
        String icon = "";
        // The token total supply number
        Long totalSupply = 100000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token description
        // The token decimals
        Integer decimals = 0;
        String description = "test unlimited issuance of apt1.0 token";
        // The operation note
        String operationMetadata = "test the unlimited issuance of apt1.0 token";
        // The transaction note
        String transMetadata = "test the unlimited issuance of apt1.0 token";
        // Transaction initiation account's Nonce + 1
        Long nonce = 114L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);

        // 2. Build asset operation
        AssetIssueOperation assetIssueOperation = new AssetIssueOperation();
        assetIssueOperation.setSourceAddress(issuerAddresss);
        assetIssueOperation.setCode(code);
        assetIssueOperation.setAmount(nowSupply);
        assetIssueOperation.setMetadata(operationMetadata);

        // 3. If this is an atp 1.0 token, you must set metadata like this
        JSONObject atp10Json = new JSONObject();
        atp10Json.put("name", name);
        atp10Json.put("code", code);
        atp10Json.put("description", description);
        atp10Json.put("decimals", decimals);
        atp10Json.put("totalSupply", totalSupply);
        atp10Json.put("icon", icon);
        atp10Json.put("version", version);

        String key = "asset_property_" + code;
        String value = atp10Json.toJSONString();

        // 4. Build setMetadata
        AccountSetMetadataOperation accountSetMetadataOperation = new AccountSetMetadataOperation();
        accountSetMetadataOperation.setSourceAddress(issuerAddresss);
        accountSetMetadataOperation.setKey(key);
        accountSetMetadataOperation.setValue(value);
        accountSetMetadataOperation.setMetadata(operationMetadata);

        BaseOperation[] operations = {assetIssueOperation, accountSetMetadataOperation};
        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(issuerPrivateKey, issuerAddresss, operations, nonce, gasPrice, feeLimit, transMetadata);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Send apt 1.0 token to other account
     */
    @Test
    public void sendAtp10Token() {
        // The account private key to send atp1.0 token
        String senderPrivateKey = "privbvTuL1k8z27i9eyBrFDUvAVVCSxKeLtzjMMZEqimFwbNchnejS81";
        // The account that issued the atp 1.0 token
        String issuerAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        // The account to receive atp 1.0 token
        String destAddress = "buQc77ZYKT2dYZ5pzdsfGdGjGMJGGR9ZVZ1p";
        // The token code
        String code = "TXT";
        // The token amount to be sent
        Long amount = 1000000000L;
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // Transaction initiation account's Nonce + 1
        Long nonce = 19L;

        // 1. Get the account address to send this transaction
        String senderAddresss = getAddressByPrivateKey(senderPrivateKey);

        // 2. Check whether the destination account is activated
        if (!checkAccountActivated(destAddress)) {
            // if it is not activated, we should activate it,
            // and the initBalance of the destination account should be 0.2 BU,
            // and nonce should add 1
            if (!activateAccount(senderPrivateKey, destAddress, ToBaseUnit.BU2MO("0.2"), nonce)) {
                return;
            }
            nonce += 1;
        }

        // 3. Build asset operation
        AssetSendOperation operation = new AssetSendOperation();
        operation.setSourceAddress(senderAddresss);
        operation.setDestAddress(destAddress);
        operation.setCode(code);
        operation.setAmount(amount);
        operation.setIssuer(issuerAddress);
        operation.setMetadata(metadata);

        BaseOperation[] operations = {operation};
        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(senderPrivateKey, senderAddresss, operations, nonce, gasPrice, feeLimit, "");
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Check whether an account is activated.\
     */
    private boolean checkAccountActivated(String address) {
        AccountCheckActivatedRequst request = new AccountCheckActivatedRequst();
        request.setAddress(address);

        AccountCheckActivatedResponse response = sdk.getAccountService().checkActivated(request);
        if (response.getErrorCode() != 0) {
            return false;
        }
        return response.getResult().getIsActivated();
    }

    /**
     * Activate a new account
     */
    private boolean activateAccount(String activatePrivateKey, String destAddress, Long initBalance, Long nonce) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.01");

        // 1. Get the account address to send this transaction
        String activateAddresss = getAddressByPrivateKey(activatePrivateKey);

        // 2. Build activateAccount
        AccountActivateOperation operation = new AccountActivateOperation();
        operation.setSourceAddress(activateAddresss);
        operation.setDestAddress(destAddress);
        operation.setInitBalance(initBalance);
        operation.setMetadata("activate account");

        BaseOperation[] operations = {operation};
        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(activatePrivateKey, activateAddresss, operations, nonce, gasPrice, feeLimit, null);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param senderPrivateKey The account private key to start transaction
     * @param senderAddresss   The account address to start transaction
     * @param operations       operations
     * @param senderNonce      Transaction initiation account's Nonce
     * @param gasPrice         Gas price
     * @param feeLimit         fee limit
     * @return java.lang.String transaction hash
     * @author riven
     */
    private String submitTransaction(String senderPrivateKey, String senderAddresss, BaseOperation[] operations, Long senderNonce, Long gasPrice, Long feeLimit, String transMetadata) {
        // 1. Build transaction
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        for (int i = 0; i < operations.length; i++) {
            transactionBuildBlobRequest.addOperation(operations[i]);
        }

        transactionBuildBlobRequest.setMetadata(transMetadata);

        // 2. Build transaction BLob
        String transactionBlob;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
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
        String Hash = null;
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            Hash = transactionSubmitResponse.getResult().getHash();
        } else {
            System.out.println(JSON.toJSONString(transactionSubmitResponse, true));
        }
        return Hash;
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
