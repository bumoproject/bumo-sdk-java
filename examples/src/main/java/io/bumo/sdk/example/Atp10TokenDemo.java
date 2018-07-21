package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.model.request.operation.Atp10TokenIssueOperation;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.TransactionBuildBlobRequest;
import io.bumo.model.request.TransactionSignRequest;
import io.bumo.model.request.TransactionSubmitRequest;
import io.bumo.model.request.other.IssueType;
import io.bumo.model.response.TransactionBuildBlobResponse;
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
     */
    @Test
    public void IssueUnlimitedAtp10Token_Success() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 3L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);

        // 2. Build issueAtp10Token operation
        Atp10TokenIssueOperation operation = new Atp10TokenIssueOperation();
        operation.setSourceAddress(issuerAddresss);
        operation.setDestAddress(destAccount);
        operation.setType(issueType);
        operation.setCode(code);
        operation.setSupply(supply);
        operation.setNowSupply(nowSupply);
        operation.setDecimals(decimals);
        operation.setDescription(description);
        operation.setMetadata(metadata);


        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(issuerPrivateKey, issuerAddresss, operation, nonce, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("hash: " + txHash);
        }
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error: The transaction source address is empty,
     *         but that the operation source address is empty is no problem
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_NoSourceAddress() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 4L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error: TransSourceAddress is ""
        transSourceAddress = "";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // success: opSourceAddress is ""
        opSourceAddress = "";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error 1: The operation source address is invalid
     * Error 2: The transaction source address is invalid
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_SourceAddressInvalid() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 9L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String transSourceAddress = issuerAddresss;

        // error 1: OpSourceAddress is invalid
        String opSourceAddress = "buQBjJD1BSJ7nzAbzdTenAhp%%FjmxRVEEtmxH";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: transSourceAddress is invalid
        transSourceAddress = "buQBjJD1BSJ7nzAbzdTenAhp%%FjmxRVEEtmxH";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error 1: The operation source address is equal to destAddress
     * Error 2: The transaction source address is equal to destAddress, when the operation source address is empty
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_SourceAddressEqualDestAddress() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 4L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String transSourceAddress = issuerAddresss;

        // error 1: OpSourceAddress equal to destAddress
        String opSourceAddress = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";

        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: TransSourceAddress equal to destAddress
        opSourceAddress = "";
        transSourceAddress = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error 1: The destination address is null
     * Error 2: The destination address is empty
     * Error 3: The destination address is invalid
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_DestAddressInvalid() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 4L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error 1: destAddress is null
        destAccount = null;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: destAddress is ""
        destAccount = "";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 3: DestAddress is invalid
        destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjm%%VEEtmxH";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error 1: The code address is null
     * Error 2: The code address is empty
     * Error 3: The code address is invalid
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_CodeInvalid() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 4L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error 1: code is null
        code = null;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: code is ""
        code = "";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 3: code is larger than 64
        code = "buQBjJD1BSJ7nzAbzdTenAhpFjm%%VEEtmxHbuQBjJD1BSJ7nzAbzdTenAhpFjm%%VEEtmxH";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token successfully
     * Success 1: The code address is null
     * Success 2: The code address is -100
     */
    @Test
    public void IssueUnlimitedAtp10Token_Success_SupplyNotCheck() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 4L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error 1: supply is null
        supply = null;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: supply is -100
        supply = -100L;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error 1: The nowSupply is 0
     * Error 2: The nowSupply is -100
     * Error 3: nowSupply * (10 ^ decimals) is bigger than Long.MAX_VALUE
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_NowSupplyInvalid() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 9L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error 1: nowSupply is 0
        nowSupply = 0L;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: nowSupply is -100
        nowSupply = -100L;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 3: nowSupply * (10 ^ decimals) is bigger than Long.MAX_VALUE
        nowSupply = (long)Math.pow(10, 11);
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // success 3: nowSupply is 1
        nowSupply = 1L;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error 1: The decimals is -1
     * Error 2: The decimals is 9
     * Error 2: The decimals is null
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_DecimalsInvalid() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 9L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error 1: decimals is -1
        decimals = -1;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 2: decimals is 9
        decimals = 9;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);

        // error 3: decimals is null
        decimals = null;
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    /**
     * Issue the unlimited apt1.0 token failed
     * Error : The length of the description is 1025
     */
    @Test
    public void IssueUnlimitedAtp10Token_Error_DescriptionInvalid() {
        // The account private key to issue atp1.0 token
        String issuerPrivateKey = "privbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        // The account to receive atp1.0 token
        String destAccount = "buQBjJD1BSJ7nzAbzdTenAhpFjmxRVEEtmxH";
        // The type to issue
        IssueType issueType = IssueType.UNLIMITED;
        // The token code
        String code = "TXT";
        // The token total supply number
        Long supply = 1000000000L;
        // The token now supply number
        Long nowSupply = 1000000000L;
        // The token decimals
        Integer decimals = 8;
        // The token description
        String description = "test unlimited issuance of apt1.0 token";
        // The operation notes
        String metadata = "test one off issue apt1.0 token";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Set up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("50.03");
        // Transaction initiation account's Nonce + 1
        Long nonce = 9L;

        // 1. Get the account address to send this transaction
        String issuerAddresss = getAddressByPrivateKey(issuerPrivateKey);
        String opSourceAddress = issuerAddresss;
        String transSourceAddress = issuerAddresss;

        // error: description's length is 1025
        description = "rS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEutprivbsKxedzmq9g9Na1bBCbnC3UQvEYZUVAEXHErS4PFvhZd2162xEut";
        submitIssueTransaction(issuerPrivateKey, opSourceAddress, transSourceAddress, destAccount, issueType, code, supply, nowSupply, decimals,
                description, metadata, nonce, gasPrice, feeLimit);
    }

    private void submitIssueTransaction(String privateKey, String opSourceAddress, String transSourceAddress, String destAccount, IssueType issueType,
                                     String code, Long supply, Long nowSupply, Integer decimals, String description, String metadata,
                                     Long nonce, Long gasPrice, Long feeLimit) {
        // 2. Build issueAtp10Token operation
        Atp10TokenIssueOperation operation = new Atp10TokenIssueOperation();
        operation.setSourceAddress(opSourceAddress);
        operation.setDestAddress(destAccount);
        operation.setType(issueType);
        operation.setCode(code);
        operation.setSupply(supply);
        operation.setNowSupply(nowSupply);
        operation.setDecimals(decimals);
        operation.setDescription(description);
        operation.setMetadata(metadata);


        // Record txhash for subsequent confirmation of the real result of the transaction.
        // After recommending five blocks, call again through txhash `Get the transaction information
        // from the transaction Hash'(see example: getTxByHash ()) to confirm the final result of the transaction
        String txHash = submitTransaction(privateKey, transSourceAddress, operation, nonce, gasPrice, feeLimit);
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
