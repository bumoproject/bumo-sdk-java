package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 布萌
 * @since 18/03/12 下午3:03.
 */
public class Hello{

    @JSONField(name = "bumo_version")
    private String bumoVersion;

    @JSONField(name = "current_time")
    private String currentTime;

    @JSONField(name = "hash_type")
    private Integer hashType;

    @JSONField(name = "ledger_version")
    private String ledgerVersion;

    @JSONField(name = "overlay_version")
    private String overlayVersion;

    @JSONField(name = "websocket_address")
    private String websocketAddress;

    public String getBumoVersion(){
        return bumoVersion;
    }

    public void setBumoVersion(String bumoVersion){
        this.bumoVersion = bumoVersion;
    }

    public String getCurrentTime(){
        return currentTime;
    }

    public void setCurrentTime(String currentTime){
        this.currentTime = currentTime;
    }

    public Integer getHashType(){
        return hashType;
    }

    public void setHashType(Integer hashType){
        this.hashType = hashType;
    }

    public String getLedgerVersion(){
        return ledgerVersion;
    }

    public void setLedgerVersion(String ledgerVersion){
        this.ledgerVersion = ledgerVersion;
    }

    public String getOverlayVersion(){
        return overlayVersion;
    }

    public void setOverlayVersion(String overlayVersion){
        this.overlayVersion = overlayVersion;
    }

    public String getWebsocketAddress(){
        return websocketAddress;
    }

    public void setWebsocketAddress(String websocketAddress){
        this.websocketAddress = websocketAddress;
    }

    @Override
    public String toString(){
        return "Hello{" +
                "bumoVersion='" + bumoVersion + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", hashType=" + hashType +
                ", ledgerVersion='" + ledgerVersion + '\'' +
                ", overlayVersion='" + overlayVersion + '\'' +
                ", websocketAddress='" + websocketAddress + '\'' +
                '}';
    }
}
