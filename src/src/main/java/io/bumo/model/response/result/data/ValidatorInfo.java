package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/12 01:03
 */
public class ValidatorInfo {
    @JSONField(name = "address")
    private String address;

    @JSONField(name = "pledge_coin_amount")
    private Long pledgeCoinAmount;

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/12 01:04
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/12 01:05
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @Author riven
     * @Method getPledgeCoinAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 01:05
     */
    public Long getPledgeCoinAmount() {
        return pledgeCoinAmount;
    }

    /**
     * @Author riven
     * @Method setPledgeCoinAmount
     * @Params [pledgeCoinAmount]
     * @Return void
     * @Date 2018/7/12 01:22
     */
    public void setPledgeCoinAmount(Long pledgeCoinAmount) {
        this.pledgeCoinAmount = pledgeCoinAmount;
    }
}
