package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 01:21
 */
public class AssetInfo {
    @JSONField(name = "key")
    private AssetKey key;

    @JSONField(name = "amount")
    private Long amount;

    /**
     * @Author riven
     * @Method getKey
     * @Params []
     * @Return io.bumo.model.response.result.data.AssetKey
     * @Date 2018/7/5 01:25
     */
    public AssetKey getKey() {
        return key;
    }

    /**
     * @Author riven
     * @Method setKey
     * @Params [key]
     * @Return void
     * @Date 2018/7/5 01:26
     */
    public void setKey(AssetKey key) {
        this.key = key;
    }

    /**
     * @Author riven
     * @Method getAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 01:26
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/5 01:26
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
