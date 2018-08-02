package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 15:00
 */
public class Signer {
    @JSONField(name = "address")
    private String address;

    @JSONField(name = "weight")
    private Long weight;

    public Signer() {

    }

    /**
     * @Author riven
     * @Method Signer
     * @Params [address, weight]
     * @Return
     * @Date
     */
    public Signer(String address, Long weight) {
        this.address = address;
        this.weight = weight;
    }

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:13
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @Author riven
     * @Method getWeight
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:11
     */
    public Long getWeight() {
        return weight;
    }

    /**
     * @Author riven
     * @Method setWeight
     * @Params [weight]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
