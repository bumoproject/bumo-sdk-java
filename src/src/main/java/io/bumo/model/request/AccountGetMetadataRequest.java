package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/15 14:56
 */
public class AccountGetMetadataRequest {
    private String address;
    private String key;

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/15 14:57
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/15 14:57
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @Author riven
     * @Method getKey
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/15 14:57
     */
    public String getKey() {
        return key;
    }

    /**
     * @Author riven
     * @Method setKey
     * @Params [key]
     * @Return void
     * @Date 2018/7/15 14:57
     */
    public void setKey(String key) {
        this.key = key;
    }
}
