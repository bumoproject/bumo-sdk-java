package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.TransactionEvaluationFeeResult;

/**
 * @Author riven
 * @Date 2018/7/5 15:56
 */
public class TransactionEvaluationFeeResponse extends ResponseBase {
    @JSONField(name = "result")
    private TransactionEvaluationFeeResult result;

    /**
     * @Author riven
     * @Method
     * @Params
     * @Return
     * @Date 2018/7/5 15:57
     */
    public TransactionEvaluationFeeResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 15:57
     */
    public void setResult(TransactionEvaluationFeeResult result) {
        this.result = result;
    }

    public void buildResponse(SdkError sdkError, TransactionEvaluationFeeResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    public void buildResponse(int errorCode, String errorDesc, TransactionEvaluationFeeResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
