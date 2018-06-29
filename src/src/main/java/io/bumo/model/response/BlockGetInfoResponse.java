package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.BlockGetInfoResult;

/**
 * @Author riven
 * @Date 2018/7/12 00:36
 */
public class BlockGetInfoResponse extends BaseResponse {
    @JSONField(name = "result")
    private BlockGetInfoResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.BlockGetFeesResult
     * @Date 2018/7/4 15:08
     */
    public BlockGetInfoResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void setResult(BlockGetInfoResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void buildResponse(SdkError sdkError, BlockGetInfoResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, BlockGetInfoResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
