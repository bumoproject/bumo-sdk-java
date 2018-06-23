package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/10 14:03
 */
public class BlockGetTransactionsRequest {
    private Long blockNumber;

    /**
     * @Author riven
     * @Method getBlockNumber
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/10 14:04
     */
    public Long getBlockNumber() {
        return blockNumber;
    }

    /**
     * @Author riven
     * @Method setBlockNumber
     * @Params [blockNumber]
     * @Return void
     * @Date 2018/7/10 14:04
     */
    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }
}
