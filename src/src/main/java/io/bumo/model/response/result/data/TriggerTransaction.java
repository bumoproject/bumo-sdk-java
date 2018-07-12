package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/11 18:44
 */
public class TriggerTransaction {
    @JSONField(name = "hash")
    private String hash;

    /**
     * @Author riven
     * @Method getHash
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 18:44
     */
    public String getHash() {
        return hash;
    }

    /**
     * @Author riven
     * @Method setHash
     * @Params [hash]
     * @Return void
     * @Date 2018/7/11 18:44
     */
    public void setHash(String hash) {
        this.hash = hash;
    }
}
