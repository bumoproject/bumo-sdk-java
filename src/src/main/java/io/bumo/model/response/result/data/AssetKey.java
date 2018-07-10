package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 01:23
 */
public class AssetKey {
    @JSONField(name = "code")
    private String code;

    @JSONField(name = "issuer")
    private String issuer;

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 01:25
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/5 01:25
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getIssuer
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 01:25
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @Author riven
     * @Method setIssuer
     * @Params [issuer]
     * @Return void
     * @Date 2018/7/5 01:25
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
