package io.bumo.token.impl;

import io.bumo.account.impl.AccountServiceImpl;
import io.bumo.common.Constant;
import io.bumo.common.ToBaseUnit;
import io.bumo.common.Tools;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.Operation.Atp10TokenAppendToIssueOperation;
import io.bumo.model.request.Operation.Atp10TokenIssueOperation;
import io.bumo.token.Atp10TokenService;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author riven
 * @Date 2018/8/6 10:00
 */
public class Atp10TokenServiceImpl implements Atp10TokenService {
    public static List<Chain.Operation> issue(Atp10TokenIssueOperation atp10TokenIssueOperation, String transSourceAddress) {
        List<Chain.Operation> operationList = null;

        try {
            String sourceAddress = atp10TokenIssueOperation.getSourceAddress();
            String destAddress = atp10TokenIssueOperation.getDestAddress();
            String code = atp10TokenIssueOperation.getCode();
            commonCheck(sourceAddress, destAddress, transSourceAddress, code);

            Atp10TokenIssueOperation.IssueType issueType = atp10TokenIssueOperation.getType();
            if (Tools.isEmpty(issueType)) {
                throw new SDKException(SdkError.INVALID_ISSUE_TYPE_ERROR);
            }
            Long supply = atp10TokenIssueOperation.getSupply();
            if (Tools.isEmpty(supply) || supply < 1) {
                throw new SDKException(SdkError.INVALID_ISSUE_AMOUNT_ERROR);
            }
            Integer decimals = atp10TokenIssueOperation.getDecimals();
            if (Tools.isEmpty(decimals) || decimals < Constant.TOKEN_DECIMALS_MIN || decimals > Constant.TOKEN_DECIMALS_MIN) {
                throw new SDKException(SdkError.INVALID_TOKEN_DECIMALS_ERROR);
            }
            Long totalSupply = supply * (long)Math.pow(10, decimals);
            if (totalSupply <= 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_SUPPLY_ERROR);
            }
            Long nowSupply = atp10TokenIssueOperation.getNowSupply();
            if (Tools.isEmpty(nowSupply) || nowSupply < 1 || nowSupply.compareTo(supply) > 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_NOW_SUPPLY_ERROR);
            }
            if ((issueType == Atp10TokenIssueOperation.IssueType.ONE_OFF && nowSupply.compareTo(supply) != 0)) {
                throw new SDKException(SdkError.INVALID_ONE_OFF_NOWSUPPLY_NOT_EQUAL_SUPPLY_ERROR);
            }
            String description = atp10TokenIssueOperation.getDescription();
            if (!Tools.isEmpty(description) && description.length() > Constant.DESCRIPTION_LENGTH_MAX) {
                throw new SDKException(SdkError.INVALID_TOKEN_SUPPLY_ERROR);
            }
            String metadata = atp10TokenIssueOperation.getMetadata();

            // build common operations
            operationList = commonBuildOperation(sourceAddress, destAddress, code, (nowSupply * (long)Math.pow(10, 8)), metadata);

            // According to issuing type to operate
            if (issueType == Atp10TokenIssueOperation.IssueType.ONE_OFF) {
                // set issuing account to a no privilege account
                Chain.Operation setPrivilegeOperation = AccountServiceImpl.buildSetPrivilegeOperation(sourceAddress, "0", "1", null, null, metadata);
                if (Tools.isEmpty(setPrivilegeOperation)) {
                    throw new SDKException(SdkError.OPERATIONS_ONE_ERROR);
                }
                operationList.add(setPrivilegeOperation);
            }
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }
        return operationList;
    }

    public static List<Chain.Operation> appendToIssue(Atp10TokenAppendToIssueOperation atp10TokenAppendToIssueOperation, String transSourceAddress) {
        List<Chain.Operation> operationList = null;
        try {
            // common check
            String sourceAddress = atp10TokenAppendToIssueOperation.getSourceAddress();
            String destAddress = atp10TokenAppendToIssueOperation.getDestAddress();
            String code = atp10TokenAppendToIssueOperation.getCode();
            commonCheck(sourceAddress, destAddress, transSourceAddress, code);
            Long appendSupply = atp10TokenAppendToIssueOperation.getAppendSupply();
            if (Tools.isEmpty(appendSupply) || appendSupply < 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_APPEND_SUPPLY_ERROR);
            }
            String metadata = atp10TokenAppendToIssueOperation.getMetadata();
            operationList = commonBuildOperation(sourceAddress, destAddress, code, appendSupply, metadata);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }
        return operationList;
    }

    private static void commonCheck(String sourceAddress, String destAddress, String transSourceAddress, String code) {
        if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        if (!PublicKey.isAddressValid(destAddress)) {
            throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
        }
        if ((!Tools.isEmpty(sourceAddress) && sourceAddress.equals(destAddress)) || transSourceAddress.equals(destAddress)) {
            throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
        }
        if (Tools.isEmpty(code) || code.length() > Constant.ASSET_CODE_MAX) {
            throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
        }
    }

    private static List<Chain.Operation> commonBuildOperation(String sourceAddress, String destAddress, String code, long nowSupply, String metadata) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        List<Chain.Operation> operationList = new ArrayList<>();
        // Issue token
        Chain.Operation issueOperation = AssetServiceImpl.buildIssueOperation(sourceAddress, code, nowSupply, metadata);
        if (null == issueOperation) {
            throw new SDKException(SdkError.OPERATIONS_ONE_ERROR);
        }
        operationList.add(issueOperation);

        // Check whether destination account is or not activated
        if (!AccountServiceImpl.isActivated(destAddress)) {
            // Activate destination account
            Chain.Operation activateOperation = AccountServiceImpl.buildActivateOperation(sourceAddress,
                    destAddress, ToBaseUnit.BU2MO("0.2"), null);
            if (Tools.isEmpty(activateOperation)) {
                throw new SDKException(SdkError.OPERATIONS_ONE_ERROR);
            }
            operationList.add(activateOperation);
        }
        // Send token
        Chain.Operation sendOperation = AssetServiceImpl.buildSendOperation(sourceAddress, destAddress, code, sourceAddress, nowSupply, metadata);
        if (Tools.isEmpty(sendOperation)) {
            throw new SDKException(SdkError.OPERATIONS_ONE_ERROR);
        }
        operationList.add(sendOperation);
        return operationList;
    }
}
