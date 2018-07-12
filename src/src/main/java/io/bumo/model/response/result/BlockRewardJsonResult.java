package io.bumo.model.response.result;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ValidatorRewardInfo;

import java.util.Arrays;

/**
 * @Author riven
 * @Date 2018/7/12 01:43
 */
public class BlockRewardJsonResult {
    @JSONField(name = "block_reward")
    private Long blockReward;

    @JSONField(name = "validators_reward")
    private JSONObject validatorsReward;

    /**
     * @Author riven
     * @Method getBlockReward
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 01:46
     */
    public Long getBlockReward() {
        return blockReward;
    }

    /**
     * @Author riven
     * @Method setBlockReward
     * @Params [blockReward]
     * @Return void
     * @Date 2018/7/12 01:46
     */
    public void setBlockReward(Long blockReward) {
        this.blockReward = blockReward;
    }

    /**
     * @Author riven
     * @Method getLatestValidatorsReward
     * @Params []
     * @Return com.alibaba.fastjson.JSONObject
     * @Date 2018/7/12 01:46
     */
    public JSONObject getValidatorsReward() {
        return validatorsReward;
    }

    /**
     * @Author riven
     * @Method setLatestValidatorsReward
     * @Params [validatorsReward]
     * @Return void
     * @Date 2018/7/12 01:46
     */
    public void setValidatorsReward(JSONObject validatorsReward) {
        this.validatorsReward = validatorsReward;
    }
}
