package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.AccountGetBalanceResult;

/**
 * @Author riven
 * @Date 2018/7/4 16:58
 */
public class AccountGetBalanceResponse extends BaseResponse {
    @JSONField(name = "result")
    private AccountGetBalanceResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.AccountGetNonceResult
     * @Date 2018/7/4 17:00
     */
    public AccountGetBalanceResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/4 17:00
     */
    public void setResult(AccountGetBalanceResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 17:01
     */
    public void buildResponse(SdkError sdkError, AccountGetBalanceResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, AccountGetBalanceResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
