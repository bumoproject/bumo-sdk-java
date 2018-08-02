package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.ContractGetAddressResult;

/**
 * @Author riven
 * @Date 2018/8/1 14:58
 */
public class ContractGetAddressResponse extends BaseResponse {
    @JSONField(name = "result")
    private ContractGetAddressResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.ContractGetAddressResult
     * @Date 2018/8/1 15:06
     */
    public ContractGetAddressResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/8/1 15:06
     */
    public void setResult(ContractGetAddressResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 15:52
     */
    public void buildResponse(SdkError sdkError, ContractGetAddressResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, ContractGetAddressResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
