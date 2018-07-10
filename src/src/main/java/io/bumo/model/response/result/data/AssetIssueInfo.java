package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:39
 */
public class AssetIssueInfo {
    @JSONField(name = "code")
    private String code;

    @JSONField(name = "amount")
    private Long amount;

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:39
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/5 16:39
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getAmount
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:39
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @Author riven
     * @Method setAmount
     * @Params [amount]
     * @Return void
     * @Date 2018/7/5 16:40
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
