package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 13:34
 */
public class TokenGetInfoResult {
    @JSONField(name = "symbol")
    private String symbol;

    @JSONField(name = "decimals")
    private String decimals;

    @JSONField(name = "totalSupply")
    private String totalSupply;

    @JSONField(name = "name")
    private String name;

    /**
     * @Author riven
     * @Method getSymbol
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 13:39
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @Author riven
     * @Method setSymbol
     * @Params [symbol]
     * @Return void
     * @Date 2018/7/6 13:39
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @Author riven
     * @Method getDecimals
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 13:39
     */
    public String getDecimals() {
        return decimals;
    }

    /**
     * @Author riven
     * @Method setDecimals
     * @Params [decimals]
     * @Return void
     * @Date 2018/7/6 13:39
     */
    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 13:39
     */
    public String getTotalSupply() {
        return totalSupply;
    }

    /**
     * @Author riven
     * @Method setTotalSupply
     * @Params [totalSupply]
     * @Return void
     * @Date 2018/7/6 13:39
     */
    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    /**
     * @Author riven
     * @Method getName
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 13:39
     */
    public String getName() {
        return name;
    }

    /**
     * @Author riven
     * @Method setName
     * @Params [name]
     * @Return void
     * @Date 2018/7/6 13:39
     */
    public void setName(String name) {
        this.name = name;
    }
}
