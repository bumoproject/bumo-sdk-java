package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 15:25
 */
public class Ctp10TokenGetTotalSupplyResult {
    @JSONField(name = "totalSupply")
    private String totalSupply;

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 22:53
     */
    public String getTotalSupply() {
        return totalSupply;
    }

    /**
     * @Author riven
     * @Method setTotalSupply
     * @Params [totalSupply]
     * @Return void
     * @Date 2018/7/11 22:53
     */
    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }
}
