package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/3 17:10
 */

public class AccountCreateResult {
    @JSONField(name = "private_key")
    private String privateKey;

    @JSONField(name = "public_key")
    private String publicKey;

    @JSONField(name = "address")
    private String address;

    /**
     * @Author riven
     * @Method getPrivateKey
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:12
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * @Author riven
     * @Method setPrivateKey
     * @Params [privateKey]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @Author riven
     * @Method getPublicKey
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:10
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @Author riven
     * @Method setPublicKey
     * @Params [publicKey]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:10
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
