package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 02:05
 */
public class LedgerSeq {
    @JSONField(name = "chain_max_ledger_seq")
    private Long chainMaxLedgerSeq;

    @JSONField(name = "ledger_sequence")
    private Long ledgerSequence;

    /**
     * @Author riven
     * @Method getChainMaxLedgerSeq
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/6 02:07
     */
    public Long getChainMaxLedgerSeq() {
        return chainMaxLedgerSeq;
    }

    /**
     * @Author riven
     * @Method setChainMaxLedgerSeq
     * @Params [chainMaxLedgerSeq]
     * @Return void
     * @Date 2018/7/6 02:07
     */
    public void setChainMaxLedgerSeq(Long chainMaxLedgerSeq) {
        this.chainMaxLedgerSeq = chainMaxLedgerSeq;
    }

    /**
     * @Author riven
     * @Method getLedgerSequence
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/6 02:07
     */
    public Long getLedgerSequence() {
        return ledgerSequence;
    }

    /**
     * @Author riven
     * @Method setLedgerSequence
     * @Params [ledgerSequence]
     * @Return void
     * @Date 2018/7/6 02:07
     */
    public void setLedgerSequence(Long ledgerSequence) {
        this.ledgerSequence = ledgerSequence;
    }
}
