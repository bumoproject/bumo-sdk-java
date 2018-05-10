package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.0
 *
 * @author bumo
 */
public class Priv{
    /**
     * 权重
     */
    @JSONField(name = "master_weight")
    private long masterWeight;
    /**
     * 签名者
     */
    private List<Signer> signers = new ArrayList<>();

    /**
     * 门限只会有一个
     */
    @JSONField(name = "thresholds")
    private Threshold threshold = new Threshold();


    public long getMasterWeight(){
        return masterWeight;
    }

    public void setMasterWeight(long masterWeight){
        this.masterWeight = masterWeight;
    }

    public List<Signer> getSigners(){
        return signers;
    }

    public void setSigners(List<Signer> signers){
        this.signers = signers;
    }

    public Threshold getThreshold(){
        return threshold;
    }

    public void setThreshold(Threshold threshold){
        this.threshold = threshold;
    }

}
