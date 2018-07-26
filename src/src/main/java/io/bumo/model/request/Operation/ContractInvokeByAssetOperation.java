package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:18
 */
public class ContractInvokeByAssetOperation extends BaseOperation {
    private OperationType operationType = OperationType.CONTRACT_INVOKE_BY_ASSET;
    private String contractAddress;
    private String code;
    private String issuer;
    private Long assetAmount;
    private String input;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:19
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getContractAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:19
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @Author riven
     * @Method setContractAddress
     * @Params [contractAddress]
     * @Return void
     * @Date 2018/7/9 17:19
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:19
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/9 17:19
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getIssuer
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:19
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @Author riven
     * @Method setIssuer
     * @Params [issuer]
     * @Return void
     * @Date 2018/7/9 17:19
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * @Author riven
     * @Method getTokenAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 17:19
     */
    public Long getAssetAmount() {
        return assetAmount;
    }

    /**
     * @Author riven
     * @Method setTokenAmount
     * @Params [assetAmount]
     * @Return void
     * @Date 2018/7/9 17:19
     */
    public void setAssetAmount(Long assetAmount) {
        this.assetAmount = assetAmount;
    }

    /**
     * @Author riven
     * @Method getInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:19
     */
    public String getInput() {
        return input;
    }

    /**
     * @Author riven
     * @Method setInput
     * @Params [input]
     * @Return void
     * @Date 2018/7/9 17:20
     */
    public void setInput(String input) {
        this.input = input;
    }
}
