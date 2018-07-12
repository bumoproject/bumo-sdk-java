package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.TokenGetDecimalsResult;

/**
 * @Author riven
 * @Date 2018/7/11 23:06
 */
public class TokenGetDecimalsResponse extends ResponseBase {
    @JSONField(name = "result")
    private TokenGetDecimalsResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TokenGetNameResult
     * @Date 2018/7/6 15:36
     */
    public TokenGetDecimalsResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/6 15:36
     */
    public void setResult(TokenGetDecimalsResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:07
     */
    public void buildResponse(SdkError sdkError, TokenGetDecimalsResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/4 15:07
     */
    public void buildResponse(int errorCode, String errorDesc, TokenGetDecimalsResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
