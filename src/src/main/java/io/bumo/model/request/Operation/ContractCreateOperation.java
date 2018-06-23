package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:17
 */
public class ContractCreateOperation extends BaseOperation {
    private OperationType operationType = OperationType.CONTRACT_CREATE;
    private Long initBalance;
    private Integer type;
    private String payload;
    private String initInput;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:17
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getInitBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 17:17
     */
    public Long getInitBalance() {
        return initBalance;
    }

    /**
     * @Author riven
     * @Method setInitBalance
     * @Params [initBalance]
     * @Return void
     * @Date 2018/7/9 17:17
     */
    public void setInitBalance(Long initBalance) {
        this.initBalance = initBalance;
    }

    /**
     * @Author riven
     * @Method getType
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/7/9 17:17
     */
    public Integer getType() {
        return type;
    }

    /**
     * @Author riven
     * @Method setType
     * @Params [type]
     * @Return void
     * @Date 2018/7/9 17:17
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @Author riven
     * @Method getPayload
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:18
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @Author riven
     * @Method setPayload
     * @Params [payload]
     * @Return void
     * @Date 2018/7/9 17:18
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * @Author riven
     * @Method getInitInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 22:43
     */
    public String getInitInput() {
        return initInput;
    }

    /**
     * @Author riven
     * @Method setInitInput
     * @Params [initInput]
     * @Return void
     * @Date 2018/7/10 10:44
     */
    public void setInitInput(String initInput) {
        this.initInput = initInput;
    }
}
