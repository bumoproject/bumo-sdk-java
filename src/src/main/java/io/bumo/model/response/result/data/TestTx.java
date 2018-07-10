package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 23:49
 */
public class TestTx {
    @JSONField(name = "transaction_env")
    private TransactionEnv transactionEnv;

    /**
     * @Author riven
     * @Method getTransactionEnv
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionEnv
     * @Date 2018/7/5 23:52
     */
    public TransactionEnv getTransactionEnv() {
        return transactionEnv;
    }

    /**
     * @Author riven
     * @Method setTransactionEnv
     * @Params [transactionEnv]
     * @Return void
     * @Date 2018/7/5 23:52
     */
    public void setTransactionEnv(TransactionEnv transactionEnv) {
        this.transactionEnv = transactionEnv;
    }
}
