package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:09
 */
public class TokenTransferFromOperation extends BaseOperation {
    private OperationType operationType = OperationType.TOKEN_TRANSFER_FROM;
    private String contractAddress;
    private String fromAddress;
    private String destAddress;
    private String tokenAmount;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:10
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
     * @Date 2018/7/9 17:11
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/9 17:11
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getFromAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:11
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * @Author riven
     * @Method setFromAddress
     * @Params [fromAddress]
     * @Return void
     * @Date 2018/7/9 17:11
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:11
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/9 17:11
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getTokenAmount
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:11
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
