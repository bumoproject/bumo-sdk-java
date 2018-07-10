package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/4 14:35
 */
public class SDKInitResult {
    @JSONField(name = "is_success")
    private boolean isSuccess;

    /**
     * @Author riven
     * @Method isSuccess
     * @Params []
     * @Return boolean
     * @Date 2018/7/4 15:11
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * @Author riven
     * @Method setSuccess
     * @Params [success]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
