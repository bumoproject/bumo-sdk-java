package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:12
 */
public class TokenApproveOperation extends BaseOperation {
    private OperationType operationType = OperationType.TOKEN_APPROVE;
    private String contractAddress;
    private String spender;
    private String amount;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:12
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:12
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/9 17:12
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getSpender
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:12
     */
    public String getSpender() {
        return spender;
    }

    /**
     * @Author riven
     * @Method setSpender
     * @Params [spender]
     * @Return void
     * @Date 2018/7/9 17:12
     */
    public void setSpender(String spender) {
        this.spender = spender;
    }

    /**
     * @Author riven
     * @Method getAmount
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:13
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/9 17:13
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
