package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 02:03
 */
public class BlockCheckStatusResult {
    @JSONField(name = "is_synchronous")
    private Boolean isSynchronous;

    /**
     * @Author riven
     * @Method getSynchronous
     * @Params []
     * @Return java.lang.Boolean
     * @Date 2018/7/6 02:08
     */
    public Boolean getSynchronous() {
        return isSynchronous;
    }

    /**
     * @Author riven
     * @Method setSynchronous
     * @Params [synchronous]
     * @Return void
     * @Date 2018/7/6 02:10
     */
    public void setSynchronous(Boolean synchronous) {
        isSynchronous = synchronous;
    }
}
