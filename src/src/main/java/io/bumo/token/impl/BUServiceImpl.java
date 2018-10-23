package io.bumo.token.impl;

import com.google.protobuf.ByteString;
import io.bumo.common.Tools;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.key.PublicKey;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.operation.BUSendOperation;

/**
 * @Author riven
 * @Date 2018/7/5 12:22
 */
public class BUServiceImpl {

    public static Chain.Operation send(BUSendOperation buSendOperation, String transSourceAddress) throws SDKException {
        Chain.Operation.Builder operation;
        try {
            String sourceAddress = buSendOperation.getSourceAddress();
            if (!Tools.isEmpty(sourceAddress) && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String destAddress = buSendOperation.getDestAddress();
            if (!PublicKey.isAddressValid(destAddress)) {
                throw new SDKException(SdkError.INVALID_DESTADDRESS_ERROR);
            }
            boolean isNotValid = (!Tools.isEmpty(sourceAddress) && sourceAddress.equals(destAddress)) ||
                    (Tools.isEmpty(sourceAddress) && transSourceAddress.equals(destAddress));
            if (isNotValid) {
                throw new SDKException(SdkError.SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR);
            }
            Long amount = buSendOperation.getAmount();
            if (Tools.isEmpty(amount) || amount < 0) {
                throw new SDKException(SdkError.INVALID_BU_AMOUNT_ERROR);
            }
            String metadata = buSendOperation.getMetadata();
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
            operationPayCoin.setDestAddress(destAddress);
            operationPayCoin.setAmount(amount);
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception e) {
            throw new SDKException(SdkError.SYSTEM_ERROR.getCode(), e.getMessage());
        }

        return operation.build();
    }
}
