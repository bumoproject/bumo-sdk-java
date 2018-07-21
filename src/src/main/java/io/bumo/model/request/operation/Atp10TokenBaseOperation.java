package io.bumo.model.request.operation;

/**
 * @Author riven
 * @Date 2018/8/6 20:30
 */
public class Atp10TokenBaseOperation  extends BaseOperation  {
    private String destAddress;
    private String code;

    /**
     * @Author riven
     * @Method getDestAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/8/6 20:31
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @Author riven
     * @Method setDestAddress
     * @Params [destAddress]
     * @Return void
     * @Date 2018/8/6 20:31
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @Author riven
     * @Method getCode
     * @Params []
     * @Return java.lang.String
     * @Date 2018/8/6 20:31
     */
    public String getCode() {
        return code;
    }

    /**
     * @Author riven
     * @Method setCode
     * @Params [code]
     * @Return void
     * @Date 2018/8/6 20:32
     */
    public void setCode(String code) {
        this.code = code;
    }
}
