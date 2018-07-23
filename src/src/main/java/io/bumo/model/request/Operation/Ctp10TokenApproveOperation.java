package io.bumo.model.request.operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:12
 */
public class Ctp10TokenApproveOperation extends BaseOperation {
    private String contractAddress;
    private String spender;
    private String tokenAmount;

    public Ctp10TokenApproveOperation() {
        operationType = OperationType.TOKEN_APPROVE;
    }

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:12
     */
    @Override
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
     * @Method getTokenAmount
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:13
     */
    public String getTokenAmount() {
        return tokenAmount;
    }

    /**
     * @Author riven
     * @Method setTokenAmount
     * @Params [tokenAmount]
     * @Return void
     * @Date 2018/7/9 17:13
     */
    public void setTokenAmount(String tokenAmount) {
        this.tokenAmount = tokenAmount;
    }
}
