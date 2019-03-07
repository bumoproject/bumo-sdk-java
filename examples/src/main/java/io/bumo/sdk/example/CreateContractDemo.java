package io.bumo.sdk.example;

import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.operation.ContractCreateOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.data.Signature;
import io.bumo.model.response.result.data.TransactionHistory;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/10/22 18:38
 */
public class CreateContractDemo {
    public static SDK sdk;
    public static void main(String argv[]) {
        String url = "http://seed1.bumotest.io:26002";
        sdk = SDK.getInstance(url);

        long nonce = getAccountNonce();
        System.out.println("nonce:" + nonce);
        BaseOperation[] operations = buildOperations();
        String transactionBlob = seralizeTransaction(nonce, operations);
        System.out.println("blob: " + transactionBlob);
        Signature[] signatures = signTransaction(transactionBlob);
        System.out.println("signData: " + signatures[0].getSignData());
        System.out.println("publicKey: " + signatures[0].getPublicKey());
        String hash = submitTransaction(transactionBlob, signatures);
        System.out.println("hash: " + hash);
        int status = checkTransactionStatus(hash);
        System.out.println("status: " + status);
    }

    @Test
    public static long getAccountNonce() {
        long nonce = 0;

        // Init request
        String accountAddress = "buQYLtRq4j3eqbjVNGYkKYo3sLBqW3TQH2xH";
        AccountGetNonceRequest request = new AccountGetNonceRequest();
        request.setAddress(accountAddress);

        // Call getNonce
        AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
        if (0 == response.getErrorCode()) {
            nonce = response.getResult().getNonce();
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
        return nonce;
    }

    public static BaseOperation[] buildOperations() {
        // The account address to issue apt1.0 token
        String createContractAddress = "buQYLtRq4j3eqbjVNGYkKYo3sLBqW3TQH2xH";
        // Contract account initialization BU，the unit is MO，and 1 BU = 10^8 MO
        Long initBalance = ToBaseUnit.BU2MO("0.01");
        // The token name
        String name = "Contract Global";
        // The token code
        String symbol = "CGO";
        // The token total supply, which includes the dicimals.
        // If decimals is 8 and you want to issue 10 tokens now, the nowSupply must be 10 * 10 ^ 8, like below.
        String totalSupply = "1000000000";
        // The token decimals
        Integer decimals = 8;
        // Contract code
        String payload = "'use strict';let globalAttribute={};const globalAttributeKey='global_attribute';function makeAllowanceKey(owner,spender){return'allow_'+owner+'_to_'+spender;}function approve(spender,value){assert(addressCheck(spender)===true,'Arg-spender is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(int64Compare(value,'0')>0,'Arg-value of spender '+spender+' must be greater than 0.');let key=makeAllowanceKey(sender,spender);storageStore(key,value);tlog('approve',sender,spender,value);return true;}function allowance(owner,spender){assert(addressCheck(owner)===true,'Arg-owner is not a valid address.');assert(addressCheck(spender)===true,'Arg-spender is not a valid address.');let key=makeAllowanceKey(owner,spender);let value=storageLoad(key);assert(value!==false,'Failed to get the allowance given to '+spender+' by '+owner+' from metadata.');return value;}function transfer(to,value){assert(addressCheck(to)===true,'Arg-to is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(int64Compare(value,'0')>0,'Arg-value must be greater than 0.');if(sender===to){tlog('transfer',sender,to,value);return true;}let senderValue=storageLoad(sender);assert(senderValue!==false,'Failed to get the balance of '+sender+' from metadata.');assert(int64Compare(senderValue,value)>=0,'Balance:'+senderValue+' of sender:'+sender+' < transfer value:'+value+'.');let toValue=storageLoad(to);toValue=(toValue===false)?value:int64Add(toValue,value);storageStore(to,toValue);senderValue=int64Sub(senderValue,value);storageStore(sender,senderValue);tlog('transfer',sender,to,value);return true;}function transferFrom(from,to,value){assert(addressCheck(from)===true,'Arg-from is not a valid address.');assert(addressCheck(to)===true,'Arg-to is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(int64Compare(value,'0')>0,'Arg-value must be greater than 0.');if(from===to){tlog('transferFrom',sender,from,to,value);return true;}let fromValue=storageLoad(from);assert(fromValue!==false,'Failed to get the value, probably because '+from+' has no value.');assert(int64Compare(fromValue,value)>=0,from+' Balance:'+fromValue+' < transfer value:'+value+'.');let allowValue=allowance(from,sender);assert(int64Compare(allowValue,value)>=0,'Allowance value:'+allowValue+' < transfer value:'+value+' from '+from+' to '+to+'.');let toValue=storageLoad(to);toValue=(toValue===false)?value:int64Add(toValue,value);storageStore(to,toValue);fromValue=int64Sub(fromValue,value);storageStore(from,fromValue);let allowKey=makeAllowanceKey(from,sender);allowValue=int64Sub(allowValue,value);storageStore(allowKey,allowValue);tlog('transferFrom',sender,from,to,value);return true;}function balanceOf(address){assert(addressCheck(address)===true,'Arg-address is not a valid address.');let value=storageLoad(address);assert(value!==false,'Failed to get the balance of '+address+' from metadata.');return value;}function init(input_str){let params=JSON.parse(input_str).params;assert(stoI64Check(params.totalSupply)===true&&params.totalSupply>0&&typeof params.name==='string'&&params.name.length>0&&typeof params.symbol==='string'&&params.symbol.length>0&&typeof params.decimals==='number'&&params.decimals>=0,'Failed to check args');globalAttribute.totalSupply=params.totalSupply;globalAttribute.name=params.name;globalAttribute.symbol=params.symbol;globalAttribute.version='ATP20';globalAttribute.decimals=params.decimals;storageStore(globalAttributeKey,JSON.stringify(globalAttribute));storageStore(sender,globalAttribute.totalSupply);}function main(input_str){let input=JSON.parse(input_str);if(input.method==='transfer'){transfer(input.params.to,input.params.value);}else if(input.method==='transferFrom'){transferFrom(input.params.from,input.params.to,input.params.value);}else if(input.method==='approve'){approve(input.params.spender,input.params.value);}else{throw'<Main interface passes an invalid operation type>';}}function query(input_str){let result={};let input=JSON.parse(input_str);if(input.method==='tokenInfo'){globalAttribute=JSON.parse(storageLoad(globalAttributeKey));result.tokenInfo=globalAttribute;}else if(input.method==='allowance'){result.allowance=allowance(input.params.owner,input.params.spender);}else if(input.method==='balanceOf'){result.balance=balanceOf(input.params.address);}else{throw'<Query interface passes an invalid operation type>';}return JSON.stringify(result);}";

        // Init initInput
        JSONObject initInput = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("symbol", symbol);
        params.put("decimals", decimals);
        params.put("totalSupply", totalSupply);
        initInput.put("params", params);

        // Build create contract operation
        ContractCreateOperation contractCreateOperation = new ContractCreateOperation();
        contractCreateOperation.setSourceAddress(createContractAddress);
        contractCreateOperation.setInitBalance(initBalance);
        contractCreateOperation.setPayload(payload);
        contractCreateOperation.setInitInput(initInput.toJSONString());
        contractCreateOperation.setMetadata("create ctp 1.0 contract");

        BaseOperation[] operations = { contractCreateOperation };
        return operations;
    }

    public static String seralizeTransaction(Long nonce,  BaseOperation[] operations) {
        String transactionBlob = null;

        // The account address to create contract and issue ctp 1.0 token
        String senderAddresss = "buQYLtRq4j3eqbjVNGYkKYo3sLBqW3TQH2xH";
        // The gasPrice is fixed at 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 10.08BU
        Long feeLimit = ToBaseUnit.BU2MO("10.08");
        // Nonce should add 1
        nonce += 1;

        // Build transaction  Blob
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(nonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        for (int i = 0; i < operations.length; i++) {
            transactionBuildBlobRequest.addOperation(operations[i]);
        }
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() == 0) {
            transactionBlob = transactionBuildBlobResponse. getResult().getTransactionBlob();
        } else {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
        }
        return transactionBlob;
    }

    public static Signature[] signTransaction(String transactionBlob) {
        Signature[] signatures = null;
        // The account private key to issue atp1.0 token
        String senderPrivateKey = "privbs4iBCugQeb2eiycU8RzqkPqd28eaAYrRJGwtJTG8FVHjwAyjiyC";

        // Sign transaction BLob
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        transactionSignRequest.addPrivateKey(senderPrivateKey);
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() == 0) {
            signatures = transactionSignResponse.getResult().getSignatures();
        } else {
            System.out.println("error: " + transactionSignResponse.getErrorDesc());
        }
        return signatures;
    }

    public static String submitTransaction(String transactionBlob, Signature[] signatures) {
        String  hash = null;

        // Submit transaction
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(signatures);
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            hash = transactionSubmitResponse.getResult().getHash();
        } else {
            System.out.println("error: " + transactionSubmitResponse.getErrorDesc());
        }
        return  hash ;
    }


    public static int checkTransactionStatus(String txHash) {
        // Init request
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);

        // Call getInfo
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        int errorCode = response.getErrorCode();
        if (errorCode == 0){
            TransactionHistory transactionHistory = response.getResult().getTransactions()[0];
            errorCode = transactionHistory.getErrorCode();
        }

        return errorCode;
    }

}
