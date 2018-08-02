package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 15:25
 */
public class TokenGetSymbolResult {
    @JSONField(name = "symbol")
    private String symbol;

    /**
     * @Author riven
     * @Method getSymbol
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 15:43
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @Author riven
     * @Method setSymbol
     * @Params [symbol]
     * @Return void
     * @Date 2018/7/6 16:16
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
