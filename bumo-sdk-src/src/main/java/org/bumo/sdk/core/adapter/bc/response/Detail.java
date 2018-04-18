package org.bumo.sdk.core.adapter.bc.response;

/**
 * @author 布萌
 */
public class Detail{
    private long amount;
    private long start;
    private long length;
    private String ext;

    public long getAmount(){
        return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }

    public long getStart(){
        return start;
    }

    public void setStart(long start){
        this.start = start;
    }

    public long getLength(){
        return length;
    }

    public void setLength(long length){
        this.length = length;
    }

    public String getExt(){
        return ext;
    }

    public void setExt(String ext){
        this.ext = ext;
    }
}
