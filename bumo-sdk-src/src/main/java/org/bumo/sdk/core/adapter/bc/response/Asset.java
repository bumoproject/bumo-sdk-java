package org.bumo.sdk.core.adapter.bc.response;

/**
 * asset
 *
 * @author bumo
 */
public class Asset{
    private long amount;
    private Key key;

    public long getAmount(){
        return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
}
