package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 23:49
 */
public class TestTx {
    @JSONField(name = "transaction_env")
    private TestTransactionFees transactionEnv;

    /**
     * @Author riven
     * @Method getTxs
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionEnv
     * @Date 2018/7/5 23:52
     */
    public TestTransactionFees getTransactionEnv() {
        return transactionEnv;
    }

    /**
     * @Author riven
     * @Method setTxs
     * @Params [transactionEnv]
     * @Return void
     * @Date 2018/7/5 23:52
     */
    public void setTransactionEnv(TestTransactionFees transactionEnv) {
        this.transactionEnv = transactionEnv;
    }
}
