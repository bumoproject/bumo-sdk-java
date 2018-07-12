package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 01:52
 */
public class BlockNumber {
    @JSONField(name = "seq")
    private Long blockNumber;

    /**
     * @Author riven
     * @Method getBlockNumber
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/6 01:53
     */
    public Long getBlockNumber() {
        return blockNumber;
    }

    /**
     * @Author riven
     * @Method setBlockNumber
     * @Params [blockNumber]
     * @Return void
     * @Date 2018/7/6 02:02
     */
    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }
}
