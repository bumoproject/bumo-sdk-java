package io.bumo.sdk.example;

import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.AccountActivateOperation;
import io.bumo.model.request.operation.AccountSetMetadataOperation;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.AccountCreateResult;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import io.bumo.model.response.result.data.TransactionHistory;

/**
 * @author riven
 * @date 2019/2/14 11:33
 */
public class ArtsCertificateDemo {
    static SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");

    /**
     * 该Demo完成了三个功能:
     *   1、创建一个用于存储艺术品证书的新区块链账户
     *   2、存储艺术品证书
     *   3、修改艺术品证书
     */
    public static void main(String[] argv) {
        // 用于创建新区块链账户的私钥
        String activatePrivateKey = "privbvTuL1k8z27i9eyBrFDUvAVVCSxKeLtzjMMZEqimFwbNchnejS81";
        // 给新账户的初始化资产
        Long initBalance = ToBaseUnit.BU2MO("1");
        // 艺术品证书ID
        String artCertificateId = "A112366544";
        // 艺术品证书所有人
        String owner = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // 艺术品信息
        String artInfo = "我的证书";
        // 修改艺术品证书所有人
        String changeOwner = "buQggKYsD24KhTnaUZ5TPTqYotKeWJSsSZZm";

        // 生成一个新账户的信息
        AccountCreateResult newAccountInfo = generateAccountKeyPair();
        if (null == newAccountInfo) {
            return;
        }
        String newAccountAddress = newAccountInfo.getAddress();

        // 1、创建新账户，用于存储艺术品证书
        String createAccountHash = createArtCertificateAccount(activatePrivateKey, newAccountAddress, initBalance);
        if (null == createAccountHash) {
            return;
        }

        // 确保新账户创建交易执行成功
        if (!MakeSureTxSuccess(createAccountHash)) {
            return;
        }

        // 2、存储艺术品证书信息
        String storePrivateKey = newAccountInfo.getPrivateKey();
        String storeArtCertificateHash = storeArtCertificate(storePrivateKey, owner, artCertificateId, artInfo);
        if (null == storeArtCertificateHash) {
            return;
        }

        // 确保存储艺术品证书信息交易执行成功
        if (!MakeSureTxSuccess(storeArtCertificateHash)) {
            return;
        }

        // 3、修改艺术品证书信息（其中，艺术品证书ID不能修改，否则会作为一个新证书被存储）
        String changeArtInfoHash = storeArtCertificate(storePrivateKey, changeOwner, artCertificateId, artInfo);
        if (null == changeArtInfoHash) {
            return;
        }
        System.out.println("存储艺术品证书信息的账户: " + newAccountAddress + ", " + storePrivateKey);
    }

    /**
     * 生成一个新账户的信息
     * @return AccountCreateResult null / 新账户的私钥、公钥和地址
     */
    public static AccountCreateResult generateAccountKeyPair() {
        AccountCreateResponse accountCreateResponse = sdk.getAccountService().create();
        if (accountCreateResponse.getErrorCode() != 0) {
            System.out.println("error: " + accountCreateResponse.getErrorDesc());
            return null;
        }
        return accountCreateResponse.getResult();
    }

    /**
     * 创建一个用于存储艺术品证书的区块链账户
     * @param activatePrivateKey 用于创建新区块链账户的私钥
     * @return String null / 交易hash
     */
    public static String createArtCertificateAccount(String activatePrivateKey, String newAccountAddress, Long initBalance) {
        // 交易费用的单价（交易按字节收费，单位是 MO，1 BU = 10^8 MO）
        Long gasPrice = 1000L;
        // 交易费用（单位是 MO， 1 BU = 10^8 MO）
        Long feeLimit = ToBaseUnit.BU2MO("0.01");

        // 1、根据私钥生成地址
        String activateAddress = getAddressByPrivateKey(activatePrivateKey);

        // 2、获取账户最新交易序列号，且新交易的序列号须增加1
        Long nonce = getAccountNonce(activateAddress);
        nonce += 1;

        // 3、组装激活新区块链账户的操作
        AccountActivateOperation operation = new AccountActivateOperation();
        operation.setDestAddress(newAccountAddress);
        operation.setInitBalance(initBalance);
        operation.setMetadata("activate account");

        String[] signerPrivateKeyArr = { activatePrivateKey };

        // 4、提交交易到区块链，完成账户激活操作，并返回交易hash
        String txHash = submitTransaction(signerPrivateKeyArr, activateAddress, operation, nonce, gasPrice, feeLimit);
        return txHash;
    }

    /**
     * 存储或修改艺术品证书信息
     * @param storePrivateKey 用于存储或修改新区块链账户的私钥(这里就是上面创建的区块链账户的私钥)
     * @param owner 艺术品证书所有权归属人
     * @param artCertificateId 艺术品证书ID
     * @param artInfo 艺术品信息
     * @return String null / 交易hash
     */
    public static String storeArtCertificate(String storePrivateKey, String owner,
                                       String artCertificateId, String artInfo) {
        // 交易费用的单价（交易按字节收费，单位是 MO，1 BU = 10^8 MO）
        Long gasPrice = 1000L;
        // 交易费用（单位是 MO， 1 BU = 10^8 MO）
        Long feeLimit = ToBaseUnit.BU2MO("0.01");

        // 1、根据私钥生成地址
        String storeAddress = getAddressByPrivateKey(storePrivateKey);

        // 2、获取账户最新交易序列号，且新交易的序列号须增加1
        Long nonce = getAccountNonce(storeAddress);
        nonce += 1;

        // 3、组装存储账户元数据的操作
        String key = artCertificateId;
        JSONObject valueJson = new JSONObject();
        valueJson.put("owner", owner);
        valueJson.put("art", artInfo);
        String value = valueJson.toJSONString();

        AccountSetMetadataOperation operation = new AccountSetMetadataOperation();
        operation.setSourceAddress(storeAddress);
        operation.setKey(key);
        operation.setValue(value);

        String[] signerPrivateKeyArr = { storePrivateKey };

        // 4、提交交易到区块链，完成账户激活操作，并返回交易hash
        String txHash = submitTransaction(signerPrivateKeyArr, storeAddress, operation, nonce, gasPrice, feeLimit);
        return txHash;
    }

    public static boolean MakeSureTxSuccess(String txHash) {
        long startTime = System.currentTimeMillis();
        while (true) {
            int status = checkTxStatusByHash(txHash);
            if (1 == status) {
                break;
            } else if (0 == status) {
                System.out.println("error: 交易(" + txHash + ") 执行失败");
                return false;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 50000) {
                System.out.println("error: 交易(" + txHash + ") 执行超时");
                return false;
            }
        }
        return true;
    }

    /**
     * 获取指定账户的账户最新交易序列号
     * @param accountAddress 区块链账户地址
     * @return Long null / 最新交易序列号
     */
    private static Long getAccountNonce(String accountAddress) {
        AccountGetNonceRequest request = new AccountGetNonceRequest();
        request.setAddress(accountAddress);

        AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
        if (response.getErrorCode() != 0) {
            System.out.println("error: " + response.getErrorDesc());
            return null;
        }
        return response.getResult().getNonce();
    }

    /**
     * 提交交易（注意: 提交交易成功，并不表示交易执行成功，须等10秒以后共识完成以后，再查询交易历史，确认最终的交易状态）
     * @param senderPrivateKeys 签名和提交交易的账户私钥
     * @param operation         待完成的操作
     * @param senderNonce       提交交易账户的交易序列号
     * @param gasPrice          交易费用单价，交易按字节收费
     * @param feeLimit          交易费用
     * @return String null / 交易hash
     */
    private static String submitTransaction(String[] senderPrivateKeys, String senderAddress, BaseOperation operation,
                                     Long senderNonce, Long gasPrice, Long feeLimit) {
        // 1、序列化交易
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddress);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        transactionBuildBlobRequest.addOperation(operation);
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();

        // 2、签名
        String txHash = transactionBuildBlobResult.getHash();
        String transactionBlob = transactionBuildBlobResult.getTransactionBlob();
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        for (int i = 0; i < senderPrivateKeys.length; i++) {
            transactionSignRequest.addPrivateKey(senderPrivateKeys[i]);
        }
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionSignResponse.getErrorDesc());
            return null;
        }

        // 3、提交交易
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (transactionSubmitResponse.getErrorCode() != 0) {
            System.out.println("error: " + transactionSubmitResponse.getErrorDesc());
            return null;
        }
        return txHash;
    }

    /**
     * 根据私钥生成地址的方法
     * @param privateKey 私钥
     * @return String null / 地址
     */
    private static String getAddressByPrivateKey(String privateKey) {
        if (!PrivateKey.isPrivateKeyValid(privateKey)) {
            System.out.println("error: private key(" + privateKey + ") is invalid");
            return null;
        }
        String publicKey = PrivateKey.getEncPublicKey(privateKey);
        return PrivateKey.getEncAddress(publicKey);
    }

    /**
     * 根据交易hash查询交易是否执行成功
     * @param txHash 待查询的交易hash
     * @return int 1: 成功, 0: 失败, -1: 未查询到
     */
    private static int checkTxStatusByHash(String txHash) {
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);

        // Call getInfo
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        int errorCode = response.getErrorCode();
        if (errorCode == 0) {
            TransactionHistory transactionHistory = response.getResult().getTransactions()[0];
            if (transactionHistory.getErrorCode() != 0) {
                return 0;
            }
            else {
                return 1;
            }
        } else if (errorCode == 4) {
            return -1;
        } else {
            return 0;
        }
    }
}
