package io.bumo.model.response.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.ContractStat;
import io.bumo.model.response.result.data.TransactionEnvs;

/**
 * @Author riven
 * @Date 2018/7/11 18:16
 */
public class ContractCallResult<T> {
    @JSONField(name = "logs")
    private JSONObject logs;

    @JSONField(name = "query_rets")
    private JSONArray queryRets;

    @JSONField(name = "actual_fee")
    private Long actualFee;

    @JSONField(name = "stat")
    private ContractStat stat;

    @JSONField(name = "txs")
    private TransactionEnvs[] transactionEnv;

    /**
     * @Author riven
     * @Method getLogs
     * @Params []
     * @Return java.lang.Object
     * @Date 2018/7/11 18:47
     */
    public JSONObject getLogs() {
        return logs;
    }

    /**
     * @Author riven
     * @Method setLogs
     * @Params [logs]
     * @Return void
     * @Date 2018/7/11 18:47
     */
    public void setLogs(JSONObject logs) {
        this.logs = logs;
    }

    /**
     * @Author riven
     * @Method getQueryRets
     * @Params []
     * @Return java.lang.Object[]
     * @Date 2018/7/11 18:47
     */
    public JSONArray getQueryRets() {
        return queryRets;
    }

    /**
     * @Author riven
     * @Method setQueryRets
     * @Params [queryRets]
     * @Return void
     * @Date 2018/7/11 18:47
     */
    public void setQueryRets(JSONArray queryRets) {
        this.queryRets = queryRets;
    }

    /**
     * @Author riven
     * @Method getActualFee
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/11 18:47
     */
    public Long getActualFee() {
        return actualFee;
    }

    /**
     * @Author riven
     * @Method setActualFee
     * @Params [actualFee]
     * @Return void
     * @Date 2018/7/11 18:47
     */
    public void setActualFee(Long actualFee) {
        this.actualFee = actualFee;
    }

    /**
     * @Author riven
     * @Method getStat
     * @Params []
     * @Return io.bumo.model.response.result.data.ContractStat
     * @Date 2018/7/11 18:47
     */
    public ContractStat getStat() {
        return stat;
    }

    /**
     * @Author riven
     * @Method setStat
     * @Params [stat]
     * @Return void
     * @Date 2018/7/11 18:47
     */
    public void setStat(ContractStat stat) {
        this.stat = stat;
    }

    /**
     * @Author riven
     * @Method getTransactionEnv
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionEnv[]
     * @Date 2018/7/11 18:47
     */
    public TransactionEnvs[] getTransactionEnv() {
        return transactionEnv;
    }

    /**
     * @Author riven
     * @Method setTransactionEnv
     * @Params [transactionEnv]
     * @Return void
     * @Date 2018/7/11 18:48
     */
    public void setTransactionEnv(TransactionEnvs[] transactionEnv) {
        this.transactionEnv = transactionEnv;
    }
}
