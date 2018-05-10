package io.bumo.sdk.core.adapter.bc.response;

/**
 * 3.0
 *
 * @author bumo
 */
public class TypeThreshold{
    /**
     * type Representing a certain type of operation (0, 100]
     */
    private long type;

    /**
     * threshold optional，default 0, 0 ：In addition to this type of operation，>0 && <= MAX(INT64)：Set the weight value for the value, and the other: illegal
     */
    private long threshold;


    public TypeThreshold(){

    }

    public TypeThreshold(long type, long threshold){
        this.type = type;
        this.threshold = threshold;
    }

    public long getThreshold(){
        return threshold;
    }

    public void setThreshold(long threshold){
        this.threshold = threshold;
    }

    public long getType(){
        return type;
    }

    public void setType(long type){
        this.type = type;
    }

}
