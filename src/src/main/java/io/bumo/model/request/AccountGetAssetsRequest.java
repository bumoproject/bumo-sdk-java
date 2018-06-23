package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/5 11:50
 */
public class AccountGetAssetsRequest {
    private String address;

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 11:51
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/5 11:52
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
