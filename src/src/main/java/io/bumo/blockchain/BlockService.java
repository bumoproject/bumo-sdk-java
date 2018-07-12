package io.bumo.blockchain;

import io.bumo.model.request.*;
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
     * @Method getInfo
     * @Params [blockGetInfoRequest]
     * @Return io.bumo.model.response.BlockGetInfoResponse
     * @Date 2018/7/12 00:39
     */
    public BlockGetInfoResponse getInfo(BlockGetInfoRequest blockGetInfoRequest);

    /**
     * @Author riven
     * @Method getLatestInfo
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestInfoResponse
     * @Date 2018/7/12 00:59
     */
    public BlockGetLatestInfoResponse getLatestInfo();

    /**
     * @Author riven
     * @Method getValidators
     * @Params [blockGetValidatorsRequest]
     * @Return io.bumo.model.response.BlockGetValidatorsResponse
     * @Date 2018/7/12 01:24
     */
    public BlockGetValidatorsResponse getValidators(BlockGetValidatorsRequest blockGetValidatorsRequest);

    /**
     * @Author riven
     * @Method getLatestValidators
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestValidatorsResponse
     * @Date 2018/7/12 01:36
     */
    public BlockGetLatestValidatorsResponse getLatestValidators();

    /**
     * @Author riven
     * @Method getReward
     * @Params [blockGetRewardRequest]
     * @Return io.bumo.model.response.BlockGetRewardResponse
     * @Date 2018/7/12 01:48
     */
    public BlockGetRewardResponse getReward(BlockGetRewardRequest blockGetRewardRequest);

    /**
     * @Author riven
     * @Method getLatestReward
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestRewardResponse
     * @Date 2018/7/12 02:08
     */
    public BlockGetLatestRewardResponse getLatestReward();

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
