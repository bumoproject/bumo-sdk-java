package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:13
 */
public class TransactionSubmitResult {
    @JSONField(name = "hash")
    private String hash;

    /**
     * @Author riven
     * @Method getHash
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 22:37
     */
    public String getHash() {
        return hash;
    }

    /**
     * @Author riven
     * @Method setHash
     * @Params [hash]
     * @Return void
     * @Date 2018/7/5 22:37
     */
    public void setHash(String hash) {
        this.hash = hash;
    }
}
