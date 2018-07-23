package io.bumo.blockchain.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import io.bumo.account.impl.AccountServiceImpl;
import io.bumo.blockchain.BlockService;
import io.bumo.blockchain.TransactionService;
import io.bumo.common.Constant;
import io.bumo.common.General;
import io.bumo.common.OperationType;
import io.bumo.common.Tools;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hash.HashUtil;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.log.LogServiceImpl;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.Signature;
import io.bumo.token.impl.AssetServiceImpl;
import io.bumo.token.impl.BUServiceImpl;
import io.bumo.token.impl.Ctp10TokenServiceImpl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @Author riven
 * @Date 2018/7/3 17:22
 */
public class TransactionServiceImpl implements TransactionService {

    /**
     * @Author riven
     * @Method buildBlob
     * @Params [transactionBuildBlobRequest]
     * @Return io.bumo.model.response.TransactionBuildBlobResponse
     * @Date 2018/7/5 19:04
     */
    @Override
    public TransactionBuildBlobResponse buildBlob(TransactionBuildBlobRequest transactionBuildBlobRequest) {
        TransactionBuildBlobResponse transactionBuildBlobResponse = new TransactionBuildBlobResponse();
        TransactionBuildBlobResult transactionBuildBlobResult = new TransactionBuildBlobResult();
        try {
            if (Tools.isEmpty(transactionBuildBlobRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            // check sourceAddress
            String sourceAddress = transactionBuildBlobRequest.getSourceAddress();
            if (!PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            // check nonce
            Long nonce = transactionBuildBlobRequest.getNonce();
            if (Tools.isEmpty(nonce) || nonce < 1) {
                throw new SDKException(SdkError.INVALID_NONCE_ERROR);
            }
            // check gasPrice
            Long gasPrice = transactionBuildBlobRequest.getGasPrice();
            if (Tools.isEmpty(gasPrice) || gasPrice < Constant.GAS_PRICE_MIN) {
                throw new SDKException(SdkError.INVALID_GASPRICE_ERROR);
            }

            // check feeLimit
            Long feeLimit = transactionBuildBlobRequest.getFeeLimit();
            if (Tools.isEmpty(feeLimit) || feeLimit < Constant.FEE_LIMIT_MIN) {
                throw new SDKException(SdkError.INVALID_FEELIMIT_ERROR);
            }

            Long ceilLedgerSeq = transactionBuildBlobRequest.getCeilLedgerSeq();
            if (!Tools.isEmpty(ceilLedgerSeq) && ceilLedgerSeq < 0) {
                throw new SDKException(SdkError.INVALID_CEILLEDGERSEQ_ERROR);
            }
            // check metadata
            String metadata = transactionBuildBlobRequest.getMetadata();
            // build transaction
            Chain.Transaction.Builder transaction = Chain.Transaction.newBuilder();
            // add note
            if (!Tools.isEmpty(metadata)) {
                transaction.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            // check operation
            BaseOperation[] baseOperations = transactionBuildBlobRequest.getOperations();
            if (Tools.isEmpty(baseOperations)) {
                throw new SDKException(SdkError.OPERATIONS_EMPTY_ERROR);
            }
            buildOperations(baseOperations, sourceAddress, transaction);
            // add other information
            transaction.setSourceAddress(sourceAddress);
            transaction.setNonce(nonce);
            transaction.setFeeLimit(feeLimit);
            transaction.setGasPrice(gasPrice);

            if (!Tools.isEmpty(ceilLedgerSeq)) {
                // get blockNumber
                BlockService blockService = new BlockServiceImpl();
                BlockGetNumberResponse blockGetNumberResponse = blockService.getNumber();
                Integer errorCode = blockGetNumberResponse.getErrorCode();
                if (!Tools.isEmpty(errorCode) && errorCode != 0) {
                    String errorDesc = blockGetNumberResponse.getErrorDesc();
                    throw new SDKException(errorCode, errorDesc);
                } else if (Tools.isEmpty(errorCode)) {
                    throw new SDKException(SdkError.SYSTEM_ERROR);
                }
                // check ceilLedgerSeq
                Long blockNumber = blockGetNumberResponse.getResult().getHeader().getBlockNumber();
                transaction.setCeilLedgerSeq(ceilLedgerSeq + blockNumber);
            }
            byte[] transactionBlobBytes = transaction.build().toByteArray();
            String transactionBlob = HexFormat.byteToHex(transactionBlobBytes);
            transactionBuildBlobResult.setTransactionBlob(transactionBlob);
            transactionBuildBlobResult.setHash(HashUtil.GenerateHashHex(transactionBlobBytes));
            transactionBuildBlobResponse.buildResponse(SdkError.SUCCESS, transactionBuildBlobResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionBuildBlobResponse.buildResponse(errorCode, errorDesc, transactionBuildBlobResult);
        } catch (Exception exception) {
            transactionBuildBlobResponse.buildResponse(SdkError.SYSTEM_ERROR, transactionBuildBlobResult);
        }

        return transactionBuildBlobResponse;
    }

    /**
     * @Author riven
     * @Method parseBlob
     * @Params [transactionParseBlobRequest]
     * @Return io.bumo.model.response.TransactionParseBlobResponse
     * @Date 2018/7/23 10:01
     */
    @Override
    public TransactionParseBlobResponse parseBlob(TransactionParseBlobRequest transactionParseBlobRequest) {
        TransactionParseBlobResponse transactionParseBlobResponse = new TransactionParseBlobResponse();
        TransactionParseBlobResult transactionParseBlobResult = new TransactionParseBlobResult();
        try {
            if (Tools.isEmpty(transactionParseBlobRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String blob = transactionParseBlobRequest.getBlob();
            if (Tools.isEmpty(blob)) {
                throw new SDKException(SdkError.INVALID_BLOB_ERROR);
            }
            Chain.Transaction transaction = Chain.Transaction.parseFrom(HexFormat.hexToByte(blob));
            JsonFormat jsonFormat = new JsonFormat();
            String transactionJson = jsonFormat.printToString(transaction);
            transactionParseBlobResult = JSONObject.parseObject(transactionJson, TransactionParseBlobResult.class);
            transactionParseBlobResponse.buildResponse(SdkError.SUCCESS, transactionParseBlobResult);
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            transactionParseBlobResponse.buildResponse(errorCode, errorDesc, transactionParseBlobResult);
        } catch (InvalidProtocolBufferException e) {
            transactionParseBlobResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionParseBlobResult);
        } catch (Exception exception) {
            exception.printStackTrace();
            transactionParseBlobResponse.buildResponse(SdkError.SYSTEM_ERROR, transactionParseBlobResult);
        }

        return transactionParseBlobResponse;
    }

    /**
     * @Author riven
     * @Method evaluateFee
     * @Params [TransactionEvaluateFeeRequest]
     * @Return io.bumo.model.response.TransactionEvaluateFeeResponse
     * @Date 2018/7/5 19:04
     */
    @Override
    public TransactionEvaluateFeeResponse evaluateFee(TransactionEvaluateFeeRequest transactionEvaluateFeeRequest) {
        TransactionEvaluateFeeResponse transactionEvaluateFeeResponse = new TransactionEvaluateFeeResponse();
        TransactionEvaluateFeeResult transactionEvaluateFeeResult = new TransactionEvaluateFeeResult();
        try {
            if (Tools.isEmpty(General.url)) {
                throw new SDKException(SdkError.URL_EMPTY_ERROR);
            }
            if (Tools.isEmpty(transactionEvaluateFeeRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            // check sourceAddress
            String sourceAddress = transactionEvaluateFeeRequest.getSourceAddress();
            if (!PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            // check nonce
            Long nonce = transactionEvaluateFeeRequest.getNonce();
            if (Tools.isEmpty(nonce) || nonce < 1) {
                throw new SDKException(SdkError.INVALID_NONCE_ERROR);
            }
            // check signatureNum
            Integer signatureNum = transactionEvaluateFeeRequest.getSignatureNumber();
            if (Tools.isEmpty(signatureNum) || signatureNum < 1) {
                throw new SDKException(SdkError.INVALID_SIGNATURENUMBER_ERROR);
            }
            // check ceilLedgerSeq
            Long ceilLedgerSeq = transactionEvaluateFeeRequest.getCeilLedgerSeq();
            if (!Tools.isEmpty(ceilLedgerSeq) && ceilLedgerSeq < 0) {
                throw new SDKException(SdkError.INVALID_CEILLEDGERSEQ_ERROR);
            }
            // check metadata
            String metadata = transactionEvaluateFeeRequest.getMetadata();
            // build transaction
            Chain.Transaction.Builder transaction = Chain.Transaction.newBuilder();
            BaseOperation[] baseOperations = transactionEvaluateFeeRequest.getOperations();
            if (Tools.isEmpty(baseOperations)) {
                throw new SDKException(SdkError.OPERATIONS_EMPTY_ERROR);
            }
            buildOperations(baseOperations, sourceAddress, transaction);
            transaction.setSourceAddress(sourceAddress);
            transaction.setNonce(nonce);
            if (!Tools.isEmpty(metadata)) {
                transaction.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            if (!Tools.isEmpty(ceilLedgerSeq)) {
                transaction.setCeilLedgerSeq(ceilLedgerSeq);
            }
            // protocol buffer to json
            JsonFormat jsonFormat = new JsonFormat();
            String transactionStr = jsonFormat.printToString(transaction.build());
            JSONObject transactionJson = JSONObject.parseObject(transactionStr);

            // build testTransaction request
            JSONObject testTransactionRequest = new JSONObject();
            JSONArray transactionItems = new JSONArray();
            JSONObject transactionItem = new JSONObject();
            transactionItem.put("transaction_json", transactionJson);
            transactionItems.add(transactionItem);
            testTransactionRequest.put("items", transactionItems);

            String evaluationFeeUrl = General.transactionEvaluationFee();
            String result = HttpKit.post(evaluationFeeUrl, testTransactionRequest.toJSONString());
            transactionEvaluateFeeResponse = JSON.parseObject(result, TransactionEvaluateFeeResponse.class);

        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionEvaluateFeeResponse.buildResponse(errorCode, errorDesc, transactionEvaluateFeeResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            transactionEvaluateFeeResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, transactionEvaluateFeeResult);
        } catch (Exception exception) {
            transactionEvaluateFeeResponse.buildResponse(SdkError.SYSTEM_ERROR, transactionEvaluateFeeResult);
        }
        return transactionEvaluateFeeResponse;
    }


    /**
     * @Author riven
     * @Method sign
     * @Params [transactionSignRequest]
     * @Return io.bumo.model.response.TransactionSignResponse
     * @Date 2018/7/5 19:04
     */
    @Override
    public TransactionSignResponse sign(TransactionSignRequest transactionSignRequest) {
        TransactionSignResponse transactionSignResponse = new TransactionSignResponse();
        TransactionSignResult transactionSignResult = new TransactionSignResult();
        try {
            if (Tools.isEmpty(transactionSignRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String blob = transactionSignRequest.getBlob();
            if (Tools.isEmpty(blob)) {
                throw new SDKException(SdkError.INVALID_BLOB_ERROR);
            }
            byte[] blobBytes = HexFormat.hexToByte(blob);
            Chain.Transaction.parseFrom(blobBytes);
            String[] privateKeys = transactionSignRequest.getPrivateKeys();
            if (Tools.isEmpty(privateKeys)) {
                throw new SDKException(SdkError.PRIVATEKEY_NULL_ERROR);
            }
            int i = 0;
            int length = privateKeys.length;
            Signature[] signatures = new Signature[length];
            for (; i < length; i++) {
                if (!PrivateKey.isPrivateKeyValid(privateKeys[i])) {
                    throw new SDKException(SdkError.PRIVATEKEY_ONE_ERROR);
                }
                Signature signature = new Signature();
                byte[] signBytes = PrivateKey.sign(blobBytes, privateKeys[i]);
                String publicKey = PrivateKey.getEncPublicKey(privateKeys[i]);
                signature.setSignData(HexFormat.byteToHex(signBytes));
                signature.setPublicKey(publicKey);
                signatures[i] = signature;
            }
            transactionSignResult.setSignatures(signatures);
            transactionSignResponse.buildResponse(SdkError.SUCCESS, transactionSignResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionSignResponse.buildResponse(errorCode, errorDesc, transactionSignResult);
        } catch (InvalidProtocolBufferException | IllegalArgumentException e) {
            transactionSignResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionSignResult);
        } catch (Exception e) {
            e.printStackTrace();
            transactionSignResponse.buildResponse(SdkError.SYSTEM_ERROR, transactionSignResult);
        }
        return transactionSignResponse;
    }

    /**
     * @Author riven
     * @Method submit
     * @Params [transactionSubmitRequest]
     * @Return io.bumo.model.response.TransactionSubmitResponse
     * @Date 2018/7/5 19:04
     */
    @Override
    public TransactionSubmitResponse submit(TransactionSubmitRequest transactionSubmitRequest) {
        TransactionSubmitResponse transactionSubmitResponse = new TransactionSubmitResponse();
        TransactionSubmitResult transactionSubmitResult = new TransactionSubmitResult();
        try {
            if (Tools.isEmpty(General.url)) {
                throw new SDKException(SdkError.URL_EMPTY_ERROR);
            }
            if (Tools.isEmpty(transactionSubmitRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String blob = transactionSubmitRequest.getTransactionBlob();
            if (Tools.isEmpty(blob)) {
                throw new SDKException(SdkError.INVALID_BLOB_ERROR);
            }
            Chain.Transaction.parseFrom(HexFormat.hexToByte(blob));
            // build transaction request
            JSONObject transactionItemsRequest = new JSONObject();
            JSONArray transactionItems = new JSONArray();
            JSONObject transactionItem = new JSONObject();
            transactionItem.put("transaction_blob", blob);
            JSONArray signatureItems = new JSONArray();
            Signature[] signatures = transactionSubmitRequest.getSignatures();
            if (Tools.isEmpty(signatures)) {
                throw new SDKException(SdkError.SIGNATURE_EMPTY_ERROR);
            }
            int i = 0;
            int length = signatures.length;
            for (; i < length; i++) {
                JSONObject signatureItem = new JSONObject();
                Signature signature = signatures[i];
                String signData = signature.getSignData();
                if (Tools.isEmpty(signData)) {
                    throw new SDKException(SdkError.SIGNDATA_NULL_ERROR);
                }
                String publicKey = signature.getPublicKey();
                if (Tools.isEmpty(publicKey)) {
                    throw new SDKException(SdkError.PUBLICKEY_NULL_ERROR);
                }
                signatureItem.put("sign_data", signData);
                signatureItem.put("public_key", publicKey);
                signatureItems.add(signatureItem);
            }
            transactionItem.put("signatures", signatureItems);
            transactionItems.add(transactionItem);
            transactionItemsRequest.put("items", transactionItems);
            // submit
            String submitUrl = General.transactionSubmitUrl();
            String result = HttpKit.post(submitUrl, transactionItemsRequest.toJSONString());
            TransactionSubmitHttpResponse transactionSubmitHttpResponse = JSONObject.parseObject(result, TransactionSubmitHttpResponse.class);
            Integer successCount = transactionSubmitHttpResponse.getSuccessCount();
            TransactionSubmitHttpResult[] httpResults = transactionSubmitHttpResponse.getResults();
            if (!Tools.isEmpty(httpResults)) {
                transactionSubmitResult.setHash(httpResults[0].getHash());
                if (!Tools.isEmpty(successCount) && 0 == successCount) {
                    Integer errorCode = httpResults[0].getErrorCode();
                    String errorDesc = httpResults[0].getErrorDesc();
                    throw new SDKException(errorCode, errorDesc);
                }
            } else {
                throw new SDKException(SdkError.SYSTEM_ERROR);
            }
            transactionSubmitResponse.buildResponse(SdkError.SUCCESS, transactionSubmitResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionSubmitResponse.buildResponse(errorCode, errorDesc, transactionSubmitResult);
        } catch (InvalidProtocolBufferException | IllegalArgumentException e) {
            transactionSubmitResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionSubmitResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            transactionSubmitResponse.buildResponse(SdkError.CONNECTN_BLOCKCHAIN_ERROR, transactionSubmitResult);
        } catch (Exception exception) {
            transactionSubmitResponse.buildResponse(SdkError.SYSTEM_ERROR, transactionSubmitResult);
        }
        return transactionSubmitResponse;
    }

    /**
     * @Author riven
     * @Method getInfo
     * @Params [transactionGetInfoRequest]
     * @Return io.bumo.model.response.TransactionGetInfoResponse
     * @Date 2018/7/5 19:04
     */
    @Override
    public TransactionGetInfoResponse getInfo(TransactionGetInfoRequest transactionGetInfoRequest) {
        TransactionGetInfoResponse transactionGetInfoResponse = new TransactionGetInfoResponse();
        TransactionGetInfoResult transactionGetInfoResult = new TransactionGetInfoResult();
        try {
            if (Tools.isEmpty(General.url)) {
                throw new SDKException(SdkError.URL_EMPTY_ERROR);
            }
            if (Tools.isEmpty(transactionGetInfoRequest)) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String hash = transactionGetInfoRequest.getHash();
            if (Tools.isEmpty(hash) || hash.length() != Constant.HASH_HEX_LENGTH) {
                throw new SDKException(SdkError.INVALID_HASH_ERROR);
            }
            transactionGetInfoResponse = getTransactionInfo(hash);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionGetInfoResponse.buildResponse(errorCode, errorDesc, transactionGetInfoResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            transactionGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, transactionGetInfoResult);
        } catch (Exception exception) {
            transactionGetInfoResponse.buildResponse(SdkError.SYSTEM_ERROR, transactionGetInfoResult);
        }
        return transactionGetInfoResponse;
    }

    /**
     * @Author riven
     * @Method buildOperations
     * @Params [operationBase, transaction]
     * @Return void
     * @Date 2018/7/23 10:19
     */
    private void buildOperations(BaseOperation[] operationBase, String transSourceAddress, Chain.Transaction.Builder transaction) throws SDKException {
        for (int i = 0; i < operationBase.length; i++) {
            Chain.Operation operation;
            OperationType operationType = operationBase[i].getOperationType();
            switch (operationType) {
                case ACCOUNT_ACTIVATE:
                    operation = AccountServiceImpl.activate((AccountActivateOperation) operationBase[i], transSourceAddress);
                    break;
                case ACCOUNT_SET_METADATA:
                    operation = AccountServiceImpl.setMetadata((AccountSetMetadataOperation) operationBase[i]);
                    break;
                case ACCOUNT_SET_PRIVILEGE:
                    operation = AccountServiceImpl.setPrivilege((AccountSetPrivilegeOperation) operationBase[i]);
                    break;
                case ASSET_ISSUE:
                    operation = AssetServiceImpl.issue((AssetIssueOperation) operationBase[i]);
                    break;
                case ASSET_SEND:
                    operation = AssetServiceImpl.send((AssetSendOperation) operationBase[i], transSourceAddress);
                    break;
                case BU_SEND:
                    operation = BUServiceImpl.send((BUSendOperation) operationBase[i], transSourceAddress);
                    break;
                case TOKEN_ISSUE:
                    operation = Ctp10TokenServiceImpl.issue((Ctp10TokenIssueOperation) operationBase[i]);
                    break;
                case TOKEN_TRANSFER:
                    operation = Ctp10TokenServiceImpl.transfer((Ctp10TokenTransferOperation) operationBase[i], transSourceAddress);
                    break;
                case TOKEN_TRANSFER_FROM:
                    operation = Ctp10TokenServiceImpl.transferFrom((Ctp10TokenTransferFromOperation) operationBase[i], transSourceAddress);
                    break;
                case TOKEN_APPROVE:
                    operation = Ctp10TokenServiceImpl.approve((Ctp10TokenApproveOperation) operationBase[i], transSourceAddress);
                    break;
                case TOKEN_ASSIGN:
                    operation = Ctp10TokenServiceImpl.assign((Ctp10TokenAssignOperation) operationBase[i], transSourceAddress);
                    break;
                case TOKEN_CHANGE_OWNER:
                    operation = Ctp10TokenServiceImpl.changeOwner((Ctp10TokenChangeOwnerOperation) operationBase[i], transSourceAddress);
                    break;
                case CONTRACT_CREATE:
                    operation = ContractServiceImpl.create((ContractCreateOperation) operationBase[i]);
                    break;
                case CONTRACT_INVOKE_BY_ASSET:
                    operation = ContractServiceImpl.invokeByAsset((ContractInvokeByAssetOperation) operationBase[i], transSourceAddress);
                    break;
                case CONTRACT_INVOKE_BY_BU:
                    operation = ContractServiceImpl.invokeByBU((ContractInvokeByBUOperation) operationBase[i], transSourceAddress);
                    break;
                case LOG_CREATE:
                    operation = LogServiceImpl.create((LogCreateOperation) operationBase[i]);
                    break;
                default:
                    throw new SDKException(SdkError.OPERATIONS_ONE_ERROR);
            }
            if (Tools.isEmpty(operation)) {
                throw new SDKException(SdkError.OPERATIONS_ONE_ERROR);
            }
            transaction.addOperations(operation);
        }
    }

    public static TransactionGetInfoResponse getTransactionInfo(String hash) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        if (Tools.isEmpty(General.url)) {
            throw new SDKException(SdkError.URL_EMPTY_ERROR);
        }
        String getInfoUrl = General.transactionGetInfoUrl(hash);
        String result = HttpKit.get(getInfoUrl);
        return JSONObject.parseObject(result, TransactionGetInfoResponse.class);
    }
}

