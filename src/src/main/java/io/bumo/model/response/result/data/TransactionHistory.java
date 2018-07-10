package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:15
 */
public class TransactionHistory {
    @JSONField(name = "actual_fee")
    private String actualFee;

    @JSONField(name = "close_time")
    private Long closeTime;

    @JSONField(name = "error_code")
    private Long errorCode;

    @JSONField(name = "error_desc")
    private String errorDesc;

    @JSONField(name = "hash")
    private String hash;

    @JSONField(name = "ledger_seq")
    private Long ledgerSeq;

    @JSONField(name = "signatures")
    private Signature[] signatures;

    @JSONField(name = "transaction")
    private TransactionInfo transaction;

    @JSONField(name = "tx_size")
    private Long txSize;

    /**
     * @Author riven
     * @Method getActualFee
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:24
     */
    public String getActualFee() {
        return actualFee;
    }

    /**
     * @Author riven
     * @Method setActualFee
     * @Params [actualFee]
     * @Return void
     * @Date 2018/7/5 16:24
     */
    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    /**
     * @Author riven
     * @Method getCloseTime
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:24
     */
    public Long getCloseTime() {
        return closeTime;
    }

    /**
     * @Author riven
     * @Method setCloseTime
     * @Params [closeTime]
     * @Return void
     * @Date 2018/7/5 16:24
     */
    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * @Author riven
     * @Method getErrorCode
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:24
     */
    public Long getErrorCode() {
        return errorCode;
    }

    /**
     * @Author riven
     * @Method setErrorCode
     * @Params [errorCode]
     * @Return void
     * @Date 2018/7/5 16:24
     */
    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @Author riven
     * @Method getErrorDesc
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:24
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * @Author riven
     * @Method setErrorDesc
     * @Params [errorDesc]
     * @Return void
     * @Date 2018/7/5 16:24
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * @Author riven
     * @Method getHash
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:25
     */
    public String getHash() {
        return hash;
    }

    /**
     * @Author riven
     * @Method setHash
     * @Params [hash]
     * @Return void
     * @Date 2018/7/5 16:25
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @Author riven
     * @Method getLedgerSeq
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:25
     */
    public Long getLedgerSeq() {
        return ledgerSeq;
    }

    /**
     * @Author riven
     * @Method setLedgerSeq
     * @Params [ledgerSeq]
     * @Return void
     * @Date 2018/7/5 16:25
     */
    public void setLedgerSeq(Long ledgerSeq) {
        this.ledgerSeq = ledgerSeq;
    }

    /**
     * @Author riven
     * @Method getSignatures
     * @Params []
     * @Return io.bumo.model.response.result.data.Signature[]
     * @Date 2018/7/5 16:25
     */
    public Signature[] getSignatures() {
        return signatures;
    }

    /**
     * @Author riven
     * @Method setSignatures
     * @Params [signatures]
     * @Return void
     * @Date 2018/7/5 16:25
     */
    public void setSignatures(Signature[] signatures) {
        this.signatures = signatures;
    }

    /**
     * @Author riven
     * @Method getTransactionService
     * @Params []
     * @Return io.bumo.model.response.result.data.TransactionInfo
     * @Date 2018/7/5 16:25
     */
    public TransactionInfo getTransaction() {
        return transaction;
    }

    /**
     * @Author riven
     * @Method setTransaction
     * @Params [transaction]
     * @Return void
     * @Date 2018/7/5 16:25
     */
    public void setTransaction(TransactionInfo transaction) {
        this.transaction = transaction;
    }

    /**
     * @Author riven
     * @Method getTxSize
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/5 16:29
     */
    public Long getTxSize() {
        return txSize;
    }

    /**
     * @Author riven
     * @Method setTxSize
     * @Params [txSize]
     * @Return void
     * @Date 2018/7/5 16:29
     */
    public void setTxSize(Long txSize) {
        this.txSize = txSize;
    }
}
