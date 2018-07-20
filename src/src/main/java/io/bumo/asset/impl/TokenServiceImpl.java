package io.bumo.asset.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.bumo.asset.TokenService;
import io.bumo.common.Constant;
import io.bumo.common.General;
import io.bumo.contract.ContractService;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.ContractInfo;
import io.bumo.model.response.result.data.MetadataInfo;
import io.bumo.model.response.result.data.TokenInfo;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Pattern;

/**
 * @Author riven
 * @Date 2018/7/6 11:08
 */
public class TokenServiceImpl implements TokenService {
    /**
     * @Author riven
     * @Method checkValid
     * @Params [tokenCheckValidRequest]
     * @Return io.bumo.model.response.TokenCheckValidResponse
     * @Date 2018/7/15 15:36
     */
    @Override
    public TokenCheckValidResponse checkValid(TokenCheckValidRequest tokenCheckValidRequest) {
        TokenCheckValidResponse tokenCheckValidResponse = new TokenCheckValidResponse();
        TokenCheckValidResult tokenCheckValidResult = new TokenCheckValidResult();
        try {
            String address = tokenCheckValidRequest.getContractAddress();
            if (!PublicKey.isAddressValid(address)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isValid = checkTokenValid(address);
            tokenCheckValidResult.setValid(isValid);
            tokenCheckValidResponse.buildResponse(SdkError.SUCCESS, tokenCheckValidResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            tokenCheckValidResponse.buildResponse(errorCode, errorDesc, tokenCheckValidResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenCheckValidResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenCheckValidResult);
        } catch (Exception e) {
            tokenCheckValidResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenCheckValidResult);
        }
        return tokenCheckValidResponse;
    }
    /**
     * @Author riven
     * @Method allowance
     * @Params [tokenAllowanceRequest]
     * @Return io.bumo.model.response.TokenAllowanceResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public TokenAllowanceResponse allowance(TokenAllowanceRequest tokenAllowanceRequest) {
        TokenAllowanceResponse tokenAllowanceResponse = new TokenAllowanceResponse();
        TokenAllowanceResult tokenAllowanceResult = new TokenAllowanceResult();
        try {
            String contractAddress = tokenAllowanceRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String tokenOwner = tokenAllowanceRequest.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                throw new SDKException(SdkError.INVALID_TOKENOWNER_ERRPR);
            }
            String spender = tokenAllowanceRequest.getSpender();
            if (!PublicKey.isAddressValid(spender)) {
                throw new SDKException(SdkError.INVALID_SPENDER_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
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
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_ALLOWANCE_ERRPR.getCode(), errorDesc);
            }
            tokenAllowanceResult = JSONObject.parseObject(result.getValue(), TokenAllowanceResult.class);
            tokenAllowanceResponse.buildResponse(SdkError.SUCCESS, tokenAllowanceResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenAllowanceResponse.buildResponse(errorCode, errorDesc, tokenAllowanceResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenAllowanceResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenAllowanceResult);
        } catch (Exception e) {
            tokenAllowanceResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenAllowanceResult);
        }

        return tokenAllowanceResponse;
    }

    /**
     * @Author riven
     * @Method getInfo
     * @Params [tokenGetInfoRequest]
     * @Return io.bumo.model.response.TokenGetInfoResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public TokenGetInfoResponse getInfo(TokenGetInfoRequest tokenGetInfoRequest) {
        TokenGetInfoResponse tokenGetInfoResponse = new TokenGetInfoResponse();
        TokenGetInfoResult tokenGetInfoResult = new TokenGetInfoResult();
        try {
            String contractAddress = tokenGetInfoRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "contractInfo");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetInfoResult =  JSONObject.parseObject(result.getValue(), TokenGetInfoResult.class);
            tokenGetInfoResponse.buildResponse(SdkError.SUCCESS, tokenGetInfoResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenGetInfoResponse.buildResponse(errorCode, errorDesc, tokenGetInfoResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetInfoResult);
        } catch (Exception e) {
            tokenGetInfoResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetInfoResult);
        }
        return tokenGetInfoResponse;
    }

    /**
     * @Author riven
     * @Method getName
     * @Params [tokenGetNameRequest]
     * @Return io.bumo.model.response.TokenGetNameResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public TokenGetNameResponse getName(TokenGetNameRequest tokenGetNameRequest) {
        TokenGetNameResponse tokenGetNameResponse = new TokenGetNameResponse();
        TokenGetNameResult tokenGetNameResult = new TokenGetNameResult();
        try {
            String contractAddress = tokenGetNameRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "name");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetNameResult =  JSONObject.parseObject(result.getValue(), TokenGetNameResult.class);
            tokenGetNameResponse.buildResponse(SdkError.SUCCESS, tokenGetNameResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenGetNameResponse.buildResponse(errorCode, errorDesc, tokenGetNameResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenGetNameResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetNameResult);
        } catch (Exception e) {
            tokenGetNameResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetNameResult);
        }
        return tokenGetNameResponse;
    }

    /**
     * @Author riven
     * @Method getSymbol
     * @Params [tokenGetSymbolRequest]
     * @Return io.bumo.model.response.TokenGetSymbolResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public TokenGetSymbolResponse getSymbol(TokenGetSymbolRequest tokenGetSymbolRequest) {
        TokenGetSymbolResponse tokenGetSymbolResponse = new TokenGetSymbolResponse();
        TokenGetSymbolResult tokenGetSymbolResult = new TokenGetSymbolResult();
        try {
            String contractAddress = tokenGetSymbolRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "symbol");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetSymbolResult =  JSONObject.parseObject(result.getValue(), TokenGetSymbolResult.class);
            tokenGetSymbolResponse.buildResponse(SdkError.SUCCESS, tokenGetSymbolResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenGetSymbolResponse.buildResponse(errorCode, errorDesc, tokenGetSymbolResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenGetSymbolResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetSymbolResult);
        } catch (Exception e) {
            tokenGetSymbolResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetSymbolResult);
        }
        return tokenGetSymbolResponse;
    }

    @Override
    public TokenGetDecimalsResponse getDecimals(TokenGetDecimalsRequest tokenGetDecimalsRequest) {
        TokenGetDecimalsResponse tokenGetDecimalsResponse = new TokenGetDecimalsResponse();
        TokenGetDecimalsResult tokenGetDecimalsResult = new TokenGetDecimalsResult();
        try {
            String contractAddress = tokenGetDecimalsRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            JSONObject input = new JSONObject();
            input.put("method", "decimals");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetDecimalsResult =  JSONObject.parseObject(result.getValue(), TokenGetDecimalsResult.class);
            tokenGetDecimalsResponse.buildResponse(SdkError.SUCCESS, tokenGetDecimalsResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenGetDecimalsResponse.buildResponse(errorCode, errorDesc, tokenGetDecimalsResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenGetDecimalsResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetDecimalsResult);
        } catch (Exception e) {
            tokenGetDecimalsResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetDecimalsResult);
        }
        return tokenGetDecimalsResponse;
    }

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params [tokenGetTotalSupplyRequest]
     * @Return io.bumo.model.response.TokenGetTotalSupplyResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public TokenGetTotalSupplyResponse getTotalSupply(TokenGetTotalSupplyRequest tokenGetTotalSupplyRequest) {
        TokenGetTotalSupplyResponse tokenGetTotalSupplyResponse = new TokenGetTotalSupplyResponse();
        TokenGetTotalSupplyResult tokenGetTotalSupplyResult = new TokenGetTotalSupplyResult();
        try {
            String contractAddress = tokenGetTotalSupplyRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            JSONObject input = new JSONObject();
            input.put("method", "totalSupply");
            JSONObject queryResult = queryContract(contractAddress, input);
            TokenQueryResponse tokenQueryResponse = JSON.toJavaObject(queryResult, TokenQueryResponse.class);
            TokenQueryResult result = tokenQueryResponse.getResult();
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetTotalSupplyResult =  JSONObject.parseObject(result.getValue(), TokenGetTotalSupplyResult.class);
            tokenGetTotalSupplyResponse.buildResponse(SdkError.SUCCESS, tokenGetTotalSupplyResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenGetTotalSupplyResponse.buildResponse(errorCode, errorDesc, tokenGetTotalSupplyResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenGetTotalSupplyResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetTotalSupplyResult);
        } catch (Exception e) {
            tokenGetTotalSupplyResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetTotalSupplyResult);
        }
        return tokenGetTotalSupplyResponse;
    }

    /**
     * @Author riven
     * @Method getBalance
     * @Params [tokenGetBalanceRequest]
     * @Return io.bumo.model.response.TokenGetBalanceResponse
     * @Date 2018/7/9 19:13
     */
    @Override
    public TokenGetBalanceResponse getBalance(TokenGetBalanceRequest tokenGetBalanceRequest) {
        TokenGetBalanceResponse tokenGetBalanceResponse = new TokenGetBalanceResponse();
        TokenGetBalanceResult tokenGetBalanceResult = new TokenGetBalanceResult();
        try {
            String contractAddress = tokenGetBalanceRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String tokenOwner = tokenGetBalanceRequest.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                throw new SDKException(SdkError.INVALID_TOKENOWNER_ERRPR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
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
            if (null == result) {
                TokenErrorResult errorResult = tokenQueryResponse.getError();
                String errorDesc = errorResult.getData().getException();
                throw new SDKException(SdkError.GET_TOKEN_INFO_ERRPR.getCode(), errorDesc);
            }
            tokenGetBalanceResult = JSONObject.parseObject(result.getValue(), TokenGetBalanceResult.class);
            tokenGetBalanceResponse.buildResponse(SdkError.SUCCESS, tokenGetBalanceResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            tokenGetBalanceResponse.buildResponse(errorCode, errorDesc, tokenGetBalanceResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            tokenGetBalanceResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, tokenGetBalanceResult);
        } catch (Exception e) {
            tokenGetBalanceResponse.buildResponse(SdkError.SYSTEM_ERROR, tokenGetBalanceResult);
        }

        return tokenGetBalanceResponse;
    }

    /**
     * @Author riven
     * @Method issue
     * @Params [tokenIssueResult]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation issue(TokenIssueOperation tokenIssueResult) throws SDKException {
        Chain.Operation.Builder operation;
        try {
            String sourceAddress = tokenIssueResult.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            Long initBalance = tokenIssueResult.getInitBalance();
            if (initBalance <= 0) {
                throw new SDKException(SdkError.INVALID_INITBALANCE_ERROR);
            }
            String name = tokenIssueResult.getName();
            if (name == null || (name != null && (name.length() < 1 || name.length() > 1024))) {
                throw new SDKException(SdkError.INVALID_TOKEN_NAME_ERROR);
            }
            String symbol = tokenIssueResult.getSymbol();
            if (symbol == null || (symbol != null && (symbol.length() < 1 || symbol.length() > 2014))) {
                throw new SDKException(SdkError.INVALID_TOKEN_SYMBOL_ERROR);
            }
            Integer decimals = tokenIssueResult.getDecimals();
            if (decimals == null || (decimals != null && (decimals < 0 || decimals > 8))) {
                throw new SDKException(SdkError.INVALID_TOKEN_DECIMALS_ERROR);
            }
            String totalSupply = tokenIssueResult.getTotalSupply();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(totalSupply).matches();
            if (!isNumber || (isNumber && Long.valueOf(totalSupply) < 1)) {
                throw new SDKException(SdkError.INVALID_TOKEN_TOTALSUPPLY_ERROR);
            }
            String metadata = tokenIssueResult.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }

            String payload = Constant.TOKEN_PAYLOAD;
            // build initInput
            JSONObject initInput = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("name", name);
            params.put("symbol", symbol);
            params.put("decimals", decimals);
            params.put("supply", totalSupply);
            initInput.put("params", params);

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
    public static Chain.Operation transfer(TokenTransferOperation tokenTransferOperation) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = tokenTransferOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = tokenTransferOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String destAddress = tokenTransferOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            if (sourceAddress.equals(destAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String amount = tokenTransferOperation.getAmount();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(amount).matches();
            if (!isNumber || (isNumber && (Long.valueOf(amount) < 1))) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = tokenTransferOperation.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }

            // build init
            JSONObject input = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("to", destAddress);
            params.put("value", amount);
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
    public static Chain.Operation transferFrom(TokenTransferFromOperation tokenTransferFromOperation) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = tokenTransferFromOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = tokenTransferFromOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            String fromAddress = tokenTransferFromOperation.getFromAddress();
            if (!PublicKey.isAddressValid(fromAddress)) {
                throw new SDKException(SdkError.INVALID_FROMADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String destAddress = tokenTransferFromOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            if (fromAddress.equals(destAddress)) {
                throw new SDKException(SdkError.FROMADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String amount = tokenTransferFromOperation.getAmount();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(amount).matches();
            if (!isNumber || (isNumber && (Long.valueOf(amount) < 1))) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = tokenTransferFromOperation.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("from", fromAddress);
            params.put("value", amount);
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
    public static Chain.Operation approve(TokenApproveOperation tokenApproveOperation) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = tokenApproveOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = tokenApproveOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String spender = tokenApproveOperation.getSpender();
            if (!PublicKey.isAddressValid(spender)) {
                throw new SDKException(SdkError.INVALID_SPENDER_ERROR);
            }
            String amount = tokenApproveOperation.getAmount();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(amount).matches();
            if (!isNumber || (isNumber && (Long.valueOf(amount) < 1))) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = tokenApproveOperation.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("spender", spender);
            params.put("value", amount);
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
     * @Params [tokenAssignResponse]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation assign(TokenAssignOperation tokenAssignResponse) throws SDKException {
        Chain.Operation operation;
        try {
            String sourceAddress = tokenAssignResponse.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = tokenAssignResponse.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String destAddress = tokenAssignResponse.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            String amount = tokenAssignResponse.getAmount();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isNumber = pattern.matcher(amount).matches();
            if (!isNumber || (isNumber && (Long.valueOf(amount) < 1))) {
                throw new SDKException(SdkError.INVALID_TOKEN_AMOUNT_ERROR);
            }
            String metadata = tokenAssignResponse.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("to", destAddress);
            params.put("value", amount);
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
    public static Chain.Operation changeOwner(TokenChangeOwnerOperation tokenChangeOwnerOperation) {
        Chain.Operation operation;
        try {
            String sourceAddress = tokenChangeOwnerOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String contractAddress = tokenChangeOwnerOperation.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                throw new SDKException(SdkError.INVALID_CONTRACTADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR);
            }
            String tokenOwner = tokenChangeOwnerOperation.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                throw new SDKException(SdkError.INVALID_TOKENOWNER_ERRPR);
            }
            String metadata = tokenChangeOwnerOperation.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }
            boolean isContractValid = checkTokenValid(contractAddress);
            if (false == isContractValid) {
                throw new SDKException(SdkError.NO_SUCH_TOKEN_ERROR);
            }
            // build input
            JSONObject input = new JSONObject();
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
        if (errorCode != null && errorCode.intValue() == 4) {
            throw new SDKException(errorCode, (null == errorDesc ? "Account (" + contractAddress +") not exist" : errorDesc));
        }
        SdkError.checkErrorCode(tokenMessageResponse);
        boolean isValid = false;
        do {
            tokenMessageResult = tokenMessageResponse.getResult();
            ContractInfo contractInfo = tokenMessageResult.getContract();
            MetadataInfo[] metadataInfos = tokenMessageResult.getMetadatas();
            if (null == contractInfo || (contractInfo != null && (contractInfo.getPayload() == null)
                    || contractInfo.getPayload().isEmpty()) || null == metadataInfos
                    || (metadataInfos != null && metadataInfos.length == 0)) {
                break;
            }
            String tokenInfoJson = metadataInfos[0].getValue();
            if (tokenInfoJson == null || (tokenInfoJson != null && tokenInfoJson.isEmpty())) {
                break;
            }
            TokenInfo tokenInfo =  JSONObject.parseObject(tokenInfoJson, TokenInfo.class);
            String ctp = tokenInfo.getCtp();
            if (null == ctp || (ctp != null && ctp != "1.0")) {
                break;
            }
            String name = tokenInfo.getName();
            if (null == name || (name != null && (name.length() < 1 || name.length() > 1024))) {
                break;
            }
            String symbol = tokenInfo.getSymbol();
            if (null == symbol || (symbol != null && (symbol.length() < 1 || symbol.length() > 2014))) {
                break;
            }
            Integer decimals = tokenInfo.getDecimals();
            if (null == decimals || (decimals != null && (decimals < 0 || decimals > 8))) {
                break;
            }
            String totalSupply = tokenInfo.getTotalSupply();
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
        if (sourceAddress != null) {
            contractInvokeByBUOperation.setSourceAddress(sourceAddress);
        }
        contractInvokeByBUOperation.setContractAddress(contractAddress);
        contractInvokeByBUOperation.setAmount(0L);
        contractInvokeByBUOperation.setInput(input);
        if (metadata != null) {
            contractInvokeByBUOperation.setMetadata(metadata);
        }
        ContractService contract = new ContractServiceImpl();
        Chain.Operation operation= ContractServiceImpl.invokeByBU(contractInvokeByBUOperation);
        return operation;
    }

    /**
     * @Author riven
     * @Method queryContract
     * @Params [sourceAddress, contractAddress, input]
     * @Return java.lang.String
     * @Date 2018/7/10 12:34
     */
    private JSONObject queryContract(String contractAddress, JSONObject input) throws SDKException {
        // call contract
        ContractCallRequest contractCallRequest = new ContractCallRequest();
        contractCallRequest.setContractAddress(contractAddress);
        contractCallRequest.setInput(input.toJSONString());
        contractCallRequest.setFeeLimit(1000000000L);
        contractCallRequest.setOptType(2);
        ContractService contractService = new ContractServiceImpl();
        ContractCallResponse contractCallResponse = contractService.call(contractCallRequest);
        Integer errorCode = contractCallResponse.getErrorCode();
        if (errorCode != 0) {
            String errorDesc = contractCallResponse.getErrorDesc();
            throw new SDKException(errorCode, errorDesc);
        }
        ContractCallResult contractCallResult = contractCallResponse.getResult();
        return contractCallResult.getQueryRets().getJSONObject(0);
    }
}
