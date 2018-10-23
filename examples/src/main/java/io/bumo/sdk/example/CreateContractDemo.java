package io.bumo.sdk.example;

import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.operation.ContractCreateOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.data.Signature;

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
        boolean status = checkTransactionStatus(hash);
        System.out.println("status: " + status);
    }


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
        // The token total supply number
        String supply = "1000000000";
        // The token decimals
        Integer decimals = 8;
        // Contract code
        String payload = "'use strict';let globalAttribute={};function globalAttributeKey(){return'global_attribute';}function loadGlobalAttribute(){if(Object.keys(globalAttribute).length===0){let value=storageLoad(globalAttributeKey());assert(value!==false,'Get global attribute from metadata failed.');globalAttribute=JSON.parse(value);}}function storeGlobalAttribute(){let value=JSON.stringify(globalAttribute);storageStore(globalAttributeKey(),value);}function powerOfBase10(exponent){let i=0;let power=1;while(i<exponent){power=power*10;i=i+1;}return power;}function makeBalanceKey(address){return'balance_'+address;}function makeAllowanceKey(owner,spender){return'allow_'+owner+'_to_'+spender;}function valueCheck(value){if(value.startsWith('-')||value==='0'){return false;}return true;}function approve(spender,value){assert(addressCheck(spender)===true,'Arg-spender is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(valueCheck(value)===true,'Arg-value must be positive number.');let key=makeAllowanceKey(sender,spender);storageStore(key,value);tlog('approve',sender,spender,value);return true;}function allowance(owner,spender){assert(addressCheck(owner)===true,'Arg-owner is not a valid address.');assert(addressCheck(spender)===true,'Arg-spender is not a valid address.');let key=makeAllowanceKey(owner,spender);let value=storageLoad(key);assert(value!==false,'Get allowance '+owner+' to '+spender+' from metadata failed.');return value;}function transfer(to,value){assert(addressCheck(to)===true,'Arg-to is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(valueCheck(value)===true,'Arg-value must be positive number.');if(sender===to){tlog('transfer',sender,to,value);return true;}let senderKey=makeBalanceKey(sender);let senderValue=storageLoad(senderKey);assert(senderValue!==false,'Get balance of '+sender+' from metadata failed.');assert(int64Compare(senderValue,value)>=0,'Balance:'+senderValue+' of sender:'+sender+' < transfer value:'+value+'.');let toKey=makeBalanceKey(to);let toValue=storageLoad(toKey);toValue=(toValue===false)?value:int64Add(toValue,value);storageStore(toKey,toValue);senderValue=int64Sub(senderValue,value);storageStore(senderKey,senderValue);tlog('transfer',sender,to,value);return true;}function assign(to,value){assert(addressCheck(to)===true,'Arg-to is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(valueCheck(value)===true,'Arg-value must be positive number.');if(thisAddress===to){tlog('assign',to,value);return true;}loadGlobalAttribute();assert(sender===globalAttribute.contractOwner,sender+' has no permission to assign contract balance.');assert(int64Compare(globalAttribute.balance,value)>=0,'Balance of contract:'+globalAttribute.balance+' < assign value:'+value+'.');let toKey=makeBalanceKey(to);let toValue=storageLoad(toKey);toValue=(toValue===false)?value:int64Add(toValue,value);storageStore(toKey,toValue);globalAttribute.balance=int64Sub(globalAttribute.balance,value);storeGlobalAttribute();tlog('assign',to,value);return true;}function transferFrom(from,to,value){assert(addressCheck(from)===true,'Arg-from is not a valid address.');assert(addressCheck(to)===true,'Arg-to is not a valid address.');assert(stoI64Check(value)===true,'Arg-value must be alphanumeric.');assert(valueCheck(value)===true,'Arg-value must be positive number.');if(from===to){tlog('transferFrom',sender,from,to,value);return true;}let fromKey=makeBalanceKey(from);let fromValue=storageLoad(fromKey);assert(fromValue!==false,'Get value failed, maybe '+from+' has no value.');assert(int64Compare(fromValue,value)>=0,from+' balance:'+fromValue+' < transfer value:'+value+'.');let allowValue=allowance(from,sender);assert(int64Compare(allowValue,value)>=0,'Allowance value:'+allowValue+' < transfer value:'+value+' from '+from+' to '+to+'.');let toKey=makeBalanceKey(to);let toValue=storageLoad(toKey);toValue=(toValue===false)?value:int64Add(toValue,value);storageStore(toKey,toValue);fromValue=int64Sub(fromValue,value);storageStore(fromKey,fromValue);let allowKey=makeAllowanceKey(from,sender);allowValue=int64Sub(allowValue,value);storageStore(allowKey,allowValue);tlog('transferFrom',sender,from,to,value);return true;}function changeOwner(address){assert(addressCheck(address)===true,'Arg-address is not a valid address.');loadGlobalAttribute();assert(sender===globalAttribute.contractOwner,sender+' has no permission to modify contract ownership.');globalAttribute.contractOwner=address;storeGlobalAttribute();tlog('changeOwner',sender,address);}function name(){return globalAttribute.name;}function symbol(){return globalAttribute.symbol;}function decimals(){return globalAttribute.decimals;}function totalSupply(){return globalAttribute.totalSupply;}function ctp(){return globalAttribute.ctp;}function contractInfo(){return globalAttribute;}function balanceOf(address){assert(addressCheck(address)===true,'Arg-address is not a valid address.');if(address===globalAttribute.contractOwner||address===thisAddress){return globalAttribute.balance;}let key=makeBalanceKey(address);let value=storageLoad(key);assert(value!==false,'Get balance of '+address+' from metadata failed.');return value;}function init(input_str){let input=JSON.parse(input_str);assert(stoI64Check(input.params.supply)===true&&typeof input.params.name==='string'&&typeof input.params.symbol==='string'&&typeof input.params.decimals==='number','Args check failed.');globalAttribute.ctp='1.0';globalAttribute.name=input.params.name;globalAttribute.symbol=input.params.symbol;globalAttribute.decimals=input.params.decimals;globalAttribute.totalSupply=int64Mul(input.params.supply,powerOfBase10(globalAttribute.decimals));globalAttribute.contractOwner=sender;globalAttribute.balance=globalAttribute.totalSupply;storageStore(globalAttributeKey(),JSON.stringify(globalAttribute));}function main(input_str){let input=JSON.parse(input_str);if(input.method==='transfer'){transfer(input.params.to,input.params.value);}else if(input.method==='transferFrom'){transferFrom(input.params.from,input.params.to,input.params.value);}else if(input.method==='approve'){approve(input.params.spender,input.params.value);}else if(input.method==='assign'){assign(input.params.to,input.params.value);}else if(input.method==='changeOwner'){changeOwner(input.params.address);}else{throw'<unidentified operation type>';}}function query(input_str){loadGlobalAttribute();let result={};let input=JSON.parse(input_str);if(input.method==='name'){result.name=name();}else if(input.method==='symbol'){result.symbol=symbol();}else if(input.method==='decimals'){result.decimals=decimals();}else if(input.method==='totalSupply'){result.totalSupply=totalSupply();}else if(input.method==='ctp'){result.ctp=ctp();}else if(input.method==='contractInfo'){result.contractInfo=contractInfo();}else if(input.method==='balanceOf'){result.balance=balanceOf(input.params.address);}else if(input.method==='allowance'){result.allowance=allowance(input.params.owner,input.params.spender);}else{throw'<unidentified operation type>';}log(result);return JSON.stringify(result);}";

        // Init initInput
        JSONObject initInput = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("symbol", symbol);
        params.put("decimals", decimals);
        params.put("supply", supply);
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


    public static boolean checkTransactionStatus(String txHash) {
        Boolean transactionStatus = false;
        // 调用上面封装的“发送交易”接口
        // 交易执行等待10秒
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Init request
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);

        // Call getInfo
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        if (response.getErrorCode() == 0) {
            transactionStatus = true;
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
        return transactionStatus;
    }

}
