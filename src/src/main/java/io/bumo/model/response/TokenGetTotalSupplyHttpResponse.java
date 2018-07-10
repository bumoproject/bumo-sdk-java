package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.TokenGetTotalSupplyHttpResults;

/**
 * @Author riven
 * @Date 2018/7/6 15:37
 */
public class TokenGetTotalSupplyHttpResponse {
    @JSONField(name = "result")
    private TokenGetTotalSupplyHttpResults result;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TokenIssueResult
     * @Date 2018/7/6 11:17
     */
    public TokenGetTotalSupplyHttpResults getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/6 11:18
     */
    public void setResult(TokenGetTotalSupplyHttpResults result) {
        this.result = result;
    }
}
