package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.TransactionSignResult;

/**
 * @Author riven
 * @Date 2018/7/5 16:09
 */
public class TransactionSignResponse extends BaseResponse {
    @JSONField(name = "result")
    private TransactionSignResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TransactionSignResult
     * @Date 2018/7/5 16:10
     */
    public TransactionSignResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 16:10
     */
    public void setResult(TransactionSignResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 16:10
     */
    public void buildResponse(SdkError sdkError, TransactionSignResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/5 16:56
     */
    public void buildResponse(int errorCode, String errorDesc, TransactionSignResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
