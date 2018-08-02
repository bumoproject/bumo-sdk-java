package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:15
 */
public class Ctp10TokenChangeOwnerOperation extends BaseOperation {
    private OperationType operationType = OperationType.TOKEN_CHANGE_OWNER;
    private String contractAddress;
    private String tokenOwner;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:16
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
     * @Date 2018/7/9 17:16
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/9 17:16
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getTokenOwner
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:16
     */
    public String getTokenOwner() {
        return tokenOwner;
    }

    /**
     * @Author riven
     * @Method setTokenOwner
     * @Params [tokenOwner]
     * @Return void
     * @Date 2018/7/9 18:59
     */
    public void setTokenOwner(String tokenOwner) {
        this.tokenOwner = tokenOwner;
    }
}
