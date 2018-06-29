package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.BlockGetLatestFeesResult;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/4 09:55
 */
public class BlockGetLatestFeesResponse extends BaseResponse {
    @JSONField(name = "result")
    private BlockGetLatestFeesResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.BlockGetLatestFeesResult
     * @Date 2018/7/4 15:08
     */
    public BlockGetLatestFeesResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void setResult(BlockGetLatestFeesResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void buildResponse(SdkError sdkError, BlockGetLatestFeesResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, BlockGetLatestFeesResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
