package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.TransactionEvaluateFeeResult;

/**
 * @Author riven
 * @Date 2018/7/5 15:56
 */
public class TransactionEvaluateFeeResponse extends BaseResponse {
    @JSONField(name = "result")
    private TransactionEvaluateFeeResult result;

    /**
     * @Author riven
     * @Method
     * @Params
     * @Return
     * @Date 2018/7/5 15:57
     */
    public TransactionEvaluateFeeResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 15:57
     */
    public void setResult(TransactionEvaluateFeeResult result) {
        this.result = result;
    }

    public void buildResponse(SdkError sdkError, TransactionEvaluateFeeResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    public void buildResponse(int errorCode, String errorDesc, TransactionEvaluateFeeResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
