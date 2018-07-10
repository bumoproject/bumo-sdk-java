package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 15:25
 */
public class TokenGetBalanceResult {
    @JSONField(name = "balance")
    private String balance;

    /**
     * @Author riven
     * @Method getBalance
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 16:17
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @Author riven
     * @Method setBalance
     * @Params [balance]
     * @Return void
     * @Date 2018/7/6 16:22
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
}
