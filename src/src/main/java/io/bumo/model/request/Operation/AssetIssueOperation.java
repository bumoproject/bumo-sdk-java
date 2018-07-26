package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:53
 */
public class AssetIssueOperation extends BaseOperation {
    private OperationType operationType = OperationType.ASSET_ISSUE;
    private String code;
    private Long amount;


    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:14
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:53
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/9 16:54
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getTokenAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 16:54
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setTokenAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/9 16:54
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
