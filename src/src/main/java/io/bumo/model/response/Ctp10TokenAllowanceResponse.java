package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.Ctp10TokenAllowanceResult;

/**
 * @Author riven
 * @Date 2018/7/6 12:43
 */
public class Ctp10TokenAllowanceResponse extends BaseResponse {
    @JSONField(name = "result")
    private Ctp10TokenAllowanceResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TokenAllowanceResult
     * @Date 2018/7/6 12:44
     */
    public Ctp10TokenAllowanceResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/6 12:44
     */
    public void setResult(Ctp10TokenAllowanceResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:07
     */
    public void buildResponse(SdkError sdkError, Ctp10TokenAllowanceResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, Ctp10TokenAllowanceResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
