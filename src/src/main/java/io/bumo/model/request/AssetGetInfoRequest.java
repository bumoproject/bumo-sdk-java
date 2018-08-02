package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/5 10:04
 */
public class AssetGetInfoRequest {
    private String address;
    private String code;
    private String issuer;

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 10:06
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/5 10:06
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 11:35
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/7/5 11:35
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @Author riven
     * @Method getIssuer
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 11:35
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @Author riven
     * @Method setIssuer
     * @Params [issuer]
     * @Return void
     * @Date 2018/7/5 11:45
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
