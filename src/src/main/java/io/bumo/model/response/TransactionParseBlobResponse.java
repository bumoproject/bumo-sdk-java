package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.TransactionParseBlobResult;

/**
 * @Author riven
 * @Date 2018/7/10 16:58
 */
public class TransactionParseBlobResponse extends ResponseBase {
    @JSONField(name = "result")
    private TransactionParseBlobResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TransactionParseBlobResult
     * @Date 2018/7/10 17:00
     */
    public TransactionParseBlobResult getResult() {
        
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/10 17:00
     */
    public void setResult(TransactionParseBlobResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 16:56
     */
    public void buildResponse(SdkError sdkError, TransactionParseBlobResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, TransactionParseBlobResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
