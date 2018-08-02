package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 18:21
 */
public class AccountSetPrivilegeInfo {
    @JSONField(name = "master_weight")
    private String masterWeight;

    @JSONField(name = "signers")
    private Signer[] signers;

    @JSONField(name = "tx_threshold")
    private String txThreshold;

    @JSONField(name = "type_thresholds")
    private TypeThreshold[] typeThresholds;

    /**
     * @Author riven
     * @Method getMasterWeight
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 18:22
     */
    public String getMasterWeight() {
        return masterWeight;
    }

    /**
     * @Author riven
     * @Method setMasterWeight
     * @Params [masterWeight]
     * @Return void
     * @Date 2018/7/4 18:22
     */
    public void setMasterWeight(String masterWeight) {
        this.masterWeight = masterWeight;
    }

    /**
     * @Author riven
     * @Method getSigners
     * @Params []
     * @Return io.bumo.model.response.result.data.Signer[]
     * @Date 2018/7/4 18:22
     */
    public Signer[] getSigners() {
        return signers;
    }

    /**
     * @Author riven
     * @Method setSigners
     * @Params [signers]
     * @Return void
     * @Date 2018/7/4 18:22
     */
    public void setSigners(Signer[] signers) {
        this.signers = signers;
    }

    /**
     * @Author riven
     * @Method getTxThreshold
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 18:22
     */
    public String getTxThreshold() {
        return txThreshold;
    }

    /**
     * @Author riven
     * @Method setTxThreshold
     * @Params [txThreshold]
     * @Return void
     * @Date 2018/7/4 18:22
     */
    public void setTxThreshold(String txThreshold) {
        this.txThreshold = txThreshold;
    }

    /**
     * @Author riven
     * @Method getTypeThresholds
     * @Params []
     * @Return io.bumo.model.response.result.data.TypeThreshold[]
     * @Date 2018/7/4 18:22
     */
    public TypeThreshold[] getTypeThresholds() {
        return typeThresholds;
    }

    /**
     * @Author riven
     * @Method setTypeThresholds
     * @Params [typeThresholds]
     * @Return void
     * @Date 2018/7/4 18:23
     */
    public void setTypeThresholds(TypeThreshold[] typeThresholds) {
        this.typeThresholds = typeThresholds;
    }
}
