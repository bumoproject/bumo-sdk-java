package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.AccountGetNonceResult;

/**
 * @Author riven
 * @Date 2018/7/4 16:58
 */
public class AccountGetNonceResponse extends BaseResponse {
    @JSONField(name = "result")
    private AccountGetNonceResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.AccountGetNonceResult
     * @Date 2018/7/4 17:00
     */
    public AccountGetNonceResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 17:00
     */
    public void setResult(AccountGetNonceResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 17:01
     */
    public void buildResponse(SdkError sdkError, AccountGetNonceResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/4 17:01
     */
    public void buildResponse(int errorCode, String errorDesc, AccountGetNonceResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
