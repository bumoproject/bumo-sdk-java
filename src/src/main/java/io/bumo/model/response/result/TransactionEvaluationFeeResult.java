package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.TestTx;

/**
 * @Author riven
 * @Date 2018/7/5 15:55
 */
public class TransactionEvaluationFeeResult {
    @JSONField(name = "txs")
    private TestTx[] txs;

    /**
     * @Author riven
     * @Method getTxs
     * @Params []
     * @Return io.bumo.model.response.result.data.TestTx[]
     * @Date 2018/7/5 23:51
     */
    public TestTx[] getTxs() {
        return txs;
    }

    /**
     * @Author riven
     * @Method setTxs
     * @Params [txs]
     * @Return void
     * @Date 2018/7/5 23:51
     */
    public void setTxs(TestTx[] txs) {
        this.txs = txs;
    }
}
