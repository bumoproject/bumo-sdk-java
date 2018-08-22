package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/12 00:59
 */
public class BlockGetValidatorsRequest {
    private Long blockNumber;

    /**
     * @Author riven
     * @Method getBlockNumber
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/12 00:59
     */
    public Long getBlockNumber() {
        return blockNumber;
    }

    /**
     * @Author riven
     * @Method setBlockNumber
     * @Params [blockNumber]
     * @Return void
     * @Date 2018/7/12 00:59
     */
    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }
}
