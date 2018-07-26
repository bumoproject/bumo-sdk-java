package io.bumo.contract.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

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
            contractGetInfoResponse = getContractInfo(contractAddress);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            contractGetInfoResponse.buildResponse(errorCode, errorDesc, contractGetInfoResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            contractGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, contractGetInfoResult);
        } catch (Exception exception) {
            contractGetInfoResponse.buildResponse(SdkError.SYSTEM_ERROR, contractGetInfoResult);
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
            String contractAddress = contractCheckValidRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isValid = checkContractValid(contractAddress);
            contractCheckValidResult.setValid(isValid);
            contractCheckValidResponse.buildResponse(SdkError.SUCCESS, contractCheckValidResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            contractCheckValidResponse.buildResponse(errorCode, errorDesc, contractCheckValidResult);
        } catch (Exception exception) {
            contractCheckValidResponse.buildResponse(SdkError.SYSTEM_ERROR, contractCheckValidResult);
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
        Chain.Operation.Builder operation;
        try {
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
            String initInput = contractCreateOperation.getInitInput();
            // build operation
            operation = Chain.Operation.newBuilder();
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
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception e) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

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
        Chain.Operation.Builder operation;
        try {
            String sourceAddress = contractInvokeByAssetOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = contractInvokeByAssetOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
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
            if (!checkContractValid(contractAddress)) {
                throw new SDKException(SdkError.CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR);
            }
            String input = contractInvokeByAssetOperation.getInput();

            // build operation
            operation = Chain.Operation.newBuilder();
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
            if (code != null && issuer != null && amount != null && amount != 0) {
                Chain.Asset.Builder asset = operationPayAsset.getAssetBuilder();
                Chain.AssetKey.Builder assetKey = asset.getKeyBuilder();
                assetKey.setCode(code);
                assetKey.setIssuer(issuer);
                asset.setAmount(amount);
            }
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception e) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
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
        Chain.Operation.Builder operation;
        try {
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
            if (amount == null || (amount != null && amount < 0)) {
                throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
            }
            String metadata = contractInvokeByBUOperation.getMetadata();
            if (!checkContractValid(contractAddress)) {
                throw new SDKException(SdkError.CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR);
            }
            String input = contractInvokeByBUOperation.getInput();

            // build operation
            operation = Chain.Operation.newBuilder();
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
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception e) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

        return operation.build();
    }

    /**
     * @Author riven
     * @Method call
     * @Params [contractCallRequest]
     * @Return io.bumo.model.response.ContractCallResponse
     * @Date 2018/7/11 18:50
     */
    @Override
    public ContractCallResponse call(ContractCallRequest contractCallRequest) {
        ContractCallResponse contractCallResponse = new ContractCallResponse();
        ContractCallResult contractCallResult = new ContractCallResult();

        try {
            String sourceAddress = contractCallRequest.getSourceAddress();
            if (sourceAddress != null && !sourceAddress.isEmpty() && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = contractCallRequest.getContractAddress();
            if (contractAddress != null && !contractAddress.isEmpty() && !PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String code = contractCallRequest.getCode();
            if ((null == contractAddress || contractAddress.isEmpty()) && (null == code || code.isEmpty())) {
                throw new SDKException(SdkError.CONTRACTADDRESS_CODE_BOTH_NULL_ERROR);
            }
            Long feeLimit = contractCallRequest.getFeeLimit();
            if (null == feeLimit || (feeLimit != null && feeLimit < 1)) {
                throw new SDKException(SdkError.INVALID_FEELIMIT_ERROR);
            }
            Integer optType = contractCallRequest.getOptType();
            if (null == optType || (optType != null && (optType < 0 || optType > 2))) {
                throw new SDKException(SdkError.INVALID_OPTTYPE_ERROR);
            }
            String input = contractCallRequest.getInput();
            Long contractBalance = contractCallRequest.getContractBalance();
            Long gasPrice = contractCallRequest.getGasPrice();

            JSONObject params = new JSONObject();
            params.put("opt_type", optType);
            params.put("fee_limit", feeLimit);
            if (sourceAddress != null) {
                params.put("source_address", sourceAddress);
            }
            if (contractAddress != null) {
                params.put("contract_address", contractAddress);
            }
            if (code != null) {
                params.put("code", code);
            }
            if (input != null) {
                params.put("input", input);
            }
            if (contractBalance != null) {
                params.put("contract_balance", input);
            }
            if (gasPrice != null) {
                params.put("gas_price", gasPrice);
            }
            // call contract
            String contractCallUrl = General.contractCallUrl();
            String result = HttpKit.post(contractCallUrl, params.toJSONString());
            contractCallResponse = JSONObject.parseObject(result, ContractCallResponse.class);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            contractCallResponse.buildResponse(errorCode, errorDesc, contractCallResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            contractCallResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, contractCallResult);
        } catch (Exception exception) {
            contractCallResponse.buildResponse(SdkError.SYSTEM_ERROR, contractCallResult);
        }
        return contractCallResponse;
    }

    private static ContractGetInfoResponse getContractInfo(String contractAddress) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException, SDKException {
        ContractGetInfoResponse contractGetInfoResponse;
        String contractGetInfoUrl = General.accountGetInfoUrl(contractAddress);
        String result = HttpKit.get(contractGetInfoUrl);
        contractGetInfoResponse = JSON.parseObject(result, ContractGetInfoResponse.class);
        Integer errorCode = contractGetInfoResponse.getErrorCode();
        String errorDesc = contractGetInfoResponse.getErrorDesc();
        if (errorCode != null && errorCode == 4) {
            throw new SDKException(errorCode, (null == errorDesc ? "contract account (" + contractAddress + ") doest not exist" : errorDesc));
        }
        SdkError.checkErrorCode(contractGetInfoResponse);
        ContractInfo contractInfo = contractGetInfoResponse.getResult().getContract();
        if (contractInfo == null) {
            throw new SDKException(SdkError.CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR);
        }
        String payLoad = contractInfo.getPayload();
        if (null == payLoad || (payLoad != null && payLoad.isEmpty())) {
            throw new SDKException(SdkError.CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR);
        }
        return contractGetInfoResponse;
    }

    private static boolean checkContractValid(String contractAddress) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        boolean isValid = false;
        try {
            getContractInfo(contractAddress);
            isValid = true;
        } catch (SDKException sdkException) {
        }
        return isValid;
    }
}
