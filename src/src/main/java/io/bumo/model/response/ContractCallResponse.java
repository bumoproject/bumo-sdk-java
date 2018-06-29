package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.ContractCallResult;

/**
 * @Author riven
 * @Date 2018/7/11 18:48
 */
public class ContractCallResponse extends BaseResponse {
    @JSONField(name = "result")
    private ContractCallResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.ContractCallResult
     * @Date 2018/7/11 18:48
     */
    public ContractCallResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/11 18:48
     */
    public void setResult(ContractCallResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 15:52
     */
    public void buildResponse(SdkError sdkError, ContractCallResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/5 15:55
     */
    public void buildResponse(int errorCode, String errorDesc, ContractCallResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
