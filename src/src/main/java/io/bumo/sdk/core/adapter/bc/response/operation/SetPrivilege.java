package io.bumo.sdk.core.adapter.bc.response.operation;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.sdk.core.adapter.bc.common.Signer;
import io.bumo.sdk.core.adapter.bc.response.TypeThreshold;

/**
 * 设置门限
 *
 * @author bumo
 */
public class SetPrivilege {
    @JSONField(name = "master_weight")
    String masterWeight;
    private Signer[] signers;
    @JSONField(name = "tx_threshold")
    private String txThreshold;
    @JSONField(name = "type_thresholds")
    private TypeThreshold[] typeThresholds;

    public String getMasterWeight() {
        return masterWeight;
    }

    public void setMasterWeight(String masterWeight) {
        this.masterWeight = masterWeight;
    }

    public Signer[] getSigners() {
        return signers;
    }

    public void setSigners(Signer[] signers) {
        this.signers = signers;
    }

    public void AddSigner(Signer signer) {
        this.signers = new Signer[this.signers.length + 1];
    }

    public String getTxThreshold(){
        return txThreshold;
    }

    public void setTxThreshold(String txThreshold){
        this.txThreshold = txThreshold;
    }

    public TypeThreshold[] getTypeThresholds(){
        return typeThresholds;
    }

    public void setTypeThresholds(TypeThreshold[] typeThresholds){
        this.typeThresholds = typeThresholds;
    }

}
