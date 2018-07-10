package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 23:50
 */
public class TransactionEnv {
    @JSONField(name = "transaction")
    private TransactionFees transactionFees;

    /**
     * @Author riven
     * @Method getTransactionFees
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionFees
     * @Date 2018/7/5 23:53
     */
    public TransactionFees getTransactionFees() {
        return transactionFees;
    }

    /**
     * @Author riven
     * @Method setTransactionFees
     * @Params [transactionFees]
     * @Return void
     * @Date 2018/7/5 23:53
     */
    public void setTransactionFees(TransactionFees transactionFees) {
        this.transactionFees = transactionFees;
    }
}
