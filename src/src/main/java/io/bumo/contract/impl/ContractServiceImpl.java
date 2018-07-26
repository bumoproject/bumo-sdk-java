package io.bumo.contract.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.bumo.common.Constant;
import io.bumo.common.General;
import io.bumo.common.Tools;
import io.bumo.contract.ContractService;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
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
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            Long initBalance = contractCreateOperation.getInitBalance();
            if (Tools.isEmpty(initBalance) || initBalance <= 0) {
                throw new SDKException(SdkError.INVALID_INITBALANCE_ERROR);
            }
            Integer type = contractCreateOperation.getType();
            if (!Tools.isEmpty(type) && type < 0) {
                throw new SDKException(SdkError.INVALID_CONTRACT_TYPE_ERROR);
            }
            String payload = contractCreateOperation.getPayload();
            if (Tools.isEmpty(payload)) {
                throw new SDKException(SdkError.PAYLOAD_EMPTY_ERROR);
            }
            String metadata = contractCreateOperation.getMetadata();
            String initInput = contractCreateOperation.getInitInput();
            // build operation
            operation = Chain.Operation.newBuilder();
            operation.setType(Chain.Operation.Type.CREATE_ACCOUNT);
            if (!Tools.isEmpty(sourceAddress)) {
                operation.setSourceAddress(sourceAddress);
            }
            if (!Tools.isEmpty(metadata)) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }

            Chain.OperationCreateAccount.Builder operationCreateContract = operation.getCreateAccountBuilder();
            operationCreateContract.setInitBalance(initBalance);
            if (!Tools.isEmpty(initInput)) {
                operationCreateContract.setInitInput(initInput);
            }
            Chain.Contract.Builder contract = operationCreateContract.getContractBuilder();
            if (!Tools.isEmpty(type)) {
                Chain.Contract.ContractType contractType = Chain.Contract.ContractType.forNumber(type);
                if (Tools.isEmpty(contractType)) {
                    throw new SDKException(SdkError.INVALID_CONTRACT_TYPE_ERROR);
                }
                contract.setType(contractType);
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
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = contractInvokeByAssetOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String code = contractInvokeByAssetOperation.getCode();
            if (!Tools.isNULL(code) && (code.length() < 1 || code.length() > 1024)) {
                throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
            }
            String issuer = contractInvokeByAssetOperation.getIssuer();
            if (!Tools.isEmpty(issuer) && !PublicKey.isAddressValid(issuer)) {
                throw new SDKException(SdkError.INVALID_ISSUER_ADDRESS_ERROR);
            }
            Long assetAmount = contractInvokeByAssetOperation.getAssetAmount();
            if (!Tools.isEmpty(assetAmount) && assetAmount < 0) {
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
            if (!Tools.isEmpty(sourceAddress)) {
                operation.setSourceAddress(sourceAddress);
            }
            if (!Tools.isEmpty(metadata)) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }

            Chain.OperationPayAsset.Builder operationPayAsset = operation.getPayAssetBuilder();
            operationPayAsset.setDestAddress(contractAddress);
            if (!Tools.isEmpty(input)) {
                operationPayAsset.setInput(input);
            }
            if (code != null && issuer != null && assetAmount != null && assetAmount != 0) {
                Chain.Asset.Builder asset = operationPayAsset.getAssetBuilder();
                Chain.AssetKey.Builder assetKey = asset.getKeyBuilder();
                assetKey.setCode(code);
                assetKey.setIssuer(issuer);
                asset.setAmount(assetAmount);
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
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = contractInvokeByBUOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            Long buAmount = contractInvokeByBUOperation.getBuAmount();
            if (Tools.isEmpty(buAmount) || buAmount < 0) {
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
            if (!Tools.isEmpty(sourceAddress)) {
                operation.setSourceAddress(sourceAddress);
            }
            if (!Tools.isEmpty(metadata)) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            Chain.OperationPayCoin.Builder operationPayCoin = operation.getPayCoinBuilder();
            operationPayCoin.setDestAddress(contractAddress);
            operationPayCoin.setAmount(buAmount);
            if (!Tools.isEmpty(input)) {
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
            if (!Tools.isEmpty(sourceAddress) && !sourceAddress.isEmpty() && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = contractCallRequest.getContractAddress();
            if (!Tools.isNULL(contractAddress) && !contractAddress.isEmpty() && !PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String code = contractCallRequest.getCode();
            if (Tools.isEmpty(contractAddress) && Tools.isEmpty(code)) {
                throw new SDKException(SdkError.CONTRACTADDRESS_CODE_BOTH_NULL_ERROR);
            }
            Long feeLimit = contractCallRequest.getFeeLimit();
            if (Tools.isEmpty(feeLimit) || feeLimit < Constant.FEE_LIMIT_MIN) {
                throw new SDKException(SdkError.INVALID_FEELIMIT_ERROR);
            }
            Integer optType = contractCallRequest.getOptType();
            if (Tools.isEmpty(optType) || (optType < Constant.OPT_TYPE_MIN || optType > Constant.OPT_TYPE_MAX)) {
                throw new SDKException(SdkError.INVALID_OPTTYPE_ERROR);
            }
            String input = contractCallRequest.getInput();
            Long contractBalance = contractCallRequest.getContractBalance();
            Long gasPrice = contractCallRequest.getGasPrice();
            contractCallResponse = callContract(sourceAddress, contractAddress, optType, code, input, contractBalance, gasPrice, feeLimit);
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

    public static ContractCallResponse callContract(String sourceAddress, String contractAddress, Integer optType, String code,
            String input, Long contractBalance, Long gasPrice, Long feeLimit)
            throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        JSONObject params = new JSONObject();
        params.put("opt_type", optType);
        params.put("fee_limit", feeLimit);
        if (!Tools.isEmpty(sourceAddress)) {
            params.put("source_address", sourceAddress);
        }
        if (!Tools.isEmpty(contractAddress)) {
            params.put("contract_address", contractAddress);
        }
        if (!Tools.isEmpty(code)) {
            params.put("code", code);
        }
        if (!Tools.isEmpty(input)) {
            params.put("input", input);
        }
        if (!Tools.isEmpty(contractBalance)) {
            params.put("contract_balance", input);
        }
        if (!Tools.isEmpty(gasPrice)) {
            params.put("gas_price", gasPrice);
        }
        // call contract
        String contractCallUrl = General.contractCallUrl();
        String result = HttpKit.post(contractCallUrl, params.toJSONString());
        return JSONObject.parseObject(result, ContractCallResponse.class);
    }

    private static ContractGetInfoResponse getContractInfo(String contractAddress) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException, SDKException {
        ContractGetInfoResponse contractGetInfoResponse;
        String contractGetInfoUrl = General.accountGetInfoUrl(contractAddress);
        String result = HttpKit.get(contractGetInfoUrl);
        contractGetInfoResponse = JSON.parseObject(result, ContractGetInfoResponse.class);
        Integer errorCode = contractGetInfoResponse.getErrorCode();
        String errorDesc = contractGetInfoResponse.getErrorDesc();
        if (!Tools.isEmpty(errorCode) && errorCode == 4) {
            throw new SDKException(errorCode, (null == errorDesc ? "contract account (" + contractAddress + ") doest not exist" : errorDesc));
        }
        SdkError.checkErrorCode(contractGetInfoResponse);
        ContractInfo contractInfo = contractGetInfoResponse.getResult().getContract();
        if (Tools.isEmpty(contractInfo)) {
            throw new SDKException(SdkError.CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR);
        }
        String payLoad = contractInfo.getPayload();
        if (Tools.isEmpty(payLoad)) {
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
