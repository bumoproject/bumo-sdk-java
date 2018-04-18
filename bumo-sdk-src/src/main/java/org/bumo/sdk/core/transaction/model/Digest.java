package org.bumo.sdk.core.transaction.model;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class Digest{

    private String publicKey; // public key
    private byte[] originDigest; // Summary

    public Digest(String publicKey, byte[] originDigest){
        this.publicKey = publicKey;
        this.originDigest = originDigest;
    }

    public String getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(String publicKey){
        this.publicKey = publicKey;
    }

    public byte[] getOriginDigest(){
        return originDigest;
    }

    public void setOriginDigest(byte[] originDigest){
        this.originDigest = originDigest;
    }
}
