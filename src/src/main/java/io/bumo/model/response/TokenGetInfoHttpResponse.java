package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.TokenGetInfoHttpResults;

/**
 * @Author riven
 * @Date 2018/7/6 13:33
 */
public class TokenGetInfoHttpResponse {
    @JSONField(name = "result")
    private TokenGetInfoHttpResults result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TokenIssueResult
     * @Date 2018/7/6 11:17
     */
    public TokenGetInfoHttpResults getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/6 11:18
     */
    public void setResult(TokenGetInfoHttpResults result) {
        this.result = result;
    }
}
