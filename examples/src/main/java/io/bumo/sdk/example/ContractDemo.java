package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.operation.ContractCreateOperation;
import io.bumo.model.request.operation.ContractInvokeByAssetOperation;
import io.bumo.model.request.operation.ContractInvokeByBUOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.ContractCallResult;
import io.bumo.model.response.result.ContractCheckValidResult;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import org.junit.Test;

import java.util.UUID;

/**
 * @Author riven
 * @Date 2018/8/6 20:52
 */
public class ContractDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumo.io:16002");

    @Test
    public void get24UUID(){
        long test = (2019L - 1700L) * 24L * 3600L * 1000L * 1000L;
        System.out.println(test);
    }

    /**
     * Check whether the contract is valid
     */
    @Test
    public void checkContractValid() {
        // Init request
        ContractCheckValidRequest request = new ContractCheckValidRequest();
        request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

        // Call checkValid
        ContractCheckValidResponse response = sdk.getContractService().checkValid(request);
        if (response.getErrorCode() == 0) {
            ContractCheckValidResult result = response.getResult();
            System.out.println(result.getValid());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Get contract info
     */
    @Test
    public void getContractInfo() {
        // Init request
        ContractGetInfoRequest request = new ContractGetInfoRequest();
        request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

        // Call getInfo
        ContractGetInfoResponse response = sdk.getContractService().getInfo(request);
        if (response.getErrorCode() == 0) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Get contract address
     */
    @Test
    public void getContractAddress() {
        // Init request
        String hash = "44246c5ba1b8b835a5cbc29bdc9454cdb9a9d049870e41227f2dcfbcf7a07689";
        ContractGetAddressRequest request = new ContractGetAddressRequest();
        request.setHash(hash);

        // Call getAddress
        ContractGetAddressResponse response = sdk.getContractService().getAddress(request);
        if (response.getErrorCode() == 0) {
            System.out.println(JSON.toJSONString(response.getResult(), true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * call contract
     */
    @Test
    public void callContract() {
        // Init variable
        // Contract address
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // Spender address
        String spender = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // Amount
        String amount = "1000000";

        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "approve");
        JSONObject params = new JSONObject();
        params.put("spender", spender);
        params.put("value", amount);
        input.put("params", params);

        // Init request
        ContractCallRequest request = new ContractCallRequest();
        //request.setContractAddress(contractAddress);
        request.setCode("\"use strict\";function unique(arr){return(arr.filter((v,i,a)=>a.indexOf(v)===i));}function subSame(arr1,arr2){return arr1.filter((v,i,a)=>arr2.indexOf(v)===-1);}function init(bar){const arr1=[1,2,3,4,5,6];const arr2=[2,2,4,4,1,1,6,6,5];const arrSub=subSame(arr1,arr2);Chain.store(\"test\",JSON.stringify(arrSub));}function main(input){let i=0;for(i=0;i<50;i+=1){Chain.store(`test${i}`,`${i}`);}}function query(input){return Chain.load(\"test\");}");
        request.setFeeLimit(1000000000L);
        request.setOptType(1);
        //request.setInput(input.toJSONString());

        // Call call
        ContractCallResponse response = sdk.getContractService().call(request);
        if (response.getErrorCode() == 0) {
            ContractCallResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * 创建合约
     */
    @Test
    public void createContract() {
        // The account private key to create contract
        String createPrivateKey = "privbUdwf6xV1d5Jvkcakuz8T8nfFn4U7d5s55VUbwmi79DPxqNWSD1n";
        // Contract account initialization BU，the unit is MO，and 1 BU = 10^8 MO
        Long initBalance = 100000000L;//ToBaseUnit.BU2MO("0.1");
        // Contract code
        String payload = "\n          \"use strict\";\n          function init(bar)\n          {\n            /*init whatever you want*/\n            return;\n          }\n          \n          function main(input)\n          {\n            let para = JSON.parse(input);\n            if (para.do_foo)\n            {\n              let x = {\n                \'hello\' : \'world\'\n              };\n            }\n          }\n          \n          function query(input)\n          { \n            return input;\n          }\n        ";
        // The fixed write 1000L ，the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 10.01BU
        Long feeLimit = 1015076000L;//ToBaseUnit.BU2MO("10.01");
        // Transaction initiation account's Nonce + 1
        Long nonce = 18L;
        // Contract init function entry
        String initInput = "";

        // 1. Get the account address to send this transaction
        String createAddresss = getAddressByPrivateKey(createPrivateKey);

        // 2. Build activateAccount operation
        ContractCreateOperation operation = new ContractCreateOperation();
        operation.setSourceAddress(createAddresss);
        operation.setInitBalance(initBalance);
        operation.setPayload(payload);
        operation.setInitInput(initInput);
        operation.setMetadata("create contract");

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(createPrivateKey, createAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Send asset and trigger contract
     */
    @Test
    public void invokeContractByAsset() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The account to receive the assets
        String destAddress = "buQd7e8E5XMa7Yg6FJe4BeLWfqpGmurxzZ5F";
        // The asset code to be sent
        String assetCode = "TST";
        // The account address to issue asset
        String assetIssuer = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 0 means that the contract is only triggered
        Long amount = 0L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // Transaction initiation account's Nonce + 1
        Long nonce = 57L;
        // Contract main function entry
        String input = "";

        // 1. Get the account address to send this transaction
        String invokeAddresss = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build sendAsset operation
        ContractInvokeByAssetOperation operation = new ContractInvokeByAssetOperation();
        operation.setSourceAddress(invokeAddresss);
        operation.setContractAddress(destAddress);
        operation.setCode(assetCode);
        operation.setIssuer(assetIssuer);
        operation.setAssetAmount(amount);
        operation.setInput(input);
        operation.setMetadata("send token");

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Send BU and trigger contract
     */
    @Test
    public void invokeContractByBU() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbxK6xXnsWVy5pHJK8vzRkExzN5fcT11B9FVGevhWQYAy9E67SRBm";
        // The account to receive the BU
        String destAddress = "buQqzdS9YSnokDjvzg4YaNatcFQfkgXqk6ss";
        // 0 means that the contract is only triggered
        Long amount = ToBaseUnit.BU2MO("810");
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.01");
        // Transaction initiation account's Nonce + 1
        Long nonce = 5L;
        // Contract main function entry
        String input = "{\"method\":\"vote\",\"params\" : {\"role\":\"kol\",\"address\": \"buQZvtjVHLVuwS43bevdxMFbZqrS6HCNrnmf\"}}";

        // 1. Get the account address to send this transaction
        String invokeAddresss = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build sendAsset operation
        ContractInvokeByBUOperation operation = new ContractInvokeByBUOperation();
        operation.setSourceAddress(invokeAddresss);
        operation.setContractAddress(destAddress);
        operation.setBuAmount(amount);
        operation.setInput(input);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
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
    private String submitTransaction(String senderPrivateKey, String senderAddresss, BaseOperation operation, Long senderNonce, Long gasPrice, Long feeLimit) {
        // 3. Build transaction
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(operation);
        // transactionBuildBlobRequest.setMetadata("abc");

        // 4. Build transaction BLob
        String transactionBlob;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        String txHash = transactionBuildBlobResult.getHash();
        transactionBlob = transactionBuildBlobResult.getTransactionBlob();

        // 5. Sign transaction BLob
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

        // 6. Broadcast
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
