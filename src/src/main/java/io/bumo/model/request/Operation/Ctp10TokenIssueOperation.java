package io.bumo.model.request.operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 16:57
 */
public class Ctp10TokenIssueOperation extends BaseOperation {
    private Long initBalance;
    private String name;
    private String symbol;
    private Integer decimals;
    private String supply;

    public Ctp10TokenIssueOperation() {
        operationType = OperationType.TOKEN_ISSUE;
    }

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:06
     */
    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getInitBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/9 17:06
     */
    public Long getInitBalance() {
        return initBalance;
    }

    /**
     * @Author riven
     * @Method setInitBalance
     * @Params [initBalance]
     * @Return void
     * @Date 2018/7/9 17:06
     */
    public void setInitBalance(Long initBalance) {
        this.initBalance = initBalance;
    }

    /**
     * @Author riven
     * @Method getName
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:06
     */
    public String getName() {
        return name;
    }

    /**
     * @Author riven
     * @Method setName
     * @Params [name]
     * @Return void
     * @Date 2018/7/9 17:06
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @Author riven
     * @Method getSymbol
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:06
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @Author riven
     * @Method setSymbol
     * @Params [symbol]
     * @Return void
     * @Date 2018/7/9 17:06
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @Author riven
     * @Method getDecimals
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:06
     */
    public Integer getDecimals() {
        return decimals;
    }

    /**
     * @Author riven
     * @Method setDecimals
     * @Params [decimals]
     * @Return void
     * @Date 2018/7/9 17:06
     */
    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    /**
     * @Author riven
     * @Method getSupply
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:07
     */
    public String getSupply() {
        return supply;
    }

    /**
     * @Author riven
     * @Method setSupply
     * @Params [supply]
     * @Return void
     * @Date 2018/7/9 17:07
     */
    public void setSupply(String supply) {
        this.supply = supply;
    }
}
