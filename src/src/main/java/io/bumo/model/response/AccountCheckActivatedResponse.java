package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.AccountCheckActivatedResult;

/**
 * @Author riven
 * @Date 2018/8/6 17:42
 */
public class AccountCheckActivatedResponse extends BaseResponse {
    @JSONField(name = "result")
    private AccountCheckActivatedResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.request.AccountCheckActivated
     * @Date 2018/8/6 17:42
     */
    public AccountCheckActivatedResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/8/6 17:42
     */
    public void setResult(AccountCheckActivatedResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void buildResponse(SdkError sdkError, AccountCheckActivatedResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void buildResponse(int errorCode, String errorDesc, AccountCheckActivatedResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
