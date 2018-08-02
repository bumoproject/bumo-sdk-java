package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 16:59
 */
public class AccountGetNonceResult {
    @JSONField(name = "nonce")
    private Long nonce;

    /**
     * @Author riven
     * @Method getNonce
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 17:00
     */
    public Long getNonce() {
        return nonce;
    }

    /**
     * @Author riven
     * @Method setNonce
     * @Params [nonce]
     * @Return void
     * @Date 2018/7/4 17:00
     */
    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
}
