package io.bumo.sdk.core.utils.blockchain;

import java.io.Serializable;

public class BlockchainKeyPair implements Serializable{

    private static final long serialVersionUID = -1758433189743575436L;

    // privat key
    private String priKey;

    // public key
    private String pubKey;

    // address
    private String bumoAddress;


    public BlockchainKeyPair(String priKey, String pubKey, String bumoAddress){
        super();
        this.priKey = priKey;
        this.pubKey = pubKey;
        this.bumoAddress = bumoAddress;
    }

    public BlockchainKeyPair(){
        super();
    }

    public String getPriKey(){
        return priKey;
    }

    public void setPriKey(String priKey){
        this.priKey = priKey;
    }

    public String getPubKey(){
        return pubKey;
    }

    public void setPubKey(String pubKey){
        this.pubKey = pubKey;
    }

    public String getBumoAddress(){
        return bumoAddress;
    }

    public void setBumoAddress(String bumoAddress){
        this.bumoAddress = bumoAddress;
    }

    @Override
    public String toString(){
        return "BumoInfo [priKey=" + priKey + ", pubKey=" + pubKey + ", bumoAddress=" + bumoAddress + "]";
    }


}
