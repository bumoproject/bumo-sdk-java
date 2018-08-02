package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 14:57
 */
public class Priv {
    @JSONField(name = "master_weight")
    private String masterWeight;

    @JSONField(name = "signers")
    private Signer[] signers;

    @JSONField(name = "thresholds")
    private Threshold threshold;

    /**
     * @Author riven
     * @Method getMasterWeight
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:13
     */
    public String getMasterWeight() {
        return masterWeight;
    }

    /**
     * @Author riven
     * @Method setMasterWeight
     * @Params [masterWeight]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setMasterWeight(String masterWeight) {
        this.masterWeight = masterWeight;
    }

    /**
     * @Author riven
     * @Method getSigners
     * @Params []
     * @Return io.bumo.model.response.result.data.Signer[]
     * @Date 2018/7/4 15:11
     */
    public Signer[] getSigners() {
        return signers;
    }

    /**
     * @Author riven
     * @Method setSigners
     * @Params [signers]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setSigners(Signer[] signers) {
        this.signers = signers;
    }

    /**
     * @Author riven
     * @Method getThreshold
     * @Params []
     * @Return io.bumo.model.response.result.data.Threshold
     * @Date 2018/7/4 15:11
     */
    public Threshold getThreshold() {
        return threshold;
    }

    /**
     * @Author riven
     * @Method setThreshold
     * @Params [threshold]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }
}
