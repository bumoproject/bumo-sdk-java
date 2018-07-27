package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.model.request.AccountGetNonceRequest;
import io.bumo.model.request.Operation.BUSendOperation;
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
     * 获取账户nonce值
     */
    @Test
    public void getAccountNonce() {
        // 初始化请求参数
        String senderAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
        getNonceRequest.setAddress(senderAddress);

        // 调用getNonce接口
        AccountGetNonceResponse getNonceResponse = sdk.getAccountService().getNonce(getNonceRequest);

        // 赋值nonce
        if (getNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult result = getNonceResponse.getResult();
            System.out.println("nonce: " + result.getNonce());
        } else {
            System.out.println("error" + getNonceResponse.getErrorDesc());
        }
    }

    /*
     * 构建操作
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
     * 构建交易Blob
     */
    @Test
    public void buildTransactionBlob() {
        // 上面的获取账户nonce值接口得到的nonce
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

        // 上面的构建操作接口得到的Operation
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");
        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        // 初始化变量
        Long gasPrice = 1000L;
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // 初始化请求参数
        TransactionBuildBlobRequest buildBlobRequest = new TransactionBuildBlobRequest();
        buildBlobRequest.setSourceAddress(senderAddress);
        buildBlobRequest.setNonce(nonce + 1);
        buildBlobRequest.setFeeLimit(feeLimit);
        buildBlobRequest.setGasPrice(gasPrice);
        buildBlobRequest.addOperation(operation);
        // 调用buildBlob接口
        TransactionBuildBlobResponse buildBlobResponse = sdk.getTransactionService().buildBlob(buildBlobRequest);
        if (buildBlobResponse.getErrorCode() == 0) {
            TransactionBuildBlobResult result = buildBlobResponse.getResult();
            System.out.println("txHash: " + result.getHash() + ", blob: " + result.getTransactionBlob());
        } else {
            System.out.println("error: " + buildBlobResponse.getErrorDesc());
        }
    }

    /*
     * 签名交易
     */
    @Test
    public void signTransaction() {
        // 上面的获取账户nonce值接口得到的nonce
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

        // 上面的构建操作接口得到的Operation
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");
        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        // 上面的构建交易Blob接口得到的transactionBlob
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

        // 初始化请求参数
        String senderPrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        String[] signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest signRequest = new TransactionSignRequest();
        signRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            signRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        // 调用sign接口
        TransactionSignResponse signResponse = sdk.getTransactionService().sign(signRequest);
        if (signResponse.getErrorCode() == 0) {
            TransactionSignResult result = signResponse.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + signResponse.getErrorDesc());
        }
    }

    /*
     * 广播交易
     */
    @Test
    public void broadcastTransaction() {
        // 上面的获取账户nonce值接口得到的nonce
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

        // 上面的构建操作接口得到的Operation
        String destAddress = "buQsurH1M4rjLkfjzkxR9KXJ6jSu2r9xBNEw";
        Long amount = ToBaseUnit.BU2MO("10.9");
        BUSendOperation operation = new BUSendOperation();
        operation.setSourceAddress(senderAddress);
        operation.setDestAddress(destAddress);
        operation.setAmount(amount);

        // 上面的构建交易Blob接口得到的transactionBlob
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

        // 上面的签名接口得到的signResult
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

        // 初始化请求参数
        TransactionSubmitRequest submitRequest = new TransactionSubmitRequest();
        submitRequest.setTransactionBlob(transactionBlob);
        submitRequest.setSignatures(signResult.getSignatures());
        // 调用submit接口
        TransactionSubmitResponse response = sdk.getTransactionService().submit(submitRequest);
        if (0 == response.getErrorCode()) {
            System.out.println("交易广播成功，hash=" + response.getResult().getHash());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }
}
