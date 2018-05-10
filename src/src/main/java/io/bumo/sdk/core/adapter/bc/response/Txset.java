package io.bumo.sdk.core.adapter.bc.response;

/**
 * Txset
 *
 * @author bumo
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
