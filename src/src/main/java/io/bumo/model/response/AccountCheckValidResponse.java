package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.AccountCheckValidResult;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/4 09:54
 */
public class AccountCheckValidResponse extends BaseResponse {
    @JSONField(name = "result")
    private AccountCheckValidResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.AccountCheckValidResult
     * @Date 2018/7/4 15:11
     */
    public AccountCheckValidResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void setResult(AccountCheckValidResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void buildResponse(SdkError sdkError, AccountCheckValidResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, AccountCheckValidResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
