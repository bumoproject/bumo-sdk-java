package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:43
 */
public class BaseOperation {
    private OperationType operationType;
    private String sourceAddress;
    private String metadata;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 22:25
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getSourceAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:46
     */
    public String getSourceAddress() {
        return sourceAddress;
    }

    /**
     * @Author riven
     * @Method setSourceAddress
     * @Params [sourceAddress]
     * @Return void
     * @Date 2018/7/9 16:46
     */
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * @Author riven
     * @Method getMetadata
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:46
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * @Author riven
     * @Method setMetadata
     * @Params [metadata]
     * @Return void
     * @Date 2018/7/9 16:52
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
