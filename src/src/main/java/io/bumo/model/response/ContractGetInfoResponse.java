package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.ContractGetInfoResult;

/**
 * @Author riven
 * @Date 2018/7/5 14:10
 */
public class ContractGetInfoResponse extends BaseResponse {
    @JSONField(name = "result")
    private ContractGetInfoResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.ContractGetInfoResult
     * @Date 2018/7/5 14:11
     */
    public ContractGetInfoResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 14:11
     */
    public void setResult(ContractGetInfoResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 14:12
     */
    public void buildResponse(SdkError sdkError, ContractGetInfoResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/5 14:12
     */
    public void buildResponse(int errorCode, String errorDesc, ContractGetInfoResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
