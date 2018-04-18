package org.bumo.sdk.core.adapter.bc.response;

/**
 * 签名者
 *
 * @author 布萌
 */
public class Signer{
    private String address;
    private long weight;


    public Signer(){
    }

    public Signer(String address, long weight){
        this.address = address;
        this.weight = weight;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public long getWeight(){
        return weight;
    }

    public void setWeight(long weight){
        this.weight = weight;
    }


}
