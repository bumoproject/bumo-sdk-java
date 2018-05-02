package io.bumo.sdk.core.adapter.bc.response;

/**
 * 3.0
 *
 * @author 布萌
 */
public class TypeThreshold{
    /**
     * type 表示某种类型的操作 (0, 100]
     */
    private long type;

    /**
     * threshold optional，default 0, 0 ：删除该类型操作，>0 && <= MAX(INT64)：设置权重值为该值，其他：非法
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
