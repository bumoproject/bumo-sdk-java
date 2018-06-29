package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.TransactionBuildBlobResult;

/**
 * @Author riven
 * @Date 2018/7/5 15:51
 */
public class TransactionBuildBlobResponse extends BaseResponse {
    @JSONField(name = "result")
    private TransactionBuildBlobResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TransactionBuildBlobResult
     * @Date 2018/7/5 15:51
     */
    public TransactionBuildBlobResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 15:51
     */
    public void setResult(TransactionBuildBlobResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 15:57
     */
    public void buildResponse(SdkError sdkError, TransactionBuildBlobResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/5 16:05
     */
    public void buildResponse(int errorCode, String errorDesc, TransactionBuildBlobResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
