package org.bumo.sdk.core.adapter.bc.response;

/**
 * Txset
 *
 * @author 布萌
 */
public class Txset{
    private Transaction[] txs;

    public Transaction[] getTxs(){
        return txs;
    }

    public void setTxs(Transaction[] txs){
        this.txs = txs;
    }

}
