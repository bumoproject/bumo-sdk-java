package io.bumo.blockchain.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import io.bumo.account.impl.AccountServiceImpl;
import io.bumo.asset.impl.AssetServiceImpl;
import io.bumo.asset.impl.BUServiceImpl;
import io.bumo.asset.impl.TokenServiceImpl;
import io.bumo.blockchain.BlockService;
import io.bumo.blockchain.TransactionService;
import io.bumo.common.General;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hash.HashUtil;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.*;
import io.bumo.model.request.Operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.Signature;

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
            // check sourceAddress
            String sourceAddress = transactionBuildBlobRequest.getSourceAddress();
            if (!PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            // check nonce
            Long nonce = transactionBuildBlobRequest.getNonce();
            if (nonce == null || (nonce != null && nonce < 1)) {
                throw new SDKException(SdkError.INVALID_NONCE_ERROR);
            }


            // check gasPrice
            Long gasPrice = transactionBuildBlobRequest.getGasPrice();
            if (gasPrice == null || (gasPrice != null && gasPrice < 1000)) {
                throw new SDKException(SdkError.INVALID_GASPRICE_ERROR);
            }

            // check feeLimit
            Long feeLimit = transactionBuildBlobRequest.getFeeLimit();
            if (feeLimit == null || (feeLimit != null && feeLimit < 1)) {
                throw new SDKException(SdkError.INVALID_FEELIMIT_ERROR);
            }

            Long ceilLedgerSeq = transactionBuildBlobRequest.getCelLedgerSeq();
            if (ceilLedgerSeq != null && ceilLedgerSeq < 0) {
                throw new SDKException(SdkError.INVALID_CEILLEDGERSEQ_ERROR);
            }

            // check metadata
            String metadata = transactionBuildBlobRequest.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }
            // check operation and build transaction
            Chain.Transaction.Builder transaction = Chain.Transaction.newBuilder();
            BaseOperation[] baseOperations = transactionBuildBlobRequest.getOperations();
            buildOperations(baseOperations, transaction);
            transaction.setSourceAddress(sourceAddress);
            transaction.setNonce(nonce);
            transaction.setFeeLimit(feeLimit);
            transaction.setGasPrice(gasPrice);

            if (metadata != null) {
                transaction.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            if (ceilLedgerSeq != null) {
                // get blockNumber
                BlockService blockService = new BlockServiceImpl();
                BlockGetNumberResponse blockGetNumberResponse = blockService.getNumber();
                Integer errorCode = blockGetNumberResponse.getErrorCode();
                if (blockGetNumberResponse.getErrorCode() != 0) {
                    String errorDesc = blockGetNumberResponse.getErrorDesc();
                    throw new SDKException(errorCode, errorDesc);
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
            transactionBuildBlobResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionBuildBlobResult);
        }

        return transactionBuildBlobResponse;
    }

    @Override
    public TransactionParseBlobResponse parseBlob(TransactionParseBlobRequest transactionParseBlobRequest) {
        TransactionParseBlobResponse transactionParseBlobResponse = new TransactionParseBlobResponse();
        TransactionParseBlobResult transactionParseBlobResult = new TransactionParseBlobResult();
        try {
            String blob = transactionParseBlobRequest.getBlob();
            if (blob == null || blob.isEmpty()) {
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
        } catch (Exception exception) {
            exception.printStackTrace();
            transactionParseBlobResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionParseBlobResult);
        }

        return transactionParseBlobResponse;
    }

    /**
     * @Author riven
     * @Method evaluationFee
     * @Params [transactionEvaluationFeeRequest]
     * @Return io.bumo.model.response.TransactionEvaluationFeeResponse
     * @Date 2018/7/5 19:04
     */
    @Override
    public TransactionEvaluationFeeResponse evaluationFee(TransactionEvaluationFeeRequest transactionEvaluationFeeRequest) {
        TransactionEvaluationFeeResponse transactionEvaluationFeeResponse = new TransactionEvaluationFeeResponse();
        TransactionEvaluationFeeResult transactionEvaluationFeeResult = new TransactionEvaluationFeeResult();
        try {
            // check sourceAddress
            String sourceAddress = transactionEvaluationFeeRequest.getSourceAddress();
            if (!PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            // check nonce
            Long nonce = transactionEvaluationFeeRequest.getNonce();
            if (nonce == null || (nonce != null && nonce < 1)) {
                throw new SDKException(SdkError.INVALID_NONCE_ERROR);
            }
            // check signatureNum
            Integer signatureNum = transactionEvaluationFeeRequest.getSignatureNumber();
            if (signatureNum == null || (signatureNum != null && signatureNum < 1)) {
                throw new SDKException(SdkError.INVALID_SIGNATURENUMBER_ERROR);
            }
            // check ceilLedgerSeq
            Long ceilLedgerSeq = transactionEvaluationFeeRequest.getCeilLedgerSeq();
            if (ceilLedgerSeq != null && ceilLedgerSeq < 0) {
                throw new SDKException(SdkError.INVALID_CEILLEDGERSEQ_ERROR);
            }
            // check metadata
            String metadata = transactionEvaluationFeeRequest.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }

            // build transaction
            Chain.Transaction.Builder transaction = Chain.Transaction.newBuilder();
            BaseOperation[] baseOperations = transactionEvaluationFeeRequest.getOperations();
            buildOperations(baseOperations, transaction);
            transaction.setSourceAddress(sourceAddress);
            transaction.setNonce(nonce);
            if (metadata != null) {
                transaction.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            if (ceilLedgerSeq != null) {
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
            transactionEvaluationFeeResponse = JSON.parseObject(result,TransactionEvaluationFeeResponse.class);

        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionEvaluationFeeResponse.buildResponse(errorCode, errorDesc, transactionEvaluationFeeResult);
        } catch (Exception e) {
            transactionEvaluationFeeResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, transactionEvaluationFeeResult);
        }
        return transactionEvaluationFeeResponse;
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
            String blob = transactionSignRequest.getBlob();
            byte[] blobBytes = HexFormat.hexToByte(blob);
             {
                Chain.Transaction.parseFrom(blobBytes);
            }
            String[] privateKeys = transactionSignRequest.getPrivateKeys();
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
        } catch (InvalidProtocolBufferException e) {
            transactionSignResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionSignResult);
        } catch (Exception e) {
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
            String blob = transactionSubmitRequest.getTransactionBlob();
            Chain.Transaction.parseFrom(HexFormat.hexToByte(blob));
            // build transaction request
            JSONObject transactionItemsRequest = new JSONObject();
            JSONArray transactionItems = new JSONArray();
            JSONObject transactionItem = new JSONObject();
            transactionItem.put("transaction_blob", blob);
            JSONArray signatureItems = new JSONArray();
            Signature[] signatures = transactionSubmitRequest.getSignatures();
            int i = 0;
            int length = signatures.length;
            for (; i < length; i++) {
                JSONObject signatureItem = new JSONObject();
                Signature signature = signatures[i];
                signatureItem.put("sign_data", signature.getSignData());
                signatureItem.put("public_key", signature.getPublicKey());
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
            if (0 == successCount) {
                Integer errorCode = httpResults[0].getErrorCode();
                String errorDesc = httpResults[0].getErrorDesc();
                throw new SDKException(errorCode, errorDesc);
            }
            transactionSubmitResult.setHash(httpResults[0].getHash());

            transactionSubmitResponse.buildResponse(SdkError.SUCCESS, transactionSubmitResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionSubmitResponse.buildResponse(errorCode, errorDesc, transactionSubmitResult);
        } catch (InvalidProtocolBufferException e) {
            transactionSubmitResponse.buildResponse(SdkError.INVALID_BLOB_ERROR, transactionSubmitResult);
        } catch (Exception e) {
            transactionSubmitResponse.buildResponse(SdkError.CONNECTN_BLOCKCHAIN_ERROR, transactionSubmitResult);
        }
        return  transactionSubmitResponse;
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
            String hash = transactionGetInfoRequest.getHash();
            if (hash == null || (hash != null && hash.length() != 64)) {
                throw new SDKException(SdkError.INVALID_HASH_ERROR);
            }
            String getInfoUrl = General.transactionGetInfoUrl(hash);
            String result = HttpKit.get(getInfoUrl);
            transactionGetInfoResponse = JSONObject.parseObject(result, TransactionGetInfoResponse.class);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            transactionGetInfoResponse.buildResponse(errorCode, errorDesc, transactionGetInfoResult);
        } catch (Exception e) {
            transactionGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, transactionGetInfoResult);
        }
        return transactionGetInfoResponse;
    }

    private void buildOperations(BaseOperation[] operationBase, Chain.Transaction.Builder transaction) throws SDKException {
        for (int i = 0; i < operationBase.length; i++) {
            Chain.Operation operation = null;
            switch (operationBase[i].getOperationType()) {
                case ACCOUNT_ACTIVATE:
                    operation = AccountServiceImpl.activate((AccountActiviateOperation)operationBase[i]);
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
                    operation = AssetServiceImpl.send((AssetSendOperation) operationBase[i]);
                    break;
                case BU_SEND:
                    operation = BUServiceImpl.send((BUSendOperation) operationBase[i]);
                    break;
                case TOKEN_ISSUE:
                    operation = TokenServiceImpl.issue((TokenIssueOperation) operationBase[i]);
                    break;
                case TOKEN_TRANSFER:
                    operation = TokenServiceImpl.transfer((TokenTransferOperation) operationBase[i]);
                    break;
                case TOKEN_TRANSFER_FROM:
                    operation = TokenServiceImpl.transferFrom((TokenTransferFromOperation) operationBase[i]);
                    break;
                case TOKEN_APPROVE:
                    operation = TokenServiceImpl.approve((TokenApproveOperation) operationBase[i]);
                    break;
                case TOKEN_ASSIGN:
                    operation = TokenServiceImpl.assign((TokenAssignOperation) operationBase[i]);
                    break;
                case TOKEN_CHANGE_OWNER:
                    operation = TokenServiceImpl.changeOwner((TokenChangeOwnerOperation) operationBase[i]);
                    break;
                case CONTRACT_CREATE:
                    operation = ContractServiceImpl.create((ContractCreateOperation) operationBase[i]);
                    break;
                case CONTRACT_INVOKE_BY_ASSET:
                    operation = ContractServiceImpl.invokeByAsset((ContractInvokeByAssetOperation) operationBase[i]);
                    break;
                case CONTRACT_INVOKE_BY_BU:
                    operation = ContractServiceImpl.invokeByBU((ContractInvokeByBUOperation) operationBase[i]);
                    break;
            }
            transaction.addOperations(operation);
        }
    }
}

