package io.bumo.model.response.result.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2019/9/16 11:25
 */
public class Rewards {
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "reward")
    private JSONArray reward;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public JSONArray getReward() {
        return reward;
    }

    public void setReward(JSONArray reward) {
        this.reward = reward;
    }
}
