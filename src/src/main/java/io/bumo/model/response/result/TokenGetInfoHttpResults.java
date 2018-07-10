package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.TokenGetInfoResponse;

/**
 * @Author riven
 * @Date 2018/7/6 13:34
 */
public class TokenGetInfoHttpResults {
    @JSONField(name = "query_rets")
    private TokenGetInfoResponse[] queryRets;

    /**
     * @Author riven
     * @Method getQueryRets
     * @Params []
     * @Return io.bumo.model.response.result.TokenAllowanceResult[]
     * @Date 2018/7/6 12:36
     */
    public TokenGetInfoResponse[] getQueryRets() {
        return queryRets;
    }

    /**
     * @Author riven
     * @Method setQueryRets
     * @Params [queryRets]
     * @Return void
     * @Date 2018/7/6 12:36
     */
    public void setQueryRets(TokenGetInfoResponse[] queryRets) {
        this.queryRets = queryRets;
    }
}
