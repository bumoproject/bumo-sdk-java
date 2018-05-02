package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易信息
 *
 * @author 布萌
 */
public class Transaction{
    @JSONField(name = "close_time")
    private long closeTime;
    @JSONField(name = "error_code")
    private long errorCode;
    @JSONField(name = "error_desc")
    private String errorDesc;
    @JSONField(name = "ledger_seq")
    private long ledgerSeq;
    private Signature[] signatures;
    private SubTransaction transaction;

    private String hash;

    public String getErrorDesc(){
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc){
        this.errorDesc = errorDesc;
    }

    public long getCloseTime(){
        return closeTime;
    }

    public void setCloseTime(long closeTime){
        this.closeTime = closeTime;
    }

    public long getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(long errorCode){
        this.errorCode = errorCode;
    }

    public long getLedgerSeq(){
        return ledgerSeq;
    }

    public void setLedgerSeq(long ledgerSeq){
        this.ledgerSeq = ledgerSeq;
    }

    public Signature[] getSignatures(){
        return signatures;
    }

    public void setSignatures(Signature[] signatures){
        this.signatures = signatures;
    }

    public SubTransaction getTransaction(){
        return transaction;
    }

    public void setTransaction(SubTransaction transaction){
        this.transaction = transaction;
    }

    public String getHash(){
        return hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }


}
