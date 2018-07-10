package io.bumo.crypto;


import io.bumo.encryption.key.PrivateKey;

public class Keypair {
    private String address;
    private String publicKey;
    private String privateKey;
    public Keypair(String address,String publicKey,String privateKey){
        this.address = address;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static Keypair generator(){
        try {
            PrivateKey keyPair = new PrivateKey();
            return new Keypair(keyPair.getEncAddress(),keyPair.getEncPublicKey(),keyPair.getEncPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
