package io.bumo.contract.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import io.bumo.common.General;
import io.bumo.contract.ContractService;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.ContractCreateOperation;
import io.bumo.model.request.Operation.ContractInvokeByAssetOperation;
import io.bumo.model.request.Operation.ContractInvokeByBUOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.ContractInfo;

/**
 * @Author riven
 * @Date 2018/7/5 13:21
 */
public class ContractServiceImpl implements ContractService {
    /**
     * @Author riven
     * @Method getInfo
     * @Params [contractGetInfoRequest]
     * @Return io.bumo.model.response.ContractGetInfoResponse
     * @Date 2018/7/5 14:22
     */
    @Override
    public ContractGetInfoResponse getInfo(ContractGetInfoRequest contractGetInfoRequest) {
        ContractGetInfoResponse contractGetInfoResponse = new ContractGetInfoResponse();
        ContractGetInfoResult contractGetInfoResult = new ContractGetInfoResult();
        try {
            String contractAddress = contractGetInfoRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String contractGetInfoUrl = General.accountGetInfoUrl(contractAddress);
            String result = HttpKit.get(contractGetInfoUrl);
            contractGetInfoResponse = JSON.parseObject(result, ContractGetInfoResponse.class);
            SdkError.checkErrorCode(contractGetInfoResponse);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            contractGetInfoResponse.buildResponse(errorCode, errorDesc, contractGetInfoResult);
        } catch (Exception e) {
            contractGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR);
        }

        return contractGetInfoResponse;
    }

    /**
     * @Author riven
     * @Method checkValid
     * @Params [contractCheckValidRequest]
     * @Return io.bumo.model.response.ContractCheckValidResponse
     * @Date 2018/7/5 15:36
     */
    @Override
    public ContractCheckValidResponse checkValid(ContractCheckValidRequest contractCheckValidRequest) {
        ContractCheckValidResponse contractCheckValidResponse = new ContractCheckValidResponse();
        ContractCheckValidResult contractCheckValidResult = new ContractCheckValidResult();
        try {
            contractCheckValidResult.setValid(true);
            ContractGetInfoRequest contractGetInfoRequest = new ContractGetInfoRequest();
            contractGetInfoRequest.setContractAddress(contractCheckValidRequest.getContractAddress());
            ContractGetInfoResponse contractGetInfoResponse = getInfo(contractGetInfoRequest);
            Integer errorCode = contractGetInfoResponse.getErrorCode();
            if (errorCode != 0) {
                String errorDesc = contractGetInfoResponse.getErrorDesc();
                throw new SDKException(errorCode, errorDesc);
            }
            ContractInfo contractInfo = contractGetInfoResponse.getResult().getContract();
            if (contractInfo == null) {
                contractCheckValidResult.setValid(false);
            } else {
                String payload = contractInfo.getPayload();
                if (payload == null || !payload.isEmpty()) {
                    contractCheckValidResult.setValid(false);
                }
            }
            contractCheckValidResponse.buildResponse(SdkError.SUCCESS, contractCheckValidResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            contractCheckValidResponse.buildResponse(errorCode, errorDesc, contractCheckValidResult);
        }
        return contractCheckValidResponse;
    }

    /**
     * @Author riven
     * @Method create
     * @Params [contractCreateOperation]
     * @Return io.bumo.model.response.ContractCreateResponse
     * @Date 2018/7/5 13:31
     */
    public static Chain.Operation create(ContractCreateOperation contractCreateOperation) throws SDKException {
        String sourceAddress = contractCreateOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        Long initBalance = contractCreateOperation.getInitBalance();
        if (initBalance <= 0) {
            throw new SDKException(SdkError.INVALID_INITBALANCE_ERROR);
        }

        Integer type = contractCreateOperation.getType();
        if (type != null && type < 0) {
            throw new SDKException(SdkError.INVALID_CONTRACT_TYPE_ERROR);
        }

        String payload = contractCreateOperation.getPayload();
        if (payload == null || (payload != null && payload.isEmpty())) {
            throw new SDKException(SdkError.PAYLOAD_EMPTY_ERROR);
        }

        String metadata = contractCreateOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }
        String initInput = contractCreateOperation.getInitInput();

        // build operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
        operation.setType(Chain.Operation.Type.CREATE_ACCOUNT);
        if (sourceAddress != null) {
            operation.setSourceAddress(sourceAddress);
        }
        if (metadata != null) {
            operation.setMetadata(ByteString.copyFromUtf8(metadata));
        }

        Chain.OperationCreateAccount.Builder operationCreateContract = operation.getCreateAccountBuilder();
        operationCreateContract.setInitBalance(initBalance);
        if (initInput != null && !initInput.isEmpty()) {
            operationCreateContract.setInitInput(initInput);
        }
        Chain.Contract.Builder contract = operationCreateContract.getContractBuilder();
        if (type != null) {
            contract.setType(Chain.Contract.ContractType.forNumber(type));
        }
        contract.setPayload(payload);
        Chain.AccountPrivilege.Builder accountPrivilege = operationCreateContract.getPrivBuilder();
        accountPrivilege.setMasterWeight(0);
        Chain.AccountThreshold.Builder accountThreshold = accountPrivilege.getThresholdsBuilder();
        accountThreshold.setTxThreshold(1);

        return operation.build();
    }

    /**
     * @Author riven
     * @Method invokeByAsset
     * @Params [contractInvokeByAssetOperation]
     * @Return io.bumo.model.response.ContractInvokeByAssetResponse
     * @Date 2018/7/5 14:30
     */
    public static Chain.Operation invokeByAsset(ContractInvokeByAssetOperation contractInvokeByAssetOperation) throws SDKException {
        String sourceAddress = contractInvokeByAssetOperation.getSourceAddress();
        if (sourceAddress != null && PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String contractAddress = contractInvokeByAssetOperation.getContractAddress();
        if (!PublicKey.isAddressValid(contractAddress)) {
            throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
        }
        if(sourceAddress != null && sourceAddress.equals(contractAddress)) {
            throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
        }
        String code = contractInvokeByAssetOperation.getCode();
        if (code != null && (code.length() < 1 || code.length() > 1024)) {
            throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
        }
        String issuer = contractInvokeByAssetOperation.getIssuer();
        if (issuer != null && !PublicKey.isAddressValid(issuer)) {
            throw new SDKException(SdkError.INVALID_ISSUER_ADDRESS_ERROR);
        }
        Long amount = contractInvokeByAssetOperation.getAmount();
        if (amount != null && amount < 0) {
            throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
        }
        String metadata = contractInvokeByAssetOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }
        String input = contractInvokeByAssetOperation.getInput();

        // build operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
        operation.setType(Chain.Operation.Type.PAY_ASSET);
        if (sourceAddress != null) {
            operation.setSourceAddress(sourceAddress);
        }
        if (metadata != null) {
            operation.setMetadata(ByteString.copyFromUtf8(metadata));
        }

        Chain.OperationPayAsset.Builder operationPayAsset = operation.getPayAssetBuilder();
        operationPayAsset.setDestAddress(contractAddress);
        if (input != null && !input.isEmpty()) {
            operationPayAsset.setInput(input);
        }
        if (code != null && issuer != null &&  amount != null && amount != 0) {
            Chain.Asset.Builder asset = operationPayAsset.getAssetBuilder();
            Chain.AssetKey.Builder assetKey = asset.getKeyBuilder();
            assetKey.setCode(code);
            assetKey.setIssuer(issuer);
            asset.setAmount(amount);
        }
        return operation.build();
    }

    /**
     * @Author riven
     * @Method invokeByBU
     * @Params [contractInvokeByBUOperation]
     * @Return io.bumo.model.response.ContractInvokeByBUResponse
     * @Date 2018/7/5 15:28
     */
    public static Chain.Operation invokeByBU(ContractInvokeByBUOperation contractInvokeByBUOperation) throws SDKException {
        String sourceAddress = contractInvokeByBUOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String contractAddress = contractInvokeByBUOperation.getContractAddress();
        if (!PublicKey.isAddressValid(contractAddress)) {
            throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
        }
        if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
            throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
        }
        Long amount = contractInvokeByBUOperation.getAmount();
        if (amount == null || (amount != null && amount <= 0)) {
            throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
        }
        String metadata = contractInvokeByBUOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }
        String input = contractInvokeByBUOperation.getInput();

        // build operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
        operation.setType(Chain.Operation.Type.PAY_COIN);
        if (sourceAddress != null) {
            operation.setSourceAddress(sourceAddress);
        }
        if (metadata != null) {
            operation.setMetadata(ByteString.copyFromUtf8(metadata));
        }

        Chain.OperationPayCoin.Builder operationPayCoin = operation.getPayCoinBuilder();
        operationPayCoin.setDestAddress(contractAddress);
        operationPayCoin.setAmount(amount);
        if (input != null && !input.isEmpty()) {
            operationPayCoin.setInput(input);
        }

        return operation.build();
    }
}
