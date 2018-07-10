package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 11:07
 */
public class Fees {
    @JSONField(name = "base_reserve")
    private long baseReserve;

    @JSONField(name = "gas_price")
    private long gasPrice;

    /**
     * @Author riven
     * @Method getBaseReserve
     * @Params []
     * @Return long
     * @Date 2018/7/4 15:13
     */
    public long getBaseReserve() {
        return baseReserve;
    }

    /**
     * @Author riven
     * @Method setBaseReserve
     * @Params [baseReserve]
     * @Return void
     * @Date 2018/7/4 15:12
     */
    public void setBaseReserve(long baseReserve) {
        this.baseReserve = baseReserve;
    }

    /**
     * @Author riven
     * @Method getGasPrice
     * @Params []
     * @Return long
     * @Date 2018/7/4 15:11
     */
    public long getGasPrice() {
        return gasPrice;
    }

    /**
     * @Author riven
     * @Method setGasPrice
     * @Params [gasPrice]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setGasPrice(long gasPrice) {
        this.gasPrice = gasPrice;
    }
}
