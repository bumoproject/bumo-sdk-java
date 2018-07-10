package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.TokenGetTotalSupplyResponse;

/**
 * @Author riven
 * @Date 2018/7/6 15:37
 */
public class TokenGetTotalSupplyHttpResults {
    @JSONField(name = "query_rets")
    private TokenGetTotalSupplyResponse[] results;

    /**
     * @Author riven
     * @Method getResults
     * @Params []
     * @Return io.bumo.model.response.TokenGetNameResponse[]
     * @Date 2018/7/6 15:44
     */
    public TokenGetTotalSupplyResponse[] getResults() {
        return results;
    }

    /**
     * @Author riven
     * @Method setResults
     * @Params [results]
     * @Return void
     * @Date 2018/7/6 15:44
     */
    public void setResults(TokenGetTotalSupplyResponse[] results) {
        this.results = results;
    }
}
