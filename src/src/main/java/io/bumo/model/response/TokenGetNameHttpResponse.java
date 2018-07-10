package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.TokenGetNameHttpResults;

/**
 * @Author riven
 * @Date 2018/7/6 15:37
 */
public class TokenGetNameHttpResponse {
    @JSONField(name = "result")
    private TokenGetNameHttpResults result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TokenIssueResult
     * @Date 2018/7/6 11:17
     */
    public TokenGetNameHttpResults getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/6 11:18
     */
    public void setResult(TokenGetNameHttpResults result) {
        this.result = result;
    }
}
