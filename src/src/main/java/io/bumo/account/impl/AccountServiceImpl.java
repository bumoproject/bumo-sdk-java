package io.bumo.account.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import io.bumo.account.AccountService;
import io.bumo.common.General;
import io.bumo.crypto.http.HttpKit;
import io.bumo.exception.SDKException;
import io.bumo.model.request.Operation.AccountActiviateOperation;
import io.bumo.model.request.Operation.AccountSetMetadataOperation;
import io.bumo.model.request.Operation.AccountSetPrivilegeOperation;
import io.bumo.model.response.result.data.Signer;
import io.bumo.model.response.result.data.TypeThreshold;
import io.bumo.model.request.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/3 12:34
 */

public class AccountServiceImpl implements AccountService {

    /**
     * @Author riven
     * @Method checkValid
     * @Params [accountCheckValidRequest]
     * @Return io.bumo.model.response.AccountCheckValidResponse
     * @Date 2018/7/4 18:19
     */
    @Override
    public AccountCheckValidResponse checkValid(AccountCheckValidRequest accountCheckValidRequest) {
        AccountCheckValidResponse accountCheckValidResponse = new AccountCheckValidResponse();
        AccountCheckValidResult accountCheckValidResult = new AccountCheckValidResult();
        boolean isValid = false;
        try{
        	isValid = PublicKey.isAddressValid(accountCheckValidRequest.getAddress());
        }catch(Exception e){
        	isValid =false;
        }
        accountCheckValidResult.setValid(isValid);
        accountCheckValidResponse.buildResponse(SdkError.SUCCESS, accountCheckValidResult);
        return accountCheckValidResponse;
    }

    /**
     * @Author riven
     * @Method create
     * @Params []
     * @Return io.bumo.model.response.AccountCreateResponse
     * @Date 2018/7/4 18:19
     */
    @Override
    public AccountCreateResponse create() {
        AccountCreateResponse accountCreateResponse = new AccountCreateResponse();

        AccountCreateResult accountCreateResult = new AccountCreateResult();
        try {
            PrivateKey privateKey = new PrivateKey();
            accountCreateResult.setPrivateKey(privateKey.getEncPrivateKey());
            accountCreateResult.setPublicKey(privateKey.getEncPublicKey());
            accountCreateResult.setAddress(privateKey.getEncAddress());
            accountCreateResponse.buildResponse(SdkError.SUCCESS, accountCreateResult);
        } catch (Exception e) {
            accountCreateResponse.buildResponse(SdkError.ACCOUNT_CREATE_ERROR, accountCreateResult);
        }
        return accountCreateResponse;
    }

    /**
     * @Author riven
     * @Method getInfo
     * @Params [accountGetInfoRequest]
     * @Return io.bumo.model.response.AccountGetInfoResponse
     * @Date 2018/7/4 18:19
     */
    @Override
    public AccountGetInfoResponse getInfo(AccountGetInfoRequest accountGetInfoRequest) {
        AccountGetInfoResponse accountGetInfoResponse = new AccountGetInfoResponse();

        AccountGetInfoResult accountGetInfoResult = new AccountGetInfoResult();
        try {
            String address = accountGetInfoRequest.getAddress();
            try{
            	if (!PublicKey.isAddressValid(address)) {
                    throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
                }
            }catch(Exception e){
            	throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
            }
            
            String accountGetInfoUrl = General.accountGetInfoUrl(address);
            String result = HttpKit.get(accountGetInfoUrl);
            accountGetInfoResponse = JSON.parseObject(result, AccountGetInfoResponse.class);
            SdkError.checkErrorCode(accountGetInfoResponse);
            Integer errorCode = accountGetInfoResponse.getErrorCode();
            if (errorCode != null && errorCode.intValue() == 4) {
                throw new SDKException(errorCode, "AccountService (" + address +") not exist");
            }
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            accountGetInfoResponse.buildResponse(errorCode, errorDesc, accountGetInfoResult);
        }
        catch (Exception e) {
            accountGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, accountGetInfoResult);
        }

        return accountGetInfoResponse;
    }

    /**
     * @Author riven
     * @Method getNonce
     * @Params [accountGetNonceRequest]
     * @Return io.bumo.model.response.AccountGetNonceResponse
     * @Date 2018/7/4 18:19
     */
    @Override
    public AccountGetNonceResponse getNonce(AccountGetNonceRequest accountGetNonceRequest) {
        AccountGetNonceResponse accountGetNonceResponse = new AccountGetNonceResponse();

        AccountGetNonceResult accountGetNonceResult = new AccountGetNonceResult();
        try {
            String address = accountGetNonceRequest.getAddress();
            if (!PublicKey.isAddressValid(address)) {
                throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
            }
            String accountGetInfoUrl = General.accountGetInfoUrl(address);
            String result = HttpKit.get(accountGetInfoUrl);
            accountGetNonceResponse = JSON.parseObject(result, AccountGetNonceResponse.class);
            SdkError.checkErrorCode(accountGetNonceResponse);
            Integer errorCode = accountGetNonceResponse.getErrorCode();
            if (errorCode != null && errorCode.intValue() == 4) {
                throw new SDKException(errorCode, "AccountService (" + address +") not exist");
            }
            if (accountGetNonceResponse.getResult().getNonce() == null) {
                accountGetNonceResponse.getResult().setNonce(0L);
            }
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            accountGetNonceResponse.buildResponse(errorCode, errorDesc, accountGetNonceResult);
        } catch (Exception e) {
        accountGetNonceResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, accountGetNonceResult);
    }

        return accountGetNonceResponse;
    }

    /**
     * @Author riven
     * @Method getBalance
     * @Params [accountGetBalanceRequest]
     * @Return io.bumo.model.response.AccountGetBalanceResponse
     * @Date 2018/7/4 18:19
     */
    @Override
    public AccountGetBalanceResponse getBalance(AccountGetBalanceRequest accountGetBalanceRequest) {
        AccountGetBalanceResponse accountGetBalanceResponse = new AccountGetBalanceResponse();
        AccountGetBalanceResult accountGetBalanceResult = new AccountGetBalanceResult();
        try {
            String address = accountGetBalanceRequest.getAddress();
            try{
            	 if (!PublicKey.isAddressValid(address)) {
                     throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
                 }
            }catch(Exception e){
            	throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
            }
           
            String accountGetInfoUrl = General.accountGetInfoUrl(address);
            String result = HttpKit.get(accountGetInfoUrl);
            accountGetBalanceResponse = JSON.parseObject(result, AccountGetBalanceResponse.class);
            SdkError.checkErrorCode(accountGetBalanceResponse);
            Integer errorCode = accountGetBalanceResponse.getErrorCode();
            if (errorCode != null && errorCode.intValue() == 4) {
                throw new SDKException(errorCode, "AccountService (" + address +") not exist");
            }
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            accountGetBalanceResponse.buildResponse(errorCode, errorDesc, accountGetBalanceResult);
        } catch (Exception e) {
            accountGetBalanceResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, accountGetBalanceResult);
        }

        return accountGetBalanceResponse;
    }

    /**
     * @Author riven
     * @Method getAssets
     * @Params [accountGetAssetsRequest]
     * @Return io.bumo.model.response.AccountGetAssetsResponse
     * @Date 2018/7/5 12:00
     */
    public AccountGetAssetsResponse getAssets(AccountGetAssetsRequest accountGetAssetsRequest) {
        AccountGetAssetsResponse accountGetAssetsResponse = new AccountGetAssetsResponse();
        AccountGetAssetsResult accountGetAssetsResult = new AccountGetAssetsResult();
        try {
            String address = accountGetAssetsRequest.getAddress();
            if (!PublicKey.isAddressValid(address)) {
                throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
            }
            String accountGetInfoUrl = General.accountGetAssetsUrl(address);
            String result = HttpKit.get(accountGetInfoUrl);
            accountGetAssetsResponse = JSON.parseObject(result, AccountGetAssetsResponse.class);
            SdkError.checkErrorCode(accountGetAssetsResponse);
            Integer errorCode = accountGetAssetsResponse.getErrorCode();
            if (errorCode != null && errorCode.intValue() == 4) {
                throw new SDKException(errorCode, "AccountService (" + address +") not exist");
            }
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            accountGetAssetsResponse.buildResponse(errorCode, errorDesc, accountGetAssetsResult);
        } catch (Exception e) {
            accountGetAssetsResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, accountGetAssetsResult);
        }

        return accountGetAssetsResponse;
    }

    /**
     * @Author riven
     * @Method activate
     * @Params [accountActivateRequest]
     * @Return io.bumo.model.response.AccountActivateResponse
     * @Date 2018/7/4 18:19
     */
    public static Chain.Operation activate(AccountActiviateOperation accountActiviateOperation) throws SDKException {
        String sourceAddress = accountActiviateOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String destAddress = accountActiviateOperation.getDestAddress();
        if (!PublicKey.isAddressValid(destAddress)) {
            throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
        }
        if (sourceAddress != null && sourceAddress.equals(destAddress)) {
            throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
        }
        Long initBalance = accountActiviateOperation.getInitBalance();
        if (initBalance <= 0) {
            throw new SDKException(SdkError.INVALID_INITBALANCE_ERROR);
        }
        String metadata = accountActiviateOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }

        // build Operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
        operation.setType(Chain.Operation.Type.CREATE_ACCOUNT);
        if (sourceAddress != null) {
            operation.setSourceAddress(sourceAddress);
        }
        if (metadata != null) {
            operation.setMetadata(ByteString.copyFromUtf8(metadata));
        }
        Chain.OperationCreateAccount.Builder operationCreateAccount = operation.getCreateAccountBuilder();
        operationCreateAccount.setDestAddress(destAddress);
        operationCreateAccount.setInitBalance(initBalance);
        Chain.AccountPrivilege.Builder accountPrivilege = operationCreateAccount.getPrivBuilder();
        accountPrivilege.setMasterWeight(1);
        Chain.AccountThreshold.Builder accountThreshold = accountPrivilege.getThresholdsBuilder();
        accountThreshold.setTxThreshold(1);

        return operation.build();
    }

    /**
     * @Author riven
     * @Method setMetadata
     * @Params [accountSetMetadataRequest]
     * @Return io.bumo.model.response.AccountSetMetadataResponse
     * @Date 2018/7/4 18:27
     */
    public static Chain.Operation setMetadata(AccountSetMetadataOperation accountSetMetadataOperation) throws SDKException {
        String sourceAddress = accountSetMetadataOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String key = accountSetMetadataOperation.getKey();
        if (key == null || (key != null && key.length() < 1 || key.length() > 1024)) {
            throw new SDKException(SdkError.INVALID_DATAKEY_ERROR);
        }
        String value = accountSetMetadataOperation.getValue();
        if (value == null || (value != null && value.length() < 0 || value.length() > 256000)) {
            throw new SDKException(SdkError.INVALID_DATAVALUE_ERROR);
        }
        Long version = accountSetMetadataOperation.getVersion();
        if (version < 0) {
            throw new SDKException(SdkError.INVALID_DATAVERSION_ERROR);
        }

        Boolean deleteFlag = accountSetMetadataOperation.getDeleteFlag();

        String metadata = accountSetMetadataOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }

        // build Operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
        operation.setType(Chain.Operation.Type.SET_METADATA);
        if (sourceAddress != null) {
            operation.setSourceAddress(sourceAddress);
        }
        if (metadata != null) {
            operation.setMetadata(ByteString.copyFromUtf8(metadata));
        }
        Chain.OperationSetMetadata.Builder operationSetMetadata = operation.getSetMetadataBuilder();
        operationSetMetadata.setKey(key);
        operationSetMetadata.setValue(value);
        if (version != null && version > 0) {
            operationSetMetadata.setVersion(version);
        }
        if (deleteFlag != null) {
            operationSetMetadata.setDeleteFlag(deleteFlag);
        }

        return operation.build();
    }

    /**
     * @Author riven
     * @Method setPrivilege
     * @Params [accountSetPrivilegeRequest]
     * @Return io.bumo.model.response.AccountSetPrivilegeResponse
     * @Date 2018/7/5 00:45
     */
    public static Chain.Operation setPrivilege(AccountSetPrivilegeOperation accountSetPrivilegeOperation) throws SDKException {
        String sourceAddress = accountSetPrivilegeOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String masterWeight = accountSetPrivilegeOperation.getMasterWeight();
        long maxInt = 1;
        if (masterWeight == null || (masterWeight != null && Long.valueOf(masterWeight) < 0 || Long.valueOf(masterWeight) > ((maxInt<<32) - 1))) {
            throw new SDKException(SdkError.INVALID_MASTERWEIGHT_ERROR);
        }
        String txThreshold = accountSetPrivilegeOperation.getTxThreshold();
        if (txThreshold == null || (txThreshold != null && Long.valueOf(masterWeight) < 0 || Long.valueOf(masterWeight) > ((maxInt<<(64-1)) - 1))) {
            throw new SDKException(SdkError.INVALID_TX_THRESHOLD_ERROR);
        }
        String metadata = accountSetPrivilegeOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }

        // build Operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
        operation.setType(Chain.Operation.Type.SET_PRIVILEGE);
        if (sourceAddress != null) {
            operation.setSourceAddress(sourceAddress);
        }
        if (metadata != null) {
            operation.setMetadata(ByteString.copyFromUtf8(metadata));
        }
        Chain.OperationSetPrivilege.Builder operationSetPrivilege = operation.getSetPrivilegeBuilder();
        if(masterWeight != null) {
            operationSetPrivilege.setMasterWeight(masterWeight);
        }
        if (txThreshold != null) {
            operationSetPrivilege.setTxThreshold(txThreshold);
        }

        // add signers
        Signer[] signers = accountSetPrivilegeOperation.getSigners();
        if (signers != null && signers.length > 0) {
            int i = 0;
            int signersLength = signers.length;
            for (; i < signersLength; i++) {
                Signer signer = signers[i];
                String signerAddress = signer.getAddress();
                if(!PublicKey.isAddressValid(signerAddress)) {
                    throw new SDKException(SdkError.INVALID_SIGNER_ADDRESS_ERROR);
                }
                Long signerWeight = signer.getWeight();
                if (signerWeight == null || (signerWeight != null && (signerWeight < 0 || signerWeight > (maxInt << (64 - 1)) - 1))) {
                    throw new SDKException(SdkError.INVALID_SIGNER_WEIGHT_ERROR);
                }
                Chain.Signer.Builder signerBuilder = operationSetPrivilege.addSignersBuilder();
                signerBuilder.setAddress(signerAddress);
                signerBuilder.setWeight(signerWeight);
            }
        }

        // add type_thresholds
        TypeThreshold[] typeThresholds = accountSetPrivilegeOperation.getTypeThresholds();
        if (typeThresholds != null && typeThresholds.length > 0) {
            int i = 0;
            int typeThresholdsLength = typeThresholds.length;
            for (; i < typeThresholdsLength; i++) {
                TypeThreshold typeThreshold = typeThresholds[i];
                Integer type = typeThreshold.getType();
                if (type == null || (type != null && (type < 1 || type > 100))) {
                    throw new SDKException(SdkError.INVALID_OPERATION_TYPE_ERROR);
                }
                Long threshold = typeThreshold.getThreshold();
                if (threshold == null || (threshold != null && (threshold < 0 || threshold > (maxInt << (64 - 1)) - 1))) {
                    throw new SDKException(SdkError.INVALID_TYPE_THRESHOLD_ERROR);
                }
                Chain.OperationTypeThreshold.Builder typeThresholdBuilder = operationSetPrivilege.addTypeThresholdsBuilder();
                typeThresholdBuilder.setType(Chain.Operation.Type.forNumber(type));
                typeThresholdBuilder.setThreshold(threshold);
            }
        }


        return operation.build();
    }
}
