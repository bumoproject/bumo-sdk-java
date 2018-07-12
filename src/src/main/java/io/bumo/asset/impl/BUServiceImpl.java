package io.bumo.asset.impl;

import com.google.protobuf.ByteString;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.exception.EncException;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.Operation.BUSendOperation;

/**
 * @Author riven
 * @Date 2018/7/5 12:22
 */
public class BUServiceImpl {

    public static Chain.Operation send(BUSendOperation buSendOperation) throws SDKException {
        Chain.Operation.Builder operation;
        try {
            String sourceAddress = buSendOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String destAddress = buSendOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            if (sourceAddress != null && sourceAddress.equals(destAddress)) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            Long amount = buSendOperation.getAmount();
            if (amount == null || (amount != null && amount <= 0)) {
                throw new SDKException(SdkError.INVALID_ASSET_AMOUNT_ERROR);
            }
            String metadata = buSendOperation.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }

            // build Operation
            operation = Chain.Operation.newBuilder();
            operation.setType(Chain.Operation.Type.PAY_COIN);
            if (sourceAddress != null) {
                operation.setSourceAddress(sourceAddress);
            }
            if (metadata != null) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            Chain.OperationPayCoin.Builder operationPayCoin = operation.getPayCoinBuilder();
            operationPayCoin.setDestAddress(destAddress);
            operationPayCoin.setAmount(amount);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

        return operation.build();
    }
}
