package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/8/6 17:40
 */
public class AccountCheckActivatedResult {
    @JSONField(name = "is_activated")
    private Boolean isActivated;

    /**
     * @Author riven
     * @Method getIsActivated
     * @Params []
     * @Return io.bumo.model.response.result.data.ContractInfo
     * @Date 2018/8/6 17:41
     */
    public Boolean getIsActivated() {
        return isActivated;
    }

    /**
     * @Author riven
     * @Method setIsActivated
     * @Params [isActivated]
     * @Return void
     * @Date 2018/8/6 17:41
     */
    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }
}
