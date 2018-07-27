package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 15:37
 */
public class TokenQueryResult {
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "value")
    private String value;

    /**
     * @Author riven
     * @Method getType
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 22:33
     */
    public String getType() {
        return type;
    }

    /**
     * @Author riven
     * @Method setType
     * @Params [type]
     * @Return void
     * @Date 2018/7/11 22:33
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @Author riven
     * @Method getValue
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 22:33
     */
    public String getValue() {
        return value;
    }

    /**
     * @Author riven
     * @Method setValue
     * @Params [value]
     * @Return void
     * @Date 2018/7/11 22:33
     */
    public void setValue(String value) {
        this.value = value;
    }
}
