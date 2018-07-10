package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.TokenAllowanceResponse;

/**
 * @Author riven
 * @Date 2018/7/6 12:32
 */
public class TokenAllowanceHttpResults {
    @JSONField(name = "query_rets")
    private TokenAllowanceResponse[] queryRets;

    /**
     * @Author riven
     * @Method getQueryRets
     * @Params []
     * @Return io.bumo.model.response.result.TokenAllowanceResult[]
     * @Date 2018/7/6 12:36
     */
    public TokenAllowanceResponse[] getQueryRets() {
        return queryRets;
    }

    /**
     * @Author riven
     * @Method setQueryRets
     * @Params [queryRets]
     * @Return void
     * @Date 2018/7/6 12:36
     */
    public void setQueryRets(TokenAllowanceResponse[] queryRets) {
        this.queryRets = queryRets;
    }
}
