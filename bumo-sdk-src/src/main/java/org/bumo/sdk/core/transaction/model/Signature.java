package org.bumo.sdk.core.transaction.model;

import java.io.Serializable;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class Signature implements Serializable{

    private static final long serialVersionUID = -2116313967723283193L;
    private String publicKey;
    private String privateKey;


    public Signature(String publicKey, String privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(String publicKey){
        this.publicKey = publicKey;
    }

    public String getPrivateKey(){
        return privateKey;
    }

    public void setPrivateKey(String privateKey){
        this.privateKey = privateKey;
    }
}
