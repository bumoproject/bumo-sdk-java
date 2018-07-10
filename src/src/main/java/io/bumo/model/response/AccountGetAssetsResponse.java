package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.AccountGetAssetsResult;

/**
 * @Author riven
 * @Date 2018/7/5 11:55
 */
public class AccountGetAssetsResponse extends ResponseBase {
    @JSONField(name = "result")
    private AccountGetAssetsResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.AccountGetAssetsResult
     * @Date 2018/7/5 11:58
     */
    public AccountGetAssetsResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 11:58
     */
    public void setResult(AccountGetAssetsResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 11:59
     */
    public void buildResponse(SdkError sdkError, AccountGetAssetsResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/5 11:59
     */
    public void buildResponse(int errorCode, String errorDesc, AccountGetAssetsResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
