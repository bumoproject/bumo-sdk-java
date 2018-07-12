package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.TokenErrorResult;
import io.bumo.model.response.result.TokenQueryResult;

/**
 * @Author riven
 * @Date 2018/7/6 15:37
 */
public class TokenQueryResponse {
    @JSONField(name = "result")
    private TokenQueryResult result;

    @JSONField(name = "error")
    private TokenErrorResult error;

    /**
     * @Author riven
     * @Method getResult
     * @Params []
     * @Return io.bumo.model.response.result.TokenGetBalanceQueryResult
     * @Date 2018/7/11 22:32
     */
    public TokenQueryResult getResult() {
        return result;
    }

    /**
     * @Author riven
     * @Method setResult
     * @Params [result]
     * @Return void
     * @Date 2018/7/11 22:32
     */
    public void setResult(TokenQueryResult result) {
        this.result = result;
    }

    /**
     * @Author riven
     * @Method getError
     * @Params []
     * @Return io.bumo.model.response.result.TokenErrorResult
     * @Date 2018/7/11 23:37
     */
    public TokenErrorResult getError() {
        return error;
    }

    /**
     * @Author riven
     * @Method setError
     * @Params [error]
     * @Return void
     * @Date 2018/7/11 23:40
     */
    public void setError(TokenErrorResult error) {
        this.error = error;
    }
}
