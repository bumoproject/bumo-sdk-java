package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/4 10:24
 */
public class ResponseBase {
    @JSONField(name = "error_code")
    protected Integer errorCode;

    @JSONField(name = "error_desc")
    protected String errorDesc;

    /**
     * @Author riven
     * @Method getErrorCode
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/7/4 15:07
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @Author riven
     * @Method setErrorCode
     * @Params [errorCode]
     * @Return void
     * @Date 2018/7/4 15:07
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @Author riven
     * @Method getErrorDesc
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:08
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * @Author riven
     * @Method setErrorDesc
     * @Params [errorDesc]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [sdkError, result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void buildResponse(SdkError sdkError) {
        this.errorCode = sdkError.getCode();
        this.errorDesc = sdkError.getDescription();
    }

    /**
     * @Author riven
     * @Method buildResponse
     * @Params [errorCode, errorDesc, result]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void buildResponse(int errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
