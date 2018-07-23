package io.bumo.log;

import com.google.protobuf.ByteString;
import io.bumo.crypto.protobuf.Chain;
import io.bumo.encryption.exception.EncException;
import io.bumo.encryption.key.PublicKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.Operation.LogCreateOperation;

import java.util.List;

/**
 * @Author riven
 * @Date 2018/7/15 14:37
 */
public class LogServiceImpl {
    /**
     * @Author riven
     * @Method send
     * @Params [assetSendRequest]
     * @Return io.bumo.model.response.AssetSendResponse
     * @Date 2018/7/5 11:45
     */
    public static Chain.Operation create(LogCreateOperation logCreateOperation) throws SDKException {
        Chain.Operation.Builder operation = null;
        try {
            String sourceAddress = logCreateOperation.getSourceAddress();
            if (sourceAddress != null && !PublicKey.isAddressValid(sourceAddress)) {
                throw new SDKException(SdkError.INVALID_SOURCEADDRESS_ERROR);
            }
            String topic = logCreateOperation.getTopic();
            if (null == topic || (topic != null && (topic.length() < 1 || topic.length() > 128))) {
                throw new SDKException(SdkError.INVALID_LOG_TOPIC_ERROR);
            }
            List<String> datas = logCreateOperation.getDatas();
            if (null == datas || (datas != null && datas.size() == 0)) {
                throw new SDKException(SdkError.INVALID_LOG_DATA_ERROR);
            }
            for (int i = 0;i < datas.size(); i++) {
                String data = datas.get(i);
                if (data.length() < 1 || data.length() > 1024) {
                    throw new SDKException(SdkError.INVALID_LOG_DATA_ERROR);
                }
            }
            String metadata = logCreateOperation.getMetadata();
            if (metadata != null && !HexFormat.isHexString(metadata)) {
                throw new SDKException(SdkError.METADATA_NOT_HEX_STRING_ERROR);
            }

            // build Operation
            operation = Chain.Operation.newBuilder();
            operation.setType(Chain.Operation.Type.LOG);
            if (sourceAddress != null) {
                operation.setSourceAddress(sourceAddress);
            }
            if (metadata != null) {
                operation.setMetadata(ByteString.copyFromUtf8(metadata));
            }
            Chain.OperationLog.Builder operationLog = operation.getLogBuilder();
            if (sourceAddress != null) {
                operation.setSourceAddress(sourceAddress);
            }
            operationLog.setTopic(topic);
            operationLog.addAllDatas(datas);
            if (metadata != null) {
                logCreateOperation.setMetadata(metadata);
            }
        } catch (SDKException sdkException) {
            throw sdkException;
        } catch (Exception exception) {
            throw new SDKException(SdkError.SYSTEM_ERROR);
        }

        return operation.build();
    }
}
