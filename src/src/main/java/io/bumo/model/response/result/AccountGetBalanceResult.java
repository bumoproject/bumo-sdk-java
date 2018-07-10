package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 17:09
 */
public class AccountGetBalanceResult {
    @JSONField(name = "balance")
    private Long balance;

    /**
     * @Author riven
     * @Method getBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 17:09
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * @Author riven
     * @Method setBalance
     * @Params [balance]
     * @Return void
     * @Date 2018/7/4 17:09
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
