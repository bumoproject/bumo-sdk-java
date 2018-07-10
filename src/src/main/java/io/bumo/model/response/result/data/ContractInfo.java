package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 14:07
 */
public class ContractInfo {
    @JSONField(name = "type")
    private Integer type = 0;

    @JSONField(name = "payload")
    private String payload;

    /**
     * @Author riven
     * @Method getType
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/7/5 14:08
     */
    public Integer getType() {
        return type;
    }

    /**
     * @Author riven
     * @Method setType
     * @Params [type]
     * @Return void
     * @Date 2018/7/5 14:08
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @Author riven
     * @Method getPayload
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 14:09
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @Author riven
     * @Method setPayload
     * @Params [payload]
     * @Return void
     * @Date 2018/7/5 14:09
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
