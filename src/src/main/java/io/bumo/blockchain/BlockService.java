package io.bumo.blockchain;

import io.bumo.model.request.BlockGetFeesRequest;
import io.bumo.model.request.BlockGetTransactionsRequest;
import io.bumo.model.response.*;

/**
 * @Author riven
 * @Date 2018/7/3 17:22
 */
public interface BlockService {
    /**
     * @Author riven
     * @Method getNumber
     * @Params []
     * @Return BlockGetNumberResponse
     * @Date 2018/7/6 01:55
     */
    public BlockGetNumberResponse getNumber();

    /**
     * @Author riven
     * @Method checkStatus
     * @Params []
     * @Return BlockCheckStatusResponse
     * @Date 2018/7/6 02:10
     */
    public BlockCheckStatusResponse checkStatus();

    /**
     * @Author riven
     * @Method getTransactions
     * @Params [blockGetTransactionsRequest]
     * @Return io.bumo.model.response.BlockGetTransactionsResponse
     * @Date 2018/7/10 15:01
     */
    public BlockGetTransactionsResponse getTransactions(BlockGetTransactionsRequest blockGetTransactionsRequest);

    /**
     * @Author riven
     * @Method getFees
     * @Params [blockGetFeesRequest]
     * @Return io.bumo.model.response.BlockGetFeesResponse
     * @Date 2018/7/4 11:52
     */
    public BlockGetFeesResponse getFees(BlockGetFeesRequest blockGetFeesRequest);

    /**
     * @Author riven
     * @Method getLatestFees
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestFeesResponse
     * @Date 2018/7/4 15:14
     */
    public BlockGetLatestFeesResponse getLatestFees();

}
