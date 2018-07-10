package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.Fees;

/**
 * @Author riven
 * @Date 2018/7/3 17:27
 */
public class BlockGetFeesResult {
    @JSONField(name = "fees")
    private Fees fees;

    /**
     * @Author riven
     * @Method getFees
     * @Params []
     * @Return io.bumo.model.response.result.data.Fees
     * @Date 2018/7/4 15:12
     */
    public Fees getFees() {
        return fees;
    }

    /**
     * @Author riven
     * @Method setFees
     * @Params [fees]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void setFees(Fees fees) {
        this.fees = fees;
    }
}
