package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:43
 */
public class BUSendInfo {
    @JSONField(name = "dest_address")
    private String destAddress;

    @JSONField(name = "amount")
    private Long amount;

    @JSONField(name = "input")
    private String input;

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:44
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/7/5 16:44
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:44
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/5 16:44
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * @Author riven
     * @Method getInput
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:44
     */
    public String getInput() {
        return input;
    }

    /**
     * @Author riven
     * @Method setInput
     * @Params [input]
     * @Return void
     * @Date 2018/7/5 16:49
     */
    public void setInput(String input) {
        this.input = input;
    }
}
