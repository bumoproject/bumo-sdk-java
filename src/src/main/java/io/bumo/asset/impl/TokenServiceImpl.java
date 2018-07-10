package io.bumo.asset.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.bumo.asset.TokenService;
import io.bumo.common.Constant;
import io.bumo.contract.ContractService;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;

import java.util.regex.Pattern;

/**
 * @Author riven
 * @Date 2018/7/6 11:08
 */
public class TokenServiceImpl implements TokenService {
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
        do {
            TokenAllowanceResult tokenAllowanceResult = new TokenAllowanceResult();
            String sourceAddress = tokenAllowanceRequest.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                tokenAllowanceResponse.buildResponse(SdkError.INVALID_SOURCEADDRESS_ERROR, tokenAllowanceResult);
                break;
            }
            String contractAddress = tokenAllowanceRequest.getContractAddress();
            if (!PublicKey.isAddressValid(contractAddress)) {
                tokenAllowanceResponse.buildResponse(SdkError.INVALID_CONTRACTADDRESS_ERROR, tokenAllowanceResult);
                break;
            }
            if (sourceAddress != null && sourceAddress.equals(contractAddress)) {
                tokenAllowanceResponse.buildResponse(SdkError.SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR, tokenAllowanceResult);
                break;
            }
            String tokenOwner = tokenAllowanceRequest.getTokenOwner();
            if (!PublicKey.isAddressValid(tokenOwner)) {
                tokenAllowanceResponse.buildResponse(SdkError.INVALID_TOKENOWNER_ERRPR, tokenAllowanceResult);
                break;
            }
            String spender = tokenAllowanceRequest.getSpender();
            if (!PublicKey.isAddressValid(spender)) {
                tokenAllowanceResponse.buildResponse(SdkError.INVALID_SPENDER_ERROR, tokenAllowanceResult);
                break;
            }


        } while (false);
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
    }

    /**
     * @Author riven
     * @Method issue
     * @Params [tokenIssueResult]
     * @Return io.bumo.crypto.protobuf.Chain.Operation
     * @Date 2018/7/10 11:41
     */
    public static Chain.Operation issue(TokenIssueOperation tokenIssueResult) throws SDKException {
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
        String tokenOwner = tokenIssueResult.getTokenOwner();
        if (!PublicKey.isAddressValid(tokenOwner)) {
            throw new SDKException(SdkError.INVALID_TOKENOWNER_ERRPR);
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
        params.put("totalSupply", totalSupply);
        params.put("contractOwner", tokenOwner);
        initInput.put("params", params);

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
            operationCreateContract.setInitInput(initInput.toJSONString());
        }
        Chain.Contract.Builder contract = operationCreateContract.getContractBuilder();
        contract.setPayload(payload);
        Chain.AccountPrivilege.Builder accountPrivilege = operationCreateContract.getPrivBuilder();
        accountPrivilege.setMasterWeight(0);
        Chain.AccountThreshold.Builder accountThreshold = accountPrivilege.getThresholdsBuilder();
        accountThreshold.setTxThreshold(1);

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
        boolean isContractValid = checkContractValid(contractAddress);
        if (false == isContractValid) {
            throw new SDKException(SdkError.CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR);
        }

        // build init
        JSONObject input = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("to", destAddress);
        params.put("value", amount);
        input.put("params", params);

        // invoke contract
        Chain.Operation operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);

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
        // build input
        JSONObject input = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("from", fromAddress);
        params.put("value", amount);
        input.put("params", params);

        // invoke contract
        Chain.Operation operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);

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
        // build input
        JSONObject input = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("spender", spender);
        params.put("value", amount);
        input.put("params", params);
        // invoke contract
        Chain.Operation operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
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
        // build input
        JSONObject input = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("to", destAddress);
        params.put("value", amount);
        input.put("params", params);
        // invoke contract
        Chain.Operation operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
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
        // build input
        JSONObject input = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("address", tokenOwner);
        input.put("params", params);
        // invoke contract
        Chain.Operation operation = invokeContract(sourceAddress, contractAddress, input.toJSONString(), metadata);
        return operation;
    }

    /**
     * @Author riven
     * @Method checkContractValid
     * @Params [contractAddress]
     * @Return boolean
     * @Date 2018/7/10 11:41
     */
    private static boolean checkContractValid(String contractAddress) throws SDKException {
        ContractCheckValidRequest contractCheckValidRequest = new ContractCheckValidRequest();
        contractCheckValidRequest.setContractAddress(contractAddress);
        ContractService contract = new ContractServiceImpl();
        ContractCheckValidResponse contractCheckValidResponse = contract.checkValid(contractCheckValidRequest);
        boolean isValid = contractCheckValidResponse.getResult().getValid();

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
    private String queryContract(String sourceAddress, String contractAddress, String input) {
        return null;
    }
}
