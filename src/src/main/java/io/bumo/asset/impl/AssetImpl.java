package io.bumo.asset.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import io.bumo.asset.Asset;
import io.bumo.common.General;
import io.bumo.crypto.http.HttpKit;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.AssetGetInfoRequest;
import io.bumo.model.request.Operation.AssetIssueOperation;
import io.bumo.model.request.Operation.AssetSendOperation;
import io.bumo.model.response.AssetGetInfoResponse;
import io.bumo.model.response.result.AssetGetInfoResult;

/**
 * @Author riven
 * @Date 2018/7/3 17:21
 */
public class AssetImpl implements Asset {
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
        do {
            AssetGetInfoResult assetGetResult = new AssetGetInfoResult();
            String address = assetGetRequest.getAddress();
            if (!PublicKey.isAddressValid(address)) {
                assetGetResponse.buildResponse(SdkError.INVALID_ADDRESS_ERROR, assetGetResult);
                break;
            }
            String code = assetGetRequest.getCode();
            if (code == null || (code != null && code.length() < 1 || code.length() > 1024)) {
                assetGetResponse.buildResponse(SdkError.INVALID_ASSET_CODE_ERROR, assetGetResult);
                break;
            }
            String issuer = assetGetRequest.getIssuer();
            if (!PublicKey.isAddressValid(issuer)) {
                assetGetResponse.buildResponse(SdkError.INVALID_ISSUER_ADDRESS_ERROR, assetGetResult);
                break;
            }
            try {
                String accountGetInfoUrl = General.assetGetUrl(address, code, issuer);
                String result = HttpKit.get(accountGetInfoUrl);
                assetGetResponse = JSON.parseObject(result, AssetGetInfoResponse.class);
                SdkError.checkErrorCode(assetGetResponse);
                Integer errorCode = assetGetResponse.getErrorCode();
                if (errorCode != null && errorCode.intValue() == 4) {
                    throw new SDKException(errorCode, "Code (" + code +") not exist");
                }
            } catch (Exception e) {
                assetGetResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR);
                break;
            }
        } while (false);

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
        String sourceAddress = assetIssueOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String code = assetIssueOperation.getCode();
        if (code == null || (code != null && (code.length() < 1 || code.length() > 1024))) {
            throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
        }
        Long amount = assetIssueOperation.getAmount();
        if (amount == null || (amount != null && amount <= 0)) {
            throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
        }
        String metadata = assetIssueOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }

        // build Operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
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

        return operation.build();
    }

    /**
     * @Author riven
     * @Method send
     * @Params [assetSendRequest]
     * @Return io.bumo.model.response.AssetSendResponse
     * @Date 2018/7/5 11:45
     */
    public static Chain.Operation send(AssetSendOperation assetSendOperation) throws SDKException {
        String sourceAddress = assetSendOperation.getSourceAddress();
        if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
            throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
        }
        String destAddress = assetSendOperation.getDestAddress();
        if (!PublicKey.isAddressValid(destAddress)) {
            throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
        }
        if (sourceAddress != null && sourceAddress.equals(destAddress)) {
            throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
        }
        String code = assetSendOperation.getCode();
        if (code == null || (code != null && (code.length() < 1 || code.length() > 1024))) {
            throw new SDKException(SdkError.INVALID_ASSET_CODE_ERROR);
        }
        String issuer = assetSendOperation.getIssuer();
        if (!PublicKey.isAddressValid(issuer)) {
            throw new SDKException(SdkError.INVALID_ISSUER_ADDRESS_ERROR);
        }
        Long amount = assetSendOperation.getAmount();
        if (amount == null || (amount != null && amount < 0)) {
            throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
        }
        String metadata = assetSendOperation.getMetadata();
        if (metadata != null && !HexFormat.isHexString(metadata)) {
            throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
        }

        // build Operation
        Chain.Operation.Builder operation = Chain.Operation.newBuilder();
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

        return operation.build();
    }
}
