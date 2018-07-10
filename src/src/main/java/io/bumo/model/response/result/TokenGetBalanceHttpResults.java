package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.TokenGetBalanceResponse;

/**
 * @Author riven
 * @Date 2018/7/6 15:37
 */
public class TokenGetBalanceHttpResults {
    @JSONField(name = "query_rets")
    private TokenGetBalanceResponse[] results;

    /**
     * @Author riven
     * @Method getResults
     * @Params []
     * @Return io.bumo.model.response.TokenGetNameResponse[]
     * @Date 2018/7/6 15:44
     */
    public TokenGetBalanceResponse[] getResults() {
        return results;
    }

    /**
     * @Author riven
     * @Method setResults
     * @Params [results]
     * @Return void
     * @Date 2018/7/6 15:44
     */
    public void setResults(TokenGetBalanceResponse[] results) {
        this.results = results;
    }
}
