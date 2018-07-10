package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.AccountCreateResult;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/4 09:55
 */
public class AccountCreateResponse extends ResponseBase {
    @JSONField(name = "result")
    private AccountCreateResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.AccountCreateResult
     * @Date 2018/7/4 15:10
     */
    public AccountCreateResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void setResult(AccountCreateResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void buildResponse(SdkError sdkError, AccountCreateResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/4 15:08
     */
    public void buildResponse(int errorCode, String errorDesc, AccountCreateResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
