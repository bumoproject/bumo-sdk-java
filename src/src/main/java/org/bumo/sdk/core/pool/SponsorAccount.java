package org.bumo.sdk.core.pool;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class SponsorAccount{

    private String address;

    private String publicKey;

    private String privateKey;

    private Long expireTime; // Expiry time


    public SponsorAccount(String address, String publicKey, String privateKey){
        this.address = address;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        initExpire();
    }

    /**
     * Overexpired examination
     */
    public boolean expire(){
        return expireTime != null && System.currentTimeMillis() > expireTime;
    }

    /**
     * Initialization of overdue time
     */
    public void initExpire(){
        this.expireTime = LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
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

    public Long getExpireTime(){
        return expireTime;
    }

    public void setExpireTime(Long expireTime){
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof SponsorAccount)) return false;

        SponsorAccount that = (SponsorAccount) o;

        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getPublicKey() != null ? !getPublicKey().equals(that.getPublicKey()) : that.getPublicKey() != null)
            return false;
        if (getPrivateKey() != null ? !getPrivateKey().equals(that.getPrivateKey()) : that.getPrivateKey() != null)
            return false;
        return getExpireTime() != null ? getExpireTime().equals(that.getExpireTime()) : that.getExpireTime() == null;
    }

    @Override
    public int hashCode(){
        int result = getAddress() != null ? getAddress().hashCode() : 0;
        result = 31 * result + (getPublicKey() != null ? getPublicKey().hashCode() : 0);
        result = 31 * result + (getPrivateKey() != null ? getPrivateKey().hashCode() : 0);
        result = 31 * result + (getExpireTime() != null ? getExpireTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "SponsorAccount{" +
                "address='" + address + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
