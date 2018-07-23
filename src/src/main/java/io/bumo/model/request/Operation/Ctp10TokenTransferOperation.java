package io.bumo.model.request.operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:07
 */
public class Ctp10TokenTransferOperation extends BaseOperation {
    private String contractAddress;
    private String destAddress;
    private String tokenAmount;

    public Ctp10TokenTransferOperation() {
        operationType = OperationType.TOKEN_TRANSFER;
    }

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:08
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
     * @Date 2018/7/9 17:09
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/9 17:09
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:09
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/9 17:09
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getTokenAmount
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:09
     */
    public String getTokenAmount() {
        return tokenAmount;
    }

    /**
     * @Author riven
     * @Method setTokenAmount
     * @Params [tokenAmount]
     * @Return void
     * @Date 2018/7/9 17:09
     */
    public void setTokenAmount(String tokenAmount) {
        this.tokenAmount = tokenAmount;
    }
}
