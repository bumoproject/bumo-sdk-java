package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import org.junit.Test;

/**
 * @Author riven
 * @Date 2018/8/6 20:54
 */
public class Ctp10TokenDemo {
    SDK sdk = SDK.getInstance("http://seed1.bumotest.io:26002");

    /**
     * Check whether token is valid
     */
    @Test
    public void checkTokenValid() {
        // Init request
        Ctp10TokenCheckValidRequest request = new Ctp10TokenCheckValidRequest();
        request.setContractAddress("buQfnVYgXuMo3rvCEpKA6SfRrDpaz8D8A9Ea");

        // Call checkValid
        Ctp10TokenCheckValidResponse response = sdk.getCtp10TokenService().checkValid(request);
        if (response.getErrorCode() == 0) {
            TokenCheckValidResult result = response.getResult();
            System.out.println(result.getValid());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query he number of available contract Token for authorized users
     */
    @Test
    public void getCtp10TokenAllowance() {
        // Init request
        Ctp10TokenAllowanceRequest request = new Ctp10TokenAllowanceRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");
        request.setTokenOwner("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");
        request.setSpender("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");

        // Call allowance
        Ctp10TokenAllowanceResponse response = sdk.getCtp10TokenService().allowance(request);
        if (response.getErrorCode() == 0) {
            TokenAllowanceResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query contract token info
     */
    @Test
    public void getCtp10TokenInfo() {
        // Init request
        Ctp10TokenGetInfoRequest request = new Ctp10TokenGetInfoRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // Call getInfo
        Ctp10TokenGetInfoResponse response = sdk.getCtp10TokenService().getInfo(request);
        if (response.getErrorCode() == 0) {
            TokenGetInfoResult result = response.getResult();
            System.out.println(JSON.toJSONString(result, true));
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query contract token name
     */
    @Test
    public void getCtp10TokenName() {
        // Init request
        Ctp10TokenGetNameRequest request = new Ctp10TokenGetNameRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // Call getName
        Ctp10TokenGetNameResponse response = sdk.getCtp10TokenService().getName(request);
        if (response.getErrorCode() == 0) {
            TokenGetNameResult result = response.getResult();
            System.out.println(result.getName());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query contract token symbol
     */
    @Test
    public void getCtp10TokenSymbol() {
        // Init request
        Ctp10TokenGetSymbolRequest request = new Ctp10TokenGetSymbolRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // Call getSymbol
        Ctp10TokenGetSymbolResponse response = sdk.getCtp10TokenService().getSymbol(request);
        if (response.getErrorCode() == 0) {
            TokenGetSymbolResult result = response.getResult();
            System.out.println(result.getSymbol());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query contract token decimals
     */
    @Test
    public void getCtp10TokenDecimals() {
        // Init request
        Ctp10TokenGetDecimalsRequest request = new Ctp10TokenGetDecimalsRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // Call getDecimals
        Ctp10TokenGetDecimalsResponse response = sdk.getCtp10TokenService().getDecimals(request);
        if (response.getErrorCode() == 0) {
            TokenGetDecimalsResult result = response.getResult();
            System.out.println(result.getDecimals());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query contract token total supply
     */
    @Test
    public void getCtp10TokenTotalSupply() {
        // Init request
        Ctp10TokenGetTotalSupplyRequest request = new Ctp10TokenGetTotalSupplyRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");

        // Call getTotalSupply
        Ctp10TokenGetTotalSupplyResponse response = sdk.getCtp10TokenService().getTotalSupply(request);
        if (response.getErrorCode() == 0) {
            TokenGetTotalSupplyResult result = response.getResult();
            System.out.println(result.getTotalSupply());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Query the balance of the contract Token under an account
     */
    @Test
    public void getCtp10TokenBalance() {
        // Init request
        Ctp10TokenGetBalanceRequest request = new Ctp10TokenGetBalanceRequest();
        request.setContractAddress("buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq");
        request.setTokenOwner("buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp");

        // Call getBalance
        Ctp10TokenGetBalanceResponse response = sdk.getCtp10TokenService().getBalance(request);
        if (response.getErrorCode() == 0) {
            TokenGetBalanceResult result = response.getResult();
            System.out.println(result.getBalance());
        } else {
            System.out.println("error: " + response.getErrorDesc());
        }
    }

    /**
     * Issue token
     */
    @Test
    public void issueToken() {
        // Init variable
        // The account private key to create contract token
        String sourcePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The initialization BU of the contract to be created
        Long initBalance = ToBaseUnit.BU2MO("0.1");
        // The decimals of token amount
        Integer decimals = 8;
        // Token name
        String name = "TST";
        // Token supply amount (not include decimals)，actually it is (10 ^ 8) * (10 ^ decimals)
        String supply = "9223372036854775808";
        // Token symbol
        String symbol = "TST";
        // Transaction initiation account's nonce + 1
        Long nonce = 36L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 10.08BU
        Long feeLimit = ToBaseUnit.BU2MO("10.08");

        // 1. Get the account address to send this transaction
        String sourceAddress = getAddressByPrivateKey(sourcePrivateKey);

        // 2. Build issueToken
        Ctp10TokenIssueOperation operation = new Ctp10TokenIssueOperation();
        operation.setSourceAddress(sourceAddress);
        operation.setDecimals(decimals);
        operation.setInitBalance(initBalance);
        operation.setName(name);
        operation.setSupply(supply);
        operation.setSymbol(symbol);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(sourcePrivateKey, sourceAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 分配Token
     */
    @Test
    public void assignToken() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The contract account address of the contract token code
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // The account to receive token
        String destAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // The token amount to be sent
        String amount = "1000000";
        // Transaction initiation account's nonce + 1
        Long nonce = 38L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.02BU
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. Get the account address to send this transaction
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build assignToken
        Ctp10TokenAssignOperation operation = new Ctp10TokenAssignOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setDestAddress(destAddress);
        operation.setTokenAmount(amount);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 转移Token
     */
    @Test
    public void transferToken() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The contract account address of the contract token code
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // The account to receive token
        String destAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // The token amount to be sent
        String amount = "1000000";
        // Transaction initiation account's nonce + 1
        Long nonce = 38L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.02BU
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. Get the account address to send this transaction
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build transferToken
        Ctp10TokenTransferOperation operation = new Ctp10TokenTransferOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setDestAddress(destAddress);
        operation.setTokenAmount(amount);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 从指定账户转移Token
     */
    @Test
    public void transferTokenFrom() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The contract account address of the contract token code
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // The account to send token
        String fromAddress = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // The account to receive token
        String destAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // The token amount to be sent
        String amount = "100000";
        // Transaction initiation account's nonce + 1
        Long nonce = 54L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.02BU
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. Get the account address to send this transaction
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build transferToken
        Ctp10TokenTransferFromOperation operation = new Ctp10TokenTransferFromOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setFromAddress(fromAddress);
        operation.setDestAddress(destAddress);
        operation.setTokenAmount(amount);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 授权转移指定数量的Token
     */
    @Test
    public void approveToken() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The contract account address of the contract token code
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // The account to be allowed to spend token
        String spender = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // The amount to be allowed
        String amount = "1000000";
        // Transaction initiation account's nonce + 1
        Long nonce = 50L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.02BU
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. Get the account address to send this transaction
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build transferToken
        Ctp10TokenApproveOperation operation = new Ctp10TokenApproveOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setSpender(spender);
        operation.setTokenAmount(amount);

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * 修改Token的拥有者
     */
    @Test
    public void changeTokenOwner() {
        // Init variable
        // The account private key to invoke contract
        String invokePrivateKey = "privbyQCRp7DLqKtRFCqKQJr81TurTqG6UKXMMtGAmPG3abcM9XHjWvq";
        // The contract account address of the contract token code
        String contractAddress = "buQhdBSkJqERBSsYiUShUZFMZQhXvkdNgnYq";
        // token owner
        String tokenOwner = "buQnnUEBREw2hB6pWHGPzwanX7d28xk6KVcp";
        // Transaction initiation account's nonce + 1
        Long nonce = 55L;
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.02BU
        Long feeLimit = ToBaseUnit.BU2MO("0.02");

        // 1. Get the account address to send this transaction
        String invokeAddress = getAddressByPrivateKey(invokePrivateKey);

        // 2. Build changeOwner
        Ctp10TokenChangeOwnerOperation operation = new Ctp10TokenChangeOwnerOperation();
        operation.setSourceAddress(invokeAddress);
        operation.setContractAddress(contractAddress);
        operation.setTokenOwner(tokenOwner);
        operation.setMetadata("");

        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(invokePrivateKey, invokeAddress, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * @param senderPrivateKey The account private key to start transaction
     * @param senderAddresss   The account address to start transaction
     * @param operation        Operation
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
