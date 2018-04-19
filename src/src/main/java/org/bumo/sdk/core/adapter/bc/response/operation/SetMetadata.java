package org.bumo.sdk.core.adapter.bc.response.operation;


/**
 * 3.0 Metadata
 */
public class SetMetadata{

    private String key;
    private String value;
    private long version;

    public SetMetadata(){
    }

    public SetMetadata(String k, String v){
        this.key = k;
        this.value = v;
    }

    public SetMetadata(String k, String v, long version){
        this.key = k;
        this.value = v;
        this.version = version;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public long getVersion(){
        return version;
    }

    public void setVersion(long version){
        this.version = version;
    }
}
