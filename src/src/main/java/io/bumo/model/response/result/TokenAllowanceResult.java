package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 12:33
 */
public class TokenAllowanceResult {
    @JSONField(name = "allowance")
    private String allowance;

    /**
     * @Author riven
     * @Method getAllowance
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 12:39
     */
    public String getAllowance() {
        return allowance;
    }

    /**
     * @Author riven
     * @Method setAllowance
     * @Params [allowance]
     * @Return void
     * @Date 2018/7/6 12:39
     */
    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }
}
