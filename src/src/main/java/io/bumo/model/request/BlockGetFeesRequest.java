package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/3 17:24
 */
public class BlockGetFeesRequest {
    private Long blockNumber;

    /**
     * @Author riven
     * @Method getBlockNumber
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:13
     */
    public Long getBlockNumber() {
        return blockNumber;
    }

    /**
     * @Author riven
     * @Method setBlockNumber
     * @Params [blockNumber]
     * @Return void
     * @Date 2018/7/4 15:12
     */
    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }
}
