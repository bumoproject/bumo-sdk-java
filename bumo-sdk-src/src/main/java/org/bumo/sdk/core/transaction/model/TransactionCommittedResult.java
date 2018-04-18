package org.bumo.sdk.core.transaction.model;

import java.io.Serializable;

/**
 * Transaction submission results
 */
@SuppressWarnings("serial")
public class TransactionCommittedResult implements Serializable{

    /**
     * Transaction hash
     */
    private String hash;


    public String getHash(){
        return hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

}
