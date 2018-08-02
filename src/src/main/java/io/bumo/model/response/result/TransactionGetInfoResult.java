package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.TransactionHistory;

/**
 * @Author riven
 * @Date 2018/7/5 16:53
 */
public class TransactionGetInfoResult {
    @JSONField(name = "total_count")
    private Long totalCount;

    @JSONField(name = "transactions")
    private TransactionHistory[] transactions;

    /**
     * @Author riven
     * @Method getTotalCount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:55
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * @Author riven
     * @Method setTotalCount
     * @Params [totalCount]
     * @Return void
     * @Date 2018/7/5 16:55
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @Author riven
     * @Method getTransactions
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionHistory[]
     * @Date 2018/7/5 16:55
     */
    public TransactionHistory[] getTransactions() {
        return transactions;
    }

    /**
     * @Author riven
     * @Method setTransactions
     * @Params [transactions]
     * @Return void
     * @Date 2018/7/5 16:55
     */
    public void setTransactions(TransactionHistory[] transactions) {
        this.transactions = transactions;
    }
}
