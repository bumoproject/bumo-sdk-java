package io.bumo.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.TransactionSubmitHttpResult;

/**
 * @Author riven
 * @Date 2018/7/5 22:12
 */
public class TransactionSubmitHttpResponse {
    @JSONField(name = "success_count")
    private Integer successCount;

    @JSONField(name = "results")
    private TransactionSubmitHttpResult[] results;

    /**
     * @Author riven
     * @Method getSuccessCount
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/7/6 00:19
     */
    public Integer getSuccessCount() {
        return successCount;
    }

    /**
     * @Author riven
     * @Method setSuccessCount
     * @Params [successCount]
     * @Return void
     * @Date 2018/7/6 00:19
     */
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    /**
     * @Author riven
     * @Method getResults
     * @Params []
     * @Return io.bumo.model.response.result.TransactionSubmitHttpResult[]
     * @Date 2018/7/6 00:19
     */
    public TransactionSubmitHttpResult[] getResults() {
        return results;
    }

    /**
     * @Author riven
     * @Method setResults
     * @Params [results]
     * @Return void
     * @Date 2018/7/6 00:21
     */
    public void setResults(TransactionSubmitHttpResult[] results) {
        this.results = results;
    }
}
