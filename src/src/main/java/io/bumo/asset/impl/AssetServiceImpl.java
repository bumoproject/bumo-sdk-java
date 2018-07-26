package io.bumo.asset.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import io.bumo.asset.AssetService;
import io.bumo.common.General;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.exception.EncException;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.AssetGetInfoRequest;
import io.bumo.model.request.Operation.AssetIssueOperation;
import io.bumo.model.request.Operation.AssetSendOperation;
import io.bumo.model.response.AssetGetInfoResponse;
import io.bumo.model.response.result.AssetGetInfoResult;
import io.bumo.model.response.result.data.AssetInfo;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @Author riven
 * @Date 2018/7/3 17:21
 */
public class AssetServiceImpl implements AssetService {
    /**
     * @Author riven
     * @Method getInfo
     * @Params [assetGetRequest]
     * @Return io.bumo.model.response.AssetGetInfoResponse
     * @Date 2018/7/5 12:05
     */
    @Override
    public AssetGetInfoResponse getInfo(AssetGetInfoRequest assetGetRequest) {
        AssetGetInfoResponse assetGetResponse = new AssetGetInfoResponse();
        AssetGetInfoResult assetGetResult = new AssetGetInfoResult();

        try {
            if (null == assetGetRequest) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String address = assetGetRequest.getAddress();
            if (!PublicKey.isAddressValid(address)) {
                throw new SDKException(SdkError.INVALID_ADDRESS_ERROR);
            }
            String code = assetGetRequest.getCode();
            if (code == null || (code != null && code.length() < 1 || code.length() > 64)) {
                throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
            }
            String issuer = assetGetRequest.getIssuer();
            if (!PublicKey.isAddressValid(issuer)) {
                throw new SDKException(SdkError.INVALID_ISSUER_ADDRESS_ERROR);
            }
            String accountGetInfoUrl = General.assetGetUrl(address, code, issuer);
            String result = HttpKit.get(accountGetInfoUrl);
            assetGetResponse = JSON.parseObject(result, AssetGetInfoResponse.class);
            Integer errorCode = assetGetResponse.getErrorCode();
            String errorDesc = assetGetResponse.getErrorDesc();
            if (errorCode != null && errorCode.intValue() == 4) {
                throw new SDKException(errorCode, (null == errorDesc ? "Code (" + code +") not exist" : errorDesc));
            }
            SdkError.checkErrorCode(assetGetResponse);
            AssetInfo[] assetInfos = assetGetResponse.getResult().getAssets();
            if (null == assetInfos || (assetInfos != null && assetInfos.length == 0)) {
                throw new SDKException(SdkError.NO_ASSET_ERROR);
            }
        } catch (SDKException sdkException) {
            Integer errorCode = sdkException.getErrorCode();
            String errorDesc = sdkException.getErrorDesc();
            assetGetResponse.buildResponse(errorCode, errorDesc, assetGetResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            assetGetResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, assetGetResult);
        } catch (Exception e) {
            e.printStackTrace();
            assetGetResponse.buildResponse(SdkError.SYSTEM_ERROR, assetGetResult);
        }

        return assetGetResponse;
    }

    /**
     * @Author riven
     * @Method issue
     * @Params [assetIssueRequest]
     * @Return io.bumo.model.response.AssetIssueResponse
     * @Date 2018/7/5 11:36
     */
    public static Chain.Operation issue(AssetIssueOperation assetIssueOperation) throws SDKException {
        Chain.Operation.Builder operation;
        try {
            if (null == assetIssueOperation) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String sourceAddress = assetIssueOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String code = assetIssueOperation.getCode();
            if (code == null || (code != null && (code.length() < 1 || code.length() > 64))) {
                throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
            }
            Long amount = assetIssueOperation.getAmount();
            if (amount == null || (amount != null && amount <= 0)) {
                throw new SDKException(SdkError.INVALID_ISSUE_AMMOUNT_ERROR);
            }
            String metadata = assetIssueOperation.getMetadata();
            operation = Chain.Operation.newBuilder();
            // build Operation
            operation.setType(Chain.Operation.Type.ISSUE_ASSET);
            if (sourceAddress != null) {
                operation.setSourceAddress(sourceAddress);
            }
            if (metadata != null) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            Chain.OperationIssueAsset.Builder operationIssueAsset = operation.getIssueAssetBuilder();
            operationIssueAsset.setCode(code);
            operationIssueAsset.setAmount(amount);
        } catch (SDKException sdkException) {
            throw sdkException;
        }  catch (Exception e) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }


        return operation.build();
    }

    /**
     * @Author riven
     * @Method send
     * @Params [assetSendRequest]
     * @Return io.bumo.model.response.AssetSendResponse
     * @Date 2018/7/5 11:45
     */
    public static Chain.Operation send(AssetSendOperation assetSendOperation, String transSourceAddress) throws SDKException {
        Chain.Operation.Builder operation = null;
        try {
            if (null == assetSendOperation) {
                throw new SDKException(SdkError.REQUEST_NULL_ERROR);
            }
            String sourceAddress = assetSendOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String destAddress = assetSendOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            if ((sourceAddress != null && sourceAddress.equals(destAddress) || transSourceAddress.equals(destAddress))) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            String code = assetSendOperation.getCode();
            if (code == null || (code != null && (code.length() < 1 || code.length() > 64))) {
                throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
            }
            String issuer= assetSendOperation.getIssuer();
            if (!PublicKey.isAddressValid(issuer)) {
                throw new SDKException(SdkError.INVALID_ISSUER_ADDRESS_ERROR);
            }
            Long amount = assetSendOperation.getAmount();
            if (amount == null || (amount != null && amount < 0)) {
                throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
            }
            String metadata = assetSendOperation.getMetadata();
            // build Operation
            operation = Chain.Operation.newBuilder();
            operation.setType(Chain.Operation.Type.PAY_ASSET);
            if (sourceAddress != null) {
                operation.setSourceAddress(sourceAddress);
            }
            if (metadata != null) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            Chain.OperationPayAsset.Builder operationPayAsset = operation.getPayAssetBuilder();
            operationPayAsset.setDestAddress(destAddress);
            Chain.Asset.Builder asset = operationPayAsset.getAssetBuilder();
            Chain.AssetKey.Builder assetKey = asset.getKeyBuilder();
            assetKey.setCode(code);
            assetKey.setIssuer(issuer);
            asset.setAmount(amount);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

        return operation.build();
    }
}
