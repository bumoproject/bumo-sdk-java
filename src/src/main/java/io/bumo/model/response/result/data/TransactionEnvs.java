package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/11 20:47
 */
public class TransactionEnvs {
    @JSONField(name = "transaction_env")
    private TransactionEnv transactionEnv;

    /**
     * @Author riven
     * @Method getTransactionEnv
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionEnv
     * @Date 2018/7/11 20:48
     */
    public TransactionEnv getTransactionEnv() {
        return transactionEnv;
    }

    /**
     * @Author riven
     * @Method setTransactionEnv
     * @Params [transactionEnv]
     * @Return void
     * @Date 2018/7/11 22:09
     */
    public void setTransactionEnv(TransactionEnv transactionEnv) {
        this.transactionEnv = transactionEnv;
    }
}
