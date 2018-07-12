package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/12 01:41
 */
public class ValidatorRewardInfo {
    @JSONField(name = "validator")
    private String validator;

    @JSONField(name = "reward")
    private Long reward;

    /**
     * @Author riven
     * @Method getValidator
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/12 01:42
     */
    public String getValidator() {
        return validator;
    }

    /**
     * @Author riven
     * @Method setValidator
     * @Params [validator]
     * @Return void
     * @Date 2018/7/12 01:42
     */
    public void setValidator(String validator) {
        this.validator = validator;
    }

    /**
     * @Author riven
     * @Method getReward
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 01:42
     */
    public Long getReward() {
        return reward;
    }

    /**
     * @Author riven
     * @Method setReward
     * @Params [reward]
     * @Return void
     * @Date 2018/7/12 01:42
     */
    public void setReward(Long reward) {
        this.reward = reward;
    }
}
