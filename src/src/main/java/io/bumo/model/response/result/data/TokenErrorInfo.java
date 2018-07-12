package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/11 23:39
 */
public class TokenErrorInfo {
    @JSONField(name = "contract")
    private String contract;

    @JSONField(name = "exception")
    private String exception;

    @JSONField(name = "linenum")
    private Long linenum;

    /**
     * @Author riven
     * @Method getContract
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:33
     */
    public String getContract() {
        return contract;
    }

    /**
     * @Author riven
     * @Method setContract
     * @Params [contract]
     * @Return void
     * @Date 2018/7/11 23:33
     */
    public void setContract(String contract) {
        this.contract = contract;
    }

    /**
     * @Author riven
     * @Method getException
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:33
     */
    public String getException() {
        return exception;
    }

    /**
     * @Author riven
     * @Method setException
     * @Params [exception]
     * @Return void
     * @Date 2018/7/11 23:33
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * @Author riven
     * @Method getLinenum
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/11 23:33
     */
    public Long getLinenum() {
        return linenum;
    }

    /**
     * @Author riven
     * @Method setLinenum
     * @Params [linenum]
     * @Return void
     * @Date 2018/7/11 23:37
     */
    public void setLinenum(Long linenum) {
        this.linenum = linenum;
    }
}
