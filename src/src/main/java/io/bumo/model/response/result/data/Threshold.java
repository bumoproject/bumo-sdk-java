package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Arrays;

/**
 * @Author riven
 * @Date 2018/7/4 15:01
 */
public class Threshold {
    @JSONField(name = "tx_threshold")
    private Long txThreshold;

    @JSONField(name = "type_thresholds")
    private TypeThreshold[] typeThresholds;

    /**
     * @Author riven
     * @Method getTxThreshold
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:13
     */
    public Long getTxThreshold() {
        return txThreshold;
    }

    /**
     * @Author riven
     * @Method setTxThreshold
     * @Params [txThreshold]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setTxThreshold(Long txThreshold) {
        this.txThreshold = txThreshold;
    }

    /**
     * @Author riven
     * @Method getTypeThresholds
     * @Params []
     * @Return io.bumo.model.response.result.data.TypeThreshold[]
     * @Date 2018/7/4 15:11
     */
    public TypeThreshold[] getTypeThresholds() {
        return typeThresholds;
    }

    /**
     * @Author riven
     * @Method setTypeThresholds
     * @Params [typeThresholds]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setTypeThresholds(TypeThreshold[] typeThresholds) {
        this.typeThresholds = typeThresholds;
    }

    /**
     * @Author riven
     * @Method addTypeThresholds
     * @Params [typeThreshold]
     * @Return void
     * @Date 2018/7/25 14:43
     */
    public void addTypeThresholds(TypeThreshold typeThreshold) {
        if (null == typeThresholds) {
            typeThresholds = new TypeThreshold[1];
        } else {
            typeThresholds = Arrays.copyOf(typeThresholds, typeThresholds.length + 1);
        }
        typeThresholds[typeThresholds.length - 1] = typeThreshold;
    }
}
