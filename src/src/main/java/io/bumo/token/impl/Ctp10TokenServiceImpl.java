package io.bumo.token.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.bumo.token.Ctp10TokenService;
import io.bumo.common.Constant;
import io.bumo.common.General;
import io.bumo.common.Tools;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.Operation.*;
import io.bumo.model.request.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.ContractInfo;
import io.bumo.model.response.result.data.MetadataInfo;
import io.bumo.model.response.result.data.TokenInfo;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Pattern;

/**
 * @Author riven
 * @Date 2018/7/6 11:08
 */
public class Ctp10TokenServiceImpl implements Ctp10TokenService {
    /**
     * @Author riven
     * @Method issue
     * @Params [tokenIssueResult]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation issue(Ctp10TokenIssueOperation ctp10TokenIssueOperation) throws SDKException {
        Chain.Operation.Builder operation;
        try {
            String sourceAddress = ctp10TokenIssueOperation.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            Long initBalance = ctp10TokenIssueOperation.getInitBalance();
            if (Tools.isEmpty(initBalance) || initBalance <= 0) {
                throw new SDKException(SdkError.INVALID_INITBALANCE_ERROR);
            }
            String name = ctp10TokenIssueOperation.getName();
            if (Tools.isEmpty(name) || name.length() > Constant.TOKEN_NAME_MAX) {
                throw new SDKException(SdkError.INVALID_TOKEN_NAME_ERROR);
            }
            String symbol = ctp10TokenIssueOperation.getSymbol();
            if (Tools.isEmpty(symbol) || symbol.length() > Constant.TOKEN_SYMBOL_MAX) {
                throw new SDKException(SdkError.INVALID_TOKEN_SYMBOL_ERROR);
            }
            Integer decimals = ctp10TokenIssueOperation.getDecimals();
            if (Tools.isEmpty(decimals) || decimals < Constant.TOKEN_DECIMALS_MIN || decimals > Constant.TOKEN_DECIMALS_MAX) {
                throw new SDKException(SdkError.INVALID_TOKEN_DECIMALS_ERROR);
            }
            String supply = ctp10TokenIssueOperation.getSupply();
            if (Tools.isEmpty(supply)) {
                throw new SDKException(SdkError.INVALID_TOKEN_TOTALSUPPLY_ERROR);
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(supply).matches();
            BigInteger bigInteger = new BigInteger(supply);
            if (!isNumber || bigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_TOTALSUPPLY_ERROR);
            }
            String metadata = ctp10TokenIssueOperation.getMetadata();
            String payload = Constant.TOKEN_PAYLOAD;
            // build initInput
            JSONObject initInput = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("name", name);
            params.put("symbol", symbol);
            params.put("decimals", decimals);
            params.put("supply", supply);
            initInput.put("params", params);

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
                operationCreateContract.setInitInput(initInput.toJSONString());
            }
            Chain.Contract.Builder contract = operationCreateContract.getContractBuilder();
            contract.setPayload(payload);
            Chain.AccountPrivilege.Builder accountPrivilege = operationCreateContract.getPrivBuilder();
            accountPrivilege.setMasterWeight(0);
            Chain.AccountThreshold.Builder accountThreshold = accountPrivilege.getThresholdsBuilder();
            accountThreshold.setTxThreshold(1);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

        return operation.build();

    }

    /**
     * @Author riven
     * @Method transfer
     * @Params [tokenTransferOperation]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation transfer(Ctp10TokenTransferOperation ctp10TokenTransferOperation, String transSourceAddress) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = ctp10TokenTransferOperation.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = ctp10TokenTransferOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if ((!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) || transSourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String destAddress = ctp10TokenTransferOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            if ((!Tools.isEmpty(sourceAddress) && sourceAddress.equals(destAddress) || transSourceAddress.equals(destAddress))) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String tokenAmount = ctp10TokenTransferOperation.getTokenAmount();
            if (Tools.isEmpty(tokenAmount)) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(tokenAmount).matches();
            BigInteger bigInteger = new BigInteger(tokenAmount);
            if (!isNumber || bigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = ctp10TokenTransferOperation.getMetadata();
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            // build init
            JSONObject input = new JSONObject();
            input.put("method", "transfer");
            JSONObject params = new JSONObject();
            params.put("to", destAddress);
            params.put("value", tokenAmount);
            input.put("params", params);

            // invoke contract
            operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

        return operation;
    }

    /**
     * @Author riven
     * @Method transferFrom
     * @Params [tokenTransferFromOperation]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation transferFrom(Ctp10TokenTransferFromOperation ctp10TokenTransferFromOperation, String transSourceAddress) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = ctp10TokenTransferFromOperation.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = ctp10TokenTransferFromOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String fromAddress = ctp10TokenTransferFromOperation.getFromAddress();
            if (!PublicKey.isAddressValid(fromAddress)) {
                throw new SDKException(SdkError.INVALID_FROMADDRESS_ERROR);
            }
            if ((!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) || transSourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String destAddress = ctp10TokenTransferFromOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            if (fromAddress.equals(destAddress)) {
                throw new SDKException(SdkError.FROMADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String tokenAmount = ctp10TokenTransferFromOperation.getTokenAmount();
            if (Tools.isEmpty(tokenAmount)) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(tokenAmount).matches();
            BigInteger bigInteger = new BigInteger(tokenAmount);
            if (!isNumber || bigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = ctp10TokenTransferFromOperation.getMetadata();
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            input.put("method", "transferFrom");
            JSONObject params = new JSONObject();
            params.put("from", fromAddress);
            params.put("to", destAddress);
            params.put("value", tokenAmount);
            input.put("params", params);

            // invoke contract
            operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }
        return operation;
    }

    /**
     * @Author riven
     * @Method approve
     * @Params [tokenApproveOperation]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation approve(Ctp10TokenApproveOperation ctp10TokenApproveOperation) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = ctp10TokenApproveOperation.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = ctp10TokenApproveOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String spender = ctp10TokenApproveOperation.getSpender();
            if (!PublicKey.isAddressValid(spender)) {
                throw new SDKException(SdkError.INVALID_SPENDER_ERROR);
            }
            String tokenAmount = ctp10TokenApproveOperation.getTokenAmount();
            if (Tools.isEmpty(tokenAmount)) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(tokenAmount).matches();
            BigInteger bigInteger = new BigInteger(tokenAmount);
            if (!isNumber || bigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = ctp10TokenApproveOperation.getMetadata();
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            input.put("method", "approve");
            JSONObject params = new JSONObject();
            params.put("spender", spender);
            params.put("value", tokenAmount);
            input.put("params", params);
            // invoke contract
            operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }
        return operation;
    }

    /**
     * @Author riven
     * @Method assign
     * @Params [ctp10TokenAssignResponse]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation assign(Ctp10TokenAssignOperation ctp10TokenAssignResponse) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = ctp10TokenAssignResponse.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = ctp10TokenAssignResponse.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String destAddress = ctp10TokenAssignResponse.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            String tokenAmount = ctp10TokenAssignResponse.getTokenAmount();
            if (Tools.isEmpty(tokenAmount)) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(tokenAmount).matches();
            BigInteger bigInteger = new BigInteger(tokenAmount);
            if (!isNumber || bigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = ctp10TokenAssignResponse.getMetadata();
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            input.put("method", "assign");
            JSONObject params = new JSONObject();
            params.put("to", destAddress);
            params.put("value", tokenAmount);
            input.put("params", params);
            // invoke contract
            operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }
        return operation;
    }

    /**
     * @Author riven
     * @Method changeOwner
     * @Params [tokenChangeOwnerOperation]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation changeOwner(Ctp10TokenChangeOwnerOperation ctp10TokenChangeOwnerOperation) {
        Chain.Operation operation;
        try {
            String sourceAddress = ctp10TokenChangeOwnerOperation.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = ctp10TokenChangeOwnerOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (!Tools.isEmpty(sourceAddress) && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String tokenOwner = ctp10TokenChangeOwnerOperation.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                throw new SDKException(SdkError.INVALID_TOKENOWNER_ERROR);
            }
            String metadata = ctp10TokenChangeOwnerOperation.getMetadata();
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            input.put("method", "changeOwner");
            JSONObject params = new JSONObject();
            params.put("address", tokenOwner);
            input.put("params", params);
            // invoke contract
            operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }
        return operation;
    }

    /**
     * @Author riven
     * @Method checkContractValid
     * @Params [contractAddress]
     * @Return boolean
     * @Date 2018/7/10 11:41
     */
    private static boolean checkTokenValid(String contractAddress) throws SDKException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String key = "global_attribute";

        String accountGetInfoUrl = General.accountGetMetadataUrl(contractAddress, key);
        String result = HttpKit.get(accountGetInfoUrl);

        TokenMessageResponse tokenMessageResponse;
        TokenMessageResult tokenMessageResult;
        tokenMessageResponse = JSON.parseObject(result, TokenMessageResponse.class);
        Integer errorCode = tokenMessageResponse.getErrorCode();
        String errorDesc = tokenMessageResponse.getErrorDesc();
        if (!Tools.isEmpty(errorCode) && errorCode == 4) {
            throw new SDKException(errorCode, (Tools.isEmpty(errorDesc) ? "Account (" + contractAddress + ") not exist" : errorDesc));
        }
        SdkError.checkErrorCode(tokenMessageResponse);
        boolean isValid = false;
        do {
            tokenMessageResult = tokenMessageResponse.getResult();
            ContractInfo contractInfo = tokenMessageResult.getContract();
            MetadataInfo[] metadataInfos = tokenMessageResult.getMetadatas();
            if (Tools.isEmpty(contractInfo) || Tools.isEmpty(contractInfo.getPayload()) || Tools.isEmpty(metadataInfos)) {
                break;
            }
            String tokenInfoJson = metadataInfos[0].getValue();
            if (Tools.isEmpty(tokenInfoJson)) {
                break;
            }
            TokenInfo tokenInfo = JSONObject.parseObject(tokenInfoJson, TokenInfo.class);
            String ctp = tokenInfo.getCtp();
            if (Tools.isEmpty(ctp)) {
                break;
            }
            Pattern ctpPattern = Pattern.compile("^([1-9]*)+(.[0-9]{1,2})?$");
            boolean isCtp = ctpPattern.matcher(ctp).matches();
            if (!isCtp) {
                break;
            }
            String name = tokenInfo.getName();
            if (Tools.isEmpty(name) || name.length() > Constant.TOKEN_NAME_MAX) {
                break;
            }
            String symbol = tokenInfo.getSymbol();
            if (Tools.isEmpty(symbol) || symbol.length() > Constant.TOKEN_SYMBOL_MAX) {
                break;
            }
            Integer decimals = tokenInfo.getDecimals();
            if (Tools.isEmpty(decimals) || decimals < Constant.TOKEN_DECIMALS_MIN || decimals > Constant.TOKEN_DECIMALS_MAX) {
                break;
            }
            String totalSupply = tokenInfo.getTotalSupply();
            if (Tools.isEmpty(totalSupply)) {
                break;
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(totalSupply).matches();
            if (!isNumber || (isNumber && Long.valueOf(totalSupply) < 1)) {
                break;
            }
            String tokenOwner = tokenInfo.getContractOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                break;
            }
            isValid = true;
        } while (false);
        return isValid;
    }

    /**
     * @Author riven
     * @Method invokeContract
     * @Params [sourceAddress, contractAddress, input, metadata]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    private static Chain.Operation invokeContract(String sourceAddress, String contractAddress, String input, String metadata) throws SDKException {
        ContractInvokeByBUOperation contractInvokeByBUOperation = new ContractInvokeByBUOperation();
        if (!Tools.isEmpty(sourceAddress)) {
            contractInvokeByBUOperation.setSourceAddress(sourceAddress);
        }
        contractInvokeByBUOperation.setContractAddress(contractAddress);
        contractInvokeByBUOperation.setBuAmount(0L);
        contractInvokeByBUOperation.setInput(input);
        if (!Tools.isEmpty(metadata)) {
            contractInvokeByBUOperation.setMetadata(metadata);
        }
        Chain.Operation operation = ContractServiceImpl.invokeByBU(contractInvokeByBUOperation);
        return operation;
    }

    /**
     * @Author riven
     * @Method checkValid
     * @Params [tokenCheckValidRequest]
     * @Return io.bumo.model.response.TokenCheckValidResponse
     * @Date 2018/7/15 15:36
     */
    @Override
    public Ctp10TokenCheckValidResponse checkValid(Ctp10TokenCheckValidRequest ctp10TokenCheckValidRequest) {
        Ctp10TokenCheckValidResponse ctp10TokenCheckValidResponse = new Ctp10TokenCheckValidResponse();
        TokenCheckValidResult tokenCheckValidResult = new TokenCheckValidResult();
        try {
            if (Tools.isEmpty(ctp10TokenCheckValidRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String address = ctp10TokenCheckValidRequest.getContractAddress();
            if (!PublicKey.isAddressValid(address)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isValid = checkTokenValid(address);
            tokenCheckValidResult.setValid(isValid);
            ctp10TokenCheckValidResponse.buildResponse(SdkError.SUCCESS, tokenCheckValidResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            ctp10TokenCheckValidResponse.buildResponse(errorCode, errorDesc, tokenCheckValidResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenCheckValidResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenCheckValidResult);
        } catch (Exception e) {
            ctp10TokenCheckValidResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenCheckValidResult);
        }
        return ctp10TokenCheckValidResponse;
    }

    /**
     * @Author riven
     * @Method allowance
     * @Params [tokenAllowanceRequest]
     * @Return io.bumo.model.response.TokenAllowanceResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public Ctp10TokenAllowanceResponse allowance(Ctp10TokenAllowanceRequest ctp10TokenAllowanceRequest) {
        Ctp10TokenAllowanceResponse ctp10TokenAllowanceResponse = new Ctp10TokenAllowanceResponse();
        TokenAllowanceResult tokenAllowanceResult = new TokenAllowanceResult();
        try {
            if (Tools.isEmpty(ctp10TokenAllowanceRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenAllowanceRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String tokenOwner = ctp10TokenAllowanceRequest.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                throw new SDKException(SdkError.INVALID_TOKENOWNER_ERROR);
            }
            String spender = ctp10TokenAllowanceRequest.getSpender();
            if (!PublicKey.isAddressValid(spender)) {
                throw new SDKException(SdkError.INVALID_SPENDER_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "allowance");
            JSONObject params = new JSONObject();
            params.put("owner", tokenOwner);
            params.put("spender", spender);
            input.put("params", params);
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_ALLOWANCE_ERRPR.getCode(), errorDesc);
            }
            tokenAllowanceResult = JSONObject.parseObject(result.getValue(), TokenAllowanceResult.class);
            ctp10TokenAllowanceResponse.buildResponse(SdkError.SUCCESS, tokenAllowanceResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenAllowanceResponse.buildResponse(errorCode, errorDesc, tokenAllowanceResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenAllowanceResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenAllowanceResult);
        } catch (Exception e) {
            ctp10TokenAllowanceResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenAllowanceResult);
        }

        return ctp10TokenAllowanceResponse;
    }

    /**
     * @Author riven
     * @Method getInfo
     * @Params [tokenGetInfoRequest]
     * @Return io.bumo.model.response.TokenGetInfoResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public Ctp10TokenGetInfoResponse getInfo(Ctp10TokenGetInfoRequest ctp10TokenGetInfoRequest) {
        Ctp10TokenGetInfoResponse ctp10TokenGetInfoResponse = new Ctp10TokenGetInfoResponse();
        TokenGetInfoResult tokenGetInfoResult = new TokenGetInfoResult();
        try {
            if (Tools.isEmpty(ctp10TokenGetInfoRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenGetInfoRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "contractInfo");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetInfoResult = JSONObject.parseObject(result.getValue(), TokenGetInfoResult.class);
            ctp10TokenGetInfoResponse.buildResponse(SdkError.SUCCESS, tokenGetInfoResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenGetInfoResponse.buildResponse(errorCode, errorDesc, tokenGetInfoResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetInfoResult);
        } catch (Exception e) {
            ctp10TokenGetInfoResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetInfoResult);
        }
        return ctp10TokenGetInfoResponse;
    }

    /**
     * @Author riven
     * @Method getName
     * @Params [tokenGetNameRequest]
     * @Return io.bumo.model.response.TokenGetNameResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public Ctp10TokenGetNameResponse getName(Ctp10TokenGetNameRequest ctp10TokenGetNameRequest) {
        Ctp10TokenGetNameResponse ctp10TokenGetNameResponse = new Ctp10TokenGetNameResponse();
        TokenGetNameResult tokenGetNameResult = new TokenGetNameResult();
        try {
            if (Tools.isEmpty(ctp10TokenGetNameRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenGetNameRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "name");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetNameResult = JSONObject.parseObject(result.getValue(), TokenGetNameResult.class);
            ctp10TokenGetNameResponse.buildResponse(SdkError.SUCCESS, tokenGetNameResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenGetNameResponse.buildResponse(errorCode, errorDesc, tokenGetNameResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenGetNameResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetNameResult);
        } catch (Exception e) {
            ctp10TokenGetNameResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetNameResult);
        }
        return ctp10TokenGetNameResponse;
    }

    /**
     * @Author riven
     * @Method getSymbol
     * @Params [tokenGetSymbolRequest]
     * @Return io.bumo.model.response.TokenGetSymbolResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public Ctp10TokenGetSymbolResponse getSymbol(Ctp10TokenGetSymbolRequest ctp10TokenGetSymbolRequest) {
        Ctp10TokenGetSymbolResponse ctp10TokenGetSymbolResponse = new Ctp10TokenGetSymbolResponse();
        TokenGetSymbolResult tokenGetSymbolResult = new TokenGetSymbolResult();
        try {
            if (Tools.isEmpty(ctp10TokenGetSymbolRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenGetSymbolRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "symbol");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetSymbolResult = JSONObject.parseObject(result.getValue(), TokenGetSymbolResult.class);
            ctp10TokenGetSymbolResponse.buildResponse(SdkError.SUCCESS, tokenGetSymbolResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenGetSymbolResponse.buildResponse(errorCode, errorDesc, tokenGetSymbolResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenGetSymbolResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetSymbolResult);
        } catch (Exception e) {
            ctp10TokenGetSymbolResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetSymbolResult);
        }
        return ctp10TokenGetSymbolResponse;
    }

    @Override
    public Ctp10TokenGetDecimalsResponse getDecimals(Ctp10TokenGetDecimalsRequest ctp10TokenGetDecimalsRequest) {
        Ctp10TokenGetDecimalsResponse ctp10TokenGetDecimalsResponse = new Ctp10TokenGetDecimalsResponse();
        TokenGetDecimalsResult tokenGetDecimalsResult = new TokenGetDecimalsResult();
        try {
            if (Tools.isEmpty(ctp10TokenGetDecimalsRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenGetDecimalsRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "decimals");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetDecimalsResult = JSONObject.parseObject(result.getValue(), TokenGetDecimalsResult.class);
            ctp10TokenGetDecimalsResponse.buildResponse(SdkError.SUCCESS, tokenGetDecimalsResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenGetDecimalsResponse.buildResponse(errorCode, errorDesc, tokenGetDecimalsResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenGetDecimalsResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetDecimalsResult);
        } catch (Exception e) {
            ctp10TokenGetDecimalsResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetDecimalsResult);
        }
        return ctp10TokenGetDecimalsResponse;
    }

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params [tokenGetTotalSupplyRequest]
     * @Return io.bumo.model.response.TokenGetTotalSupplyResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public Ctp10TokenGetTotalSupplyResponse getTotalSupply(Ctp10TokenGetTotalSupplyRequest ctp10TokenGetTotalSupplyRequest) {
        Ctp10TokenGetTotalSupplyResponse ctp10TokenGetTotalSupplyResponse = new Ctp10TokenGetTotalSupplyResponse();
        TokenGetTotalSupplyResult tokenGetTotalSupplyResult = new TokenGetTotalSupplyResult();
        try {
            if (Tools.isEmpty(ctp10TokenGetTotalSupplyRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenGetTotalSupplyRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            JSONObject input = new JSONObject();
            input.put("method", "totalSupply");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetTotalSupplyResult = JSONObject.parseObject(result.getValue(), TokenGetTotalSupplyResult.class);
            ctp10TokenGetTotalSupplyResponse.buildResponse(SdkError.SUCCESS, tokenGetTotalSupplyResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenGetTotalSupplyResponse.buildResponse(errorCode, errorDesc, tokenGetTotalSupplyResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenGetTotalSupplyResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetTotalSupplyResult);
        } catch (Exception e) {
            ctp10TokenGetTotalSupplyResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetTotalSupplyResult);
        }
        return ctp10TokenGetTotalSupplyResponse;
    }

    /**
     * @Author riven
     * @Method getBalance
     * @Params [tokenGetBalanceRequest]
     * @Return io.bumo.model.response.TokenGetBalanceResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public Ctp10TokenGetBalanceResponse getBalance(Ctp10TokenGetBalanceRequest ctp10TokenGetBalanceRequest) {
        Ctp10TokenGetBalanceResponse ctp10TokenGetBalanceResponse = new Ctp10TokenGetBalanceResponse();
        TokenGetBalanceResult tokenGetBalanceResult = new TokenGetBalanceResult();
        try {
            if (Tools.isEmpty(ctp10TokenGetBalanceRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String contractAddress = ctp10TokenGetBalanceRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String tokenOwner = ctp10TokenGetBalanceRequest.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                throw new SDKException(SdkError.INVALID_TOKENOWNER_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (!isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            JSONObject input = new JSONObject();
            input.put("method", "balanceOf");
            JSONObject params = new JSONObject();
            params.put("address", tokenOwner);
            input.put("params", params);
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (Tools.isEmpty(result)) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetBalanceResult = JSONObject.parseObject(result.getValue(), TokenGetBalanceResult.class);
            ctp10TokenGetBalanceResponse.buildResponse(SdkError.SUCCESS, tokenGetBalanceResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            ctp10TokenGetBalanceResponse.buildResponse(errorCode, errorDesc, tokenGetBalanceResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            ctp10TokenGetBalanceResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetBalanceResult);
        } catch (Exception e) {
            ctp10TokenGetBalanceResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetBalanceResult);
        }

        return ctp10TokenGetBalanceResponse;
    }

    /**
     * @Author riven
     * @Method queryContract
     * @Params [sourceAddress, contractAddress, input]
     * @Return java.lang.String
     * @Date 2018/7/10 12:34
     */
    private JSONObject queryContract(String contractAddress, JSONObject input)
            throws SDKException, IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        // call contract
        ContractCallResponse contractCallResponse = ContractServiceImpl.callContract(null, contractAddress,
                2, null, input.toJSONString(), null, null, 1000000000L);
        ContractCallResult contractCallResult = contractCallResponse.getResult();
        return contractCallResult.getQueryRets().getJSONObject(0);
    }
}
