package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.0
 *
 * @author bumo
 */
public class Threshold{
    @JSONField(name = "tx_threshold")
    private long txThreshold;
    @JSONField(name = "type_thresholds")
    private List<TypeThreshold> typeThresholds = new ArrayList<>();


    public long getTxThreshold(){
        return txThreshold;
    }

    public void setTxThreshold(long txThreshold){
        this.txThreshold = txThreshold;
    }

    public List<TypeThreshold> getTypeThresholds(){
        return typeThresholds;
    }

    public void setTypeThresholds(List<TypeThreshold> typeThresholds){
        this.typeThresholds = typeThresholds;
    }
}
