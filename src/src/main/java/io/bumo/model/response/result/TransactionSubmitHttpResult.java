package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 22:14
 */
public class TransactionSubmitHttpResult {
    @JSONField(name = "hash")
    private String hash;

    @JSONField(name = "error_code")
    private Integer errorCode;

    @JSONField(name = "error_desc")
    private String errorDesc;

    /**
     * @Author riven
     * @Method getHash
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 22:15
     */
    public String getHash() {
        return hash;
    }

    /**
     * @Author riven
     * @Method setHash
     * @Params [hash]
     * @Return void
     * @Date 2018/7/5 22:15
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @Author riven
     * @Method getErrorCode
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/7/5 22:35
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @Author riven
     * @Method setErrorCode
     * @Params [errorCode]
     * @Return void
     * @Date 2018/7/5 22:35
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @Author riven
     * @Method getErrorDesc
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 22:35
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * @Author riven
     * @Method setErrorDesc
     * @Params [errorDesc]
     * @Return void
     * @Date 2018/7/5 22:35
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
