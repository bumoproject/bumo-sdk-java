package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:06
 */
public class Signature {
    @JSONField(name = "sign_data")
    private String signData;

    @JSONField(name = "public_key")
    private String publicKey;

    /**
     * @Author riven
     * @Method getSignData
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:07
     */
    public String getSignData() {
        return signData;
    }

    /**
     * @Author riven
     * @Method setSignData
     * @Params [signData]
     * @Return void
     * @Date 2018/7/5 16:07
     */
    public void setSignData(String signData) {
        this.signData = signData;
    }

    /**
     * @Author riven
     * @Method getPublicKey
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:07
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @Author riven
     * @Method setPublicKey
     * @Params [publicKey]
     * @Return void
     * @Date 2018/7/5 16:08
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
