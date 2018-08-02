package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 15:03
 */
public class TypeThreshold {
    @JSONField(name = "type")
    private Integer type;
    @JSONField(name = "threshold")
    private Long threshold;

    public TypeThreshold() {

    }

    /**
     * @Author riven
     * @Method TypeThreshold
     * @Params [type, threshold]
     * @Return
     * @Date
     */
    public TypeThreshold(Integer type, Long threshold) {
        this.type = type;
        this.threshold = threshold;
    }

    /**
     * @Author riven
     * @Method getType
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:12
     */
    public Integer getType() {
        return type;
    }

    /**
     * @Author riven
     * @Method setType
     * @Params [type]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @Author riven
     * @Method getThreshold
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:11
     */
    public Long getThreshold() {
        return threshold;
    }

    /**
     * @Author riven
     * @Method setThreshold
     * @Params [threshold]
     * @Return void
     * @Date 2018/7/4 15:11
     */
    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }
}
