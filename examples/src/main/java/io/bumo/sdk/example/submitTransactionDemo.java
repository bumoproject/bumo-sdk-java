package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.model.request.AccountGetNonceRequest;
import io.bumo.model.request.operation.BUSendOperation;
import io.bumo.model.request.TransactionBuildBlobRequest;
import io.bumo.model.request.TransactionSignRequest;
import io.bumo.model.request.TransactionSubmitRequest;
import io.bumo.model.response.AccountGetNonceResponse;
import io.bumo.model.response.TransactionBuildBlobResponse;
import io.bumo.model.response.TransactionSignResponse;
import io.bumo.model.response.TransactionSubmitResponse;
import io.bumo.model.response.result.AccountGetNonceResult;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import io.bumo.model.response.result.TransactionSignResult;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/7/26 08:19
 */
public class submitTransactionDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");

    /*
     * Get account nonce to start transaction
     */
    @Test
    public void getAccountNonce() {
        // Init request
        String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
        getNonceRequest.setAddress(senderAddress);

        // Call getNonce
        AccountGetNonceResponse getNonceResponse = sdk.getAccountService().getNonce(getNonceRequest);

        // Get nonce
        if (getNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult result = getNonceResponse.getResult();
            System.out.println("nonce: " + result.getNonce());
        } else {
            System.out.println("error" + getNonceResponse.getErrorDesc());
        }
    }

    /*
     * Build operation
     */
    @Test
    public void buildOperation() {
        String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");

        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        System.out.println(JSON.toJSONString(operation, true));
    }

    /*
     * Build transaction blob
     */
    @Test
    public void buildTransactionBlob() {
        // Get the nonce above getAccountNonce interface
        String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
        getNonceRequest.setAddress(senderAddress);
        AccountGetNonceResponse getNonceResponse = sdk.getAccountService().getNonce(getNonceRequest);
        Long nonce;
        if (getNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult result = getNonceResponse.getResult();
            nonce = result.getNonce();
        } else {
            System.out.println("error" + getNonceResponse.getErrorDesc());
            return;
        }

        // Get the operation above buildOperation interface
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");
        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        // Init variable
        Long gasPrice = 1000L;
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // Init request
        TransactionBuildBlobRequest buildBlobRequest = new TransactionBuildBlobRequest();
        buildBlobRequest.setSourceAddress(senderAddress);
        buildBlobRequest.setNonce(nonce + 1);
        buildBlobRequest.setFeeLimit(feeLimit);
        buildBlobRequest.setGasPrice(gasPrice);
        buildBlobRequest.addOperation(operation);
        // Build buildBlob
        TransactionBuildBlobResponse buildBlobResponse = sdk.getTransactionService().buildBlob(buildBlobRequest);
        if (buildBlobResponse.getErrorCode() == 0) {
            TransactionBuildBlobResult result = buildBlobResponse.getResult();
            System.out.println("txHash: " + result.getHash() + ", blob: " + result.getTransactionBlob());
        } else {
            System.out.println("error: " + buildBlobResponse.getErrorDesc());
        }
    }

    /*
     * Sign transaction
     */
    @Test
    public void signTransaction() {
        // Get the nonce above getAccountNonce interface
        String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
        getNonceRequest.setAddress(senderAddress);
        AccountGetNonceResponse getNonceResponse = sdk.getAccountService().getNonce(getNonceRequest);
        Long nonce;
        if (getNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult result = getNonceResponse.getResult();
            nonce = result.getNonce();
        } else {
            System.out.println("error" + getNonceResponse.getErrorDesc());
            return;
        }

        // Get the operation above buildOperation interface
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");
        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        // Get the transaction blob above buildTransactionBlob interface
        Long gasPrice = 1000L;
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        TransactionBuildBlobRequest buildBlobRequest = new TransactionBuildBlobRequest();
        buildBlobRequest.setSourceAddress(senderAddress);
        buildBlobRequest.setNonce(nonce + 1);
        buildBlobRequest.setFeeLimit(feeLimit);
        buildBlobRequest.setGasPrice(gasPrice);
        buildBlobRequest.addOperation(operation);
        String transactionBlob;
        TransactionBuildBlobResponse buildBlobResponse = sdk.getTransactionService().buildBlob(buildBlobRequest);
        if (buildBlobResponse.getErrorCode() == 0) {
            TransactionBuildBlobResult result = buildBlobResponse.getResult();
            transactionBlob = result.getTransactionBlob();
        } else {
            System.out.println("error: " + buildBlobResponse.getErrorDesc());
            return;
        }

        // Init request
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        String[] signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest signRequest = new TransactionSignRequest();
        signRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            signRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        // Call sign
        TransactionSignResponse signResponse = sdk.getTransactionService().sign(signRequest);
        if (signResponse.getErrorCode() == 0) {
            TransactionSignResult result = signResponse.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + signResponse.getErrorDesc());
        }
    }

    /*
     * Broadcast transaction
     */
    @Test
    public void broadcastTransaction() {
        // Get the nonce above getAccountNonce interface
        String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
        getNonceRequest.setAddress(senderAddress);
        AccountGetNonceResponse getNonceResponse = sdk.getAccountService().getNonce(getNonceRequest);
        Long nonce;
        if (getNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult result = getNonceResponse.getResult();
            nonce = result.getNonce();
        } else {
            System.out.println("error" + getNonceResponse.getErrorDesc());
            return;
        }

        // Get the operation above buildOperation interface
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");
        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        // Get the transaction blob above buildTransactionBlob interface
        Long gasPrice = 1000L;
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        TransactionBuildBlobRequest buildBlobRequest = new TransactionBuildBlobRequest();
        buildBlobRequest.setSourceAddress(senderAddress);
        buildBlobRequest.setNonce(nonce + 1);
        buildBlobRequest.setFeeLimit(feeLimit);
        buildBlobRequest.setGasPrice(gasPrice);
        buildBlobRequest.addOperation(operation);
        String transactionBlob;
        TransactionBuildBlobResponse buildBlobResponse = sdk.getTransactionService().buildBlob(buildBlobRequest);
        if (buildBlobResponse.getErrorCode() == 0) {
            TransactionBuildBlobResult result = buildBlobResponse.getResult();
            transactionBlob = result.getTransactionBlob();
        } else {
            System.out.println("error: " + buildBlobResponse.getErrorDesc());
            return;
        }

        // Get sign result above signTransaction interface
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        String[] signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest signRequest = new TransactionSignRequest();
        signRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            signRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        TransactionSignResult signResult;
        TransactionSignResponse signResponse = sdk.getTransactionService().sign(signRequest);
        if (signResponse.getErrorCode() == 0) {
            signResult = signResponse.getResult();
        } else {
            System.out.println("error: " + signResponse.getErrorDesc());
            return;
        }

        // Init request
        TransactionSubmitRequest submitRequest = new TransactionSubmitRequest();
        submitRequest.setTransactionBlob(transactionBlob);
        submitRequest.setSignatures(signResult.getSignatures());
        // Call submit
        TransactionSubmitResponse response = sdk.getTransactionService().submit(submitRequest);
        if (0 == response.getErrorCode()) {
            System.out.println("Success，hash=" + response.getResult().getHash());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }
}
