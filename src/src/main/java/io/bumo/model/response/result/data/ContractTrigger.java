package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/11 18:42
 */
public class ContractTrigger {
    @JSONField(name = "transaction")
    private TriggerTransaction transaction;

    /**
     * @Author riven
     * @Method getTransaction
     * @Params []
     * @Return io.bumo.model.response.result.data.TriggerTransaction
     * @Date 2018/7/11 18:45
     */
    public TriggerTransaction getTransaction() {
        return transaction;
    }

    /**
     * @Author riven
     * @Method setTransaction
     * @Params [transaction]
     * @Return void
     * @Date 2018/7/11 18:45
     */
    public void setTransaction(TriggerTransaction transaction) {
        this.transaction = transaction;
    }
}
