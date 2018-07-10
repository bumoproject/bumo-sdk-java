package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;
import io.bumo.model.response.result.AssetGetInfoResult;

/**
 * @Author riven
 * @Date 2018/7/5 11:47
 */
public class AssetGetInfoResponse extends ResponseBase {
    @JSONField(name = "result")
    private AssetGetInfoResult result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.AssetGetInfoResult
     * @Date 2018/7/5 11:48
     */
    public AssetGetInfoResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/5 11:48
     */
    public void setResult(AssetGetInfoResult result) {
        this.result = result;
    }

    public void buildResponse(SdkError sdkError, AssetGetInfoResult result) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
        this.result = result;
    }

    public void buildResponse(int errorCode, String errorDesc, AssetGetInfoResult result) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.result = result;
    }
}
