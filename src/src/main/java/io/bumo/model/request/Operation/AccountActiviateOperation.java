package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:45
 */
public class AccountActiviateOperation extends BaseOperation {
    private OperationType operationType = OperationType.ACCOUNT_ACTIVATE;
    private String destAddress;
    private Long initBalance;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:04
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:46
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/9 16:46
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getInitBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 16:46
     */
    public Long getInitBalance() {
        return initBalance;
    }

    /**
     * @Author riven
     * @Method setInitBalance
     * @Params [initBalance]
     * @Return void
     * @Date 2018/7/9 16:52
     */
    public void setInitBalance(Long initBalance) {
        this.initBalance = initBalance;
    }
}
