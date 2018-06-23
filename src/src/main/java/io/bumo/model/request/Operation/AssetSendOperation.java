package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:54
 */
public class AssetSendOperation extends BaseOperation {
    private OperationType operationType = OperationType.ASSET_SEND;
    private String destAddress;
    private String code;
    private String issuer;
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
     * @Date 2018/7/9 16:55
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/9 16:55
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:55
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/9 16:56
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getIssuer
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:56
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @Author riven
     * @Method setIssuer
     * @Params [issuer]
     * @Return void
     * @Date 2018/7/9 16:56
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * @Author riven
     * @Method getAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 16:56
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/9 16:56
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
