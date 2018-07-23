package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/8/6 17:39
 */
public class AccountCheckActivatedRequst {
    private String address;

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/8/6 17:48
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/8/6 17:48
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
