package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 共识历史信息
 *
 * @author bumo
 */
public class ConsensusValue{
    /**
     * 3.0
     */
    @JSONField(name = "close_time")
    private long closeTime;
    @JSONField(name = "previous_ledger_hash")
    private String previousLedgerHash;

    private Txset txset;

    public long getCloseTime(){
        return closeTime;
    }

    public void setCloseTime(long closeTime){
        this.closeTime = closeTime;
    }

    public String getPreviousLedgerHash(){
        return previousLedgerHash;
    }

    public void setPreviousLedgerHash(String previousLedgerHash){
        this.previousLedgerHash = previousLedgerHash;
    }

    public Txset getTxset(){
        return txset;
    }

    public void setTxset(Txset txset){
        this.txset = txset;
    }

}
