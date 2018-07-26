package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:56
 */
public class BUSendOperation extends BaseOperation {
    private OperationType operationType = OperationType.BU_SEND;
    private String destAddress;
    private Long amount;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:15
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:57
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/9 16:57
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getTokenAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 16:57
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setTokenAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/9 16:57
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
