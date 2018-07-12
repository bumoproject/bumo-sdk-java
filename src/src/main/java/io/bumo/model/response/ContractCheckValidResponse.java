package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.ContractCheckValidResult;

/**
 * @Author riven
 * @Date 2018/7/5 15:30
 */
public class ContractCheckValidResponse extends ResponseBase {

    @JSONField(name = "result")
    private ContractCheckValidResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.ContractCheckValidResult
     * @Date 2018/7/5 15:35
     */
    public ContractCheckValidResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 15:35
     */
    public void setResult(ContractCheckValidResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/5 15:52
     */
    public void buildResponse(SdkError sdkError, ContractCheckValidResult result) {
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
    public void buildResponse(int errorCode, String errorDesc, ContractCheckValidResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
