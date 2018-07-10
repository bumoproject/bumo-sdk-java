package io.bumo.blockchain.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.blockchain.BlockService;
import io.bumo.common.General;
import io.bumo.crypto.http.HttpKit;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.BlockGetFeesRequest;
import io.bumo.model.request.BlockGetTransactionsRequest;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.LedgerSeq;

/**
 * @Author riven
 * @Date 2018/7/3 17:23
 */
public class BlockServiceImpl implements BlockService {
    /**
     * @Author riven
     * @Method getNumber
     * @Params []
     * @Return io.bumo.model.response.BlockGetNumberResponse
     * @Date 2018/7/6 02:17
     */
    @Override
    public BlockGetNumberResponse getNumber() {
        BlockGetNumberResponse blockGetNumberResponse = new BlockGetNumberResponse();
        BlockGetNumberResult blockGetNumberResult = new BlockGetNumberResult();
        try {
            String getNumberUrl = General.blockGetNumberUrl();
            String result = HttpKit.get(getNumberUrl);
            blockGetNumberResponse = JSONObject.parseObject(result, BlockGetNumberResponse.class);
        } catch (Exception e) {
            blockGetNumberResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetNumberResult);
        }
        return blockGetNumberResponse;
    }

    /**
     * @Author riven
     * @Method checkStatus
     * @Params []
     * @Return io.bumo.model.response.BlockCheckStatusResponse
     * @Date 2018/7/6 02:17
     */
    @Override
    public BlockCheckStatusResponse checkStatus() {
        BlockCheckStatusResponse blockCheckStatusResponse = new BlockCheckStatusResponse();
        BlockCheckStatusResult blockCheckStatusResult = new BlockCheckStatusResult();
        try {
            String checkStatusUrl = General.blockCheckStatusUrl();
            String result = HttpKit.get(checkStatusUrl);
            BlockCheckStatusLedgerSeqResponse blockCheckStatusLedgerSeqResponse = JSONObject.parseObject(result, BlockCheckStatusLedgerSeqResponse.class);
            if (blockCheckStatusLedgerSeqResponse == null) {
                throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
            }
            LedgerSeq ledgerSeq = blockCheckStatusLedgerSeqResponse.getLedgerSeq();
            if (ledgerSeq.getLedgerSequence() < ledgerSeq.getChainMaxLedgerSeq()) {
                blockCheckStatusResult.setSynchronous(false);
            }
            else {
                blockCheckStatusResult.setSynchronous(true);
            }
            blockCheckStatusResponse.buildResponse(SdkError.SUCCESS, blockCheckStatusResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockCheckStatusResponse.buildResponse(errorCode, errorDesc, blockCheckStatusResult);
        } catch (Exception e) {
            blockCheckStatusResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockCheckStatusResult);
        }
        return blockCheckStatusResponse;
    }

    @Override
    public BlockGetTransactionsResponse getTransactions(BlockGetTransactionsRequest blockGetTransactionsRequest) {
        BlockGetTransactionsResponse blockGetTransactions = new BlockGetTransactionsResponse();
        BlockGetTransactionsResult transactionGetInfoResult = new BlockGetTransactionsResult();
        try {
            Long blockNumber = blockGetTransactionsRequest.getBlockNumber();
            if (blockNumber == null || (blockNumber != null && blockNumber < 1)) {
                throw new SDKException(SdkError.INVALID_HASH_ERROR);
            }
            String getTransactionsUrl = General.blockGetTransactionsUrl(blockNumber);
            String result = HttpKit.get(getTransactionsUrl);
            blockGetTransactions = JSONObject.parseObject(result, BlockGetTransactionsResponse.class);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetTransactions.buildResponse(errorCode, errorDesc, transactionGetInfoResult);
        } catch (Exception e) {
            blockGetTransactions.buildResponse(SdkError.CONNECTNETWORK_ERROR, transactionGetInfoResult);
        }
        return blockGetTransactions;
    }

    /**
     * @Author riven
     * @Method getFees
     * @Params [blockGetFeesRequest]
     * @Return io.bumo.model.response.BlockGetFeesResponse
     * @Date 2018/7/6 02:17
     */
    @Override
    public BlockGetFeesResponse getFees(BlockGetFeesRequest blockGetFeesRequest) {
        BlockGetFeesResponse blockGetFeesResponse = new BlockGetFeesResponse();
        BlockGetFeesResult blockGetFeesResult = new BlockGetFeesResult();
        try {
            Long blockNumber = blockGetFeesRequest.getBlockNumber();
            if (null == blockNumber || blockNumber <= 0) {
                throw new SDKException(SdkError.INVALID_BLOCKNUMBER_ERROR);
            }
            String blockGetFeesUrl = General.blockGetFeesUrl(blockNumber);
            String result = HttpKit.get(blockGetFeesUrl);
            blockGetFeesResponse = JSON.parseObject(result, BlockGetFeesResponse.class);
            SdkError.checkErrorCode(blockGetFeesResponse);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetFeesResponse.buildResponse(errorCode, errorDesc, blockGetFeesResult);
        } catch (Exception e) {
            blockGetFeesResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetFeesResult);
        }

        return blockGetFeesResponse;
    }

    /**
     * @Author riven
     * @Method getLatestFees
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestFeesResponse
     * @Date 2018/7/6 10:35
     */
    @Override
    public BlockGetLatestFeesResponse getLatestFees() {
        BlockGetLatestFeesResponse blockGetLatestFeesResponse = new BlockGetLatestFeesResponse();
        BlockGetLatestFeesResult blockGetLatestFeesResult = new BlockGetLatestFeesResult();
        try {
            String blockGetLatestFeeUrl = General.blockGetLatestFeeUrl();
            String result = HttpKit.get(blockGetLatestFeeUrl);
            blockGetLatestFeesResponse = JSON.parseObject(result, BlockGetLatestFeesResponse.class);
            SdkError.checkErrorCode(blockGetLatestFeesResponse);

        } catch (Exception e) {
            blockGetLatestFeesResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetLatestFeesResult);
        }

        return blockGetLatestFeesResponse;
    }
}
