package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ValidatorRewardInfo;

import java.util.Arrays;

/**
 * @Author riven
 * @Date 2018/7/12 01:43
 */
public class BlockGetLatestRewardResult {
    @JSONField(name = "block_reward")
    private Long blockReward;

    @JSONField(name = "validators_reward")
    private ValidatorRewardInfo[] rewardResults;

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
     * @Method getRewardResults
     * @Params []
     * @Return io.bumo.model.response.result.BlockGetRewardResult[]
     * @Date 2018/7/12 01:46
     */
    public ValidatorRewardInfo[] getRewardResults() {
        return rewardResults;
    }

    /**
     * @Author riven
     * @Method setRewardResults
     * @Params [rewardResults]
     * @Return void
     * @Date 2018/7/12 01:47
     */
    public void setRewardResults(ValidatorRewardInfo[] rewardResults) {
        this.rewardResults = rewardResults;
    }

    public void addRewareResult(ValidatorRewardInfo rewardInfo) {
        if (null == rewardResults) {
            rewardResults = new ValidatorRewardInfo[1];
        } else {
            rewardResults = Arrays.copyOf(rewardResults, rewardResults.length + 1);
        }
        rewardResults[rewardResults.length - 1] = rewardInfo;
    }
}
