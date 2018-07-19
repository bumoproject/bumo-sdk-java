package io.bumo.blockchain.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.blockchain.BlockService;
import io.bumo.common.General;
import io.bumo.crypto.http.HttpKit;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;
import io.bumo.model.request.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.*;
import io.bumo.model.response.result.data.LedgerSeq;
import io.bumo.model.response.result.data.ValidatorRewardInfo;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

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
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetNumberResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetNumberResult);
        } catch (Exception exception) {
            blockGetNumberResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetNumberResult);
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
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockCheckStatusResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockCheckStatusResult);
        } catch (Exception exception) {
            blockCheckStatusResponse.buildResponse(SdkError.SYSTEM_ERROR, blockCheckStatusResult);
        }
        return blockCheckStatusResponse;
    }

    /**
     * @Author riven
     * @Method getTransactions
     * @Params [blockGetTransactionsRequest]
     * @Return io.bumo.model.response.BlockGetTransactionsResponse
     * @Date 2018/7/12 00:38
     */
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
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetTransactions.buildResponse(SdkError.CONNECTNETWORK_ERROR, transactionGetInfoResult);
        } catch (Exception exception) {
            blockGetTransactions.buildResponse(SdkError.SYSTEM_ERROR, transactionGetInfoResult);
        }
        return blockGetTransactions;
    }

    /**
     * @Author riven
     * @Method getInfo
     * @Params [blockGetInfoRequest]
     * @Return io.bumo.model.response.BlockGetInfoResponse
     * @Date 2018/7/12 00:38
     */
    @Override
    public BlockGetInfoResponse getInfo(BlockGetInfoRequest blockGetInfoRequest) {
        BlockGetInfoResponse blockGetInfoResponse = new BlockGetInfoResponse();
        BlockGetInfoResult blockGetInfoResult = new BlockGetInfoResult();
        try {
            Long blockNumber = blockGetInfoRequest.getBlockNumber();
            if (blockNumber == null || (blockNumber != null && blockNumber < 1)) {
                throw new SDKException(SdkError.INVALID_HASH_ERROR);
            }
            String getInfoUrl = General.blockGetInfoUrl(blockNumber);
            String result = HttpKit.get(getInfoUrl);
            blockGetInfoResponse = JSONObject.parseObject(result, BlockGetInfoResponse.class);
            Integer errorCode = blockGetInfoResponse.getErrorCode();
            String errorDesc = blockGetInfoResponse.getErrorDesc();
            if (errorCode != null && errorCode == 4) {
                throw new SDKException(4, (null == errorDesc? "Block (" + blockNumber + ") does not exist" : errorDesc));
            }
            SdkError.checkErrorCode(blockGetInfoResponse);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetInfoResponse.buildResponse(errorCode, errorDesc, blockGetInfoResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetInfoResult);
        } catch (Exception exception) {
            blockGetInfoResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetInfoResult);
        }

        return blockGetInfoResponse;
    }

    /**
     * @Author riven
     * @Method getLatestInfo
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestInfoResponse
     * @Date 2018/7/12 00:52
     */
    @Override
    public BlockGetLatestInfoResponse getLatestInfo() {
        BlockGetLatestInfoResponse blockGetLatestInfoResponse = new BlockGetLatestInfoResponse();
        BlockGetLatestInfoResult blockGetLatestInfoResult = new BlockGetLatestInfoResult();
        try {
            String getInfoUrl = General.blockGetLatestInfoUrl();
            String result = HttpKit.get(getInfoUrl);
            blockGetLatestInfoResponse = JSONObject.parseObject(result, BlockGetLatestInfoResponse.class);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetLatestInfoResponse.buildResponse(errorCode, errorDesc, blockGetLatestInfoResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetLatestInfoResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetLatestInfoResult);
        } catch (Exception exception) {
            blockGetLatestInfoResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetLatestInfoResult);
        }
        return blockGetLatestInfoResponse;
    }

    /**
     * @Author riven
     * @Method getValidators
     * @Params [blockGetValidatorsRequest]
     * @Return io.bumo.model.response.BlockGetValidatorsResponse
     * @Date 2018/7/12 01:24
     */
    @Override
    public BlockGetValidatorsResponse getValidators(BlockGetValidatorsRequest blockGetValidatorsRequest) {
        BlockGetValidatorsResponse blockGetValidatorsResponse = new BlockGetValidatorsResponse();
        BlockGetValidatorsResult blockGetValidatorsResult = new BlockGetValidatorsResult();
        try {
            Long blockNumber = blockGetValidatorsRequest.getBlockNumber();
            if (blockNumber == null || (blockNumber != null && blockNumber < 1)) {
                throw new SDKException(SdkError.INVALID_HASH_ERROR);
            }
            String getInfoUrl = General.blockGetValidatorsUrl(blockNumber);
            String result = HttpKit.get(getInfoUrl);
            blockGetValidatorsResponse = JSONObject.parseObject(result, BlockGetValidatorsResponse.class);
            Integer errorCode = blockGetValidatorsResponse.getErrorCode();
            String errorDesc = blockGetValidatorsResponse.getErrorDesc();
            if (errorCode != null && errorCode == 4) {
                throw new SDKException(4, (null == errorDesc? "Block (" + blockNumber + ") does not exist" : errorDesc));
            }
            SdkError.checkErrorCode(blockGetValidatorsResponse);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetValidatorsResponse.buildResponse(errorCode, errorDesc, blockGetValidatorsResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetValidatorsResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetValidatorsResult);
        } catch (Exception exception) {
            blockGetValidatorsResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetValidatorsResult);
        }

        return blockGetValidatorsResponse;
    }

    /**
     * @Author riven
     * @Method getLatestValidators
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestValidatorsResponse
     * @Date 2018/7/12 01:36
     */
    @Override
    public BlockGetLatestValidatorsResponse getLatestValidators() {
        BlockGetLatestValidatorsResponse blockGetLatestValidatorsResponse = new BlockGetLatestValidatorsResponse();
        BlockGetLatestValidatorsResult blockGetLatestValidatorsResult = new BlockGetLatestValidatorsResult();
        try {

            String getInfoUrl = General.blockGetLatestValidatorsUrl();
            String result = HttpKit.get(getInfoUrl);
            blockGetLatestValidatorsResponse = JSONObject.parseObject(result, BlockGetLatestValidatorsResponse.class);
            SdkError.checkErrorCode(blockGetLatestValidatorsResponse);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetLatestValidatorsResponse.buildResponse(errorCode, errorDesc, blockGetLatestValidatorsResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetLatestValidatorsResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetLatestValidatorsResult);
        } catch (Exception exception) {
            blockGetLatestValidatorsResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetLatestValidatorsResult);
        }

        return blockGetLatestValidatorsResponse;
    }

    /**
     * @Author riven
     * @Method getReward
     * @Params [blockGetRewardRequest]
     * @Return io.bumo.model.response.BlockGetRewardResponse
     * @Date 2018/7/12 01:48
     */
    @Override
    public BlockGetRewardResponse getReward(BlockGetRewardRequest blockGetRewardRequest) {
        BlockGetRewardResponse blockGetRewardResponse = new BlockGetRewardResponse();
        BlockGetRewardResult blockGetRewardResult = new BlockGetRewardResult();
        try {
            Long blockNumber = blockGetRewardRequest.getBlockNumber();
            if (blockNumber == null || (blockNumber != null && blockNumber < 1)) {
                throw new SDKException(SdkError.INVALID_HASH_ERROR);
            }
            String getInfoUrl = General.blockGetRewardUrl(blockNumber);
            String result = HttpKit.get(getInfoUrl);
            BlockRewardJsonResponse blockRewardJsonResponse = JSONObject.parseObject(result, BlockRewardJsonResponse.class);
            Integer errorCode = blockRewardJsonResponse.getErrorCode();
            String errorDesc = blockRewardJsonResponse.getErrorDesc();
            if (errorCode != null && errorCode == 4) {
                throw new SDKException(4, (null == errorDesc? "Block (" + blockNumber + ") does not exist" : errorDesc));
            }
            SdkError.checkErrorCode(blockRewardJsonResponse);
            Long blockReward = blockRewardJsonResponse.getResult().getBlockReward();
            JSONObject getRewardsJson = blockRewardJsonResponse.getResult().getValidatorsReward();
            for (Map.Entry<String, Object> entry : getRewardsJson.entrySet()) {
                ValidatorRewardInfo validatorRewardInfo = new ValidatorRewardInfo();
                validatorRewardInfo.setValidator(entry.getKey());
                validatorRewardInfo.setReward(getRewardsJson.getLong(entry.getKey()));
                blockGetRewardResult.addRewareResult(validatorRewardInfo);
            }
            blockGetRewardResult.setBlockReward(blockReward);
            blockGetRewardResponse.buildResponse(SdkError.SUCCESS, blockGetRewardResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetRewardResponse.buildResponse(errorCode, errorDesc, blockGetRewardResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetRewardResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetRewardResult);
        } catch (Exception exception) {
            blockGetRewardResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetRewardResult);
        }
        return blockGetRewardResponse;
    }

    /**
     * @Author riven
     * @Method getLatestReward
     * @Params []
     * @Return io.bumo.model.response.BlockGetLatestRewardResponse
     * @Date 2018/7/12 02:08
     */
    @Override
    public BlockGetLatestRewardResponse getLatestReward() {
        BlockGetLatestRewardResponse blockGetLatestRewardResponse = new BlockGetLatestRewardResponse();
        BlockGetLatestRewardResult blockGetLatestRewardResult = new BlockGetLatestRewardResult();
        try {
            String getInfoUrl = General.blockGetLatestRewardUrl();
            String result = HttpKit.get(getInfoUrl);
            BlockRewardJsonResponse blockRewardJsonResponse = JSONObject.parseObject(result, BlockRewardJsonResponse.class);
            SdkError.checkErrorCode(blockRewardJsonResponse);
            JSONObject getLatestRewardsJson = blockRewardJsonResponse.getResult().getValidatorsReward();
            Long blockReward = blockRewardJsonResponse.getResult().getBlockReward();
            for (Map.Entry<String, Object> entry : getLatestRewardsJson.entrySet()) {
                ValidatorRewardInfo validatorLatestRewardInfo = new ValidatorRewardInfo();
                validatorLatestRewardInfo.setValidator(entry.getKey());
                validatorLatestRewardInfo.setReward(getLatestRewardsJson.getLong(entry.getKey()));
                blockGetLatestRewardResult.addRewareResult(validatorLatestRewardInfo);
            }
            blockGetLatestRewardResult.setBlockReward(blockReward);
            blockGetLatestRewardResponse.buildResponse(SdkError.SUCCESS, blockGetLatestRewardResult);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetLatestRewardResponse.buildResponse(errorCode, errorDesc, blockGetLatestRewardResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetLatestRewardResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetLatestRewardResult);
        } catch (Exception exception) {
            blockGetLatestRewardResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetLatestRewardResult);
        }
        return blockGetLatestRewardResponse;
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
            Integer errorCode = blockGetFeesResponse.getErrorCode();
            String errorDesc = blockGetFeesResponse.getErrorDesc();
            if (errorCode != null && errorCode == 4) {
                throw new SDKException(4, (null == errorDesc? "Block (" + blockNumber + ") does not exist" : errorDesc));
            }
            SdkError.checkErrorCode(blockGetFeesResponse);
        } catch (SDKException apiException) {
            Integer errorCode = apiException.getErrorCode();
            String errorDesc = apiException.getErrorDesc();
            blockGetFeesResponse.buildResponse(errorCode, errorDesc, blockGetFeesResult);
        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetFeesResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetFeesResult);
        } catch (Exception exception) {
            blockGetFeesResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetFeesResult);
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

        } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException | IOException e) {
            blockGetLatestFeesResponse.buildResponse(SdkError.CONNECTNETWORK_ERROR, blockGetLatestFeesResult);
        } catch (Exception exception) {
            blockGetLatestFeesResponse.buildResponse(SdkError.SYSTEM_ERROR, blockGetLatestFeesResult);
        }

        return blockGetLatestFeesResponse;
    }


}
