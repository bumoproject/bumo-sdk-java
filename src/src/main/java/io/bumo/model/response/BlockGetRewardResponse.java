package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.BlockGetRewardResult;

/**
 * @Author riven
 * @Date 2018/7/12 01:46
 */
public class BlockGetRewardResponse extends BaseResponse {
    @JSONField(name = "result")
    private BlockGetRewardResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.BlockGetLatestFeesResult
     * @Date 2018/7/4 15:08
     */
    public BlockGetRewardResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void setResult(BlockGetRewardResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void buildResponse(SdkError sdkError, BlockGetRewardResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void buildResponse(int errorCode, String errorDesc, BlockGetRewardResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
