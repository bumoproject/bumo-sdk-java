package org.bumo.sdk.core.adapter.bc.response.operation;

import org.bumo.sdk.core.adapter.bc.common.Signer;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 设置权重
 *
 * @author 布萌
 */
public class SetSignerWeight{
    /**
     * 权重
     */
    @JSONField(name = "master_weight")
    private long masterWeight;
    /**
     * 签名者
     */
    private Signer[] signers;

    public long getMasterWeight(){
        return masterWeight;
    }

    public void setMasterWeight(long masterWeight){
        this.masterWeight = masterWeight;
    }

    public Signer[] getSigners(){
        return signers;
    }

    public void setSigners(Signer[] signers){
        this.signers = signers;
    }
}
