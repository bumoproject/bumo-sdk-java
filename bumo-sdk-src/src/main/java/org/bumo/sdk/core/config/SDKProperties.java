package org.bumo.sdk.core.config;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Simply providing basic configuration information
 */
public class SDKProperties{
    private String eventUtis;
    private String ips;

    private boolean accountPoolEnable = false;
    private String address;
    private String publicKey;
    private String privateKey;
    private int size;
    private String poolFilepath;
    private String mark;

    private boolean redisSeqManagerEnable = false;
    private String redisHost;
    private int redisPort;
    private String redisPassword;
    private String redisDatabase = "0";
    
    private boolean initBalanceEnable = true;

    public boolean isInitBalanceEnable() {
        return initBalanceEnable;
    }

    public void setInitBalanceEnable(boolean initBalanceEnable) {
        this.initBalanceEnable = initBalanceEnable;
    }

    public String getRedisDatabase(){
        return redisDatabase;
    }

    public String getEventUtis(){
        return eventUtis;
    }

    public void setEventUtis(String eventUtis){
        this.eventUtis = eventUtis;
    }

    public String getIps(){
        return ips;
    }

    public void setIps(String ips){
        this.ips = ips;
    }

    public boolean isAccountPoolEnable(){
        return accountPoolEnable;
    }

    public void setAccountPoolEnable(boolean accountPoolEnable){
        this.accountPoolEnable = accountPoolEnable;
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

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    public String getPoolFilepath(){
        return poolFilepath;
    }

    public void setPoolFilepath(String poolFilepath){
        this.poolFilepath = poolFilepath;
    }

    public String getMark(){
        return mark;
    }

    public void setMark(String mark){
        this.mark = mark;
    }

    public boolean isRedisSeqManagerEnable(){
        return redisSeqManagerEnable;
    }

    public void setRedisSeqManagerEnable(boolean redisSeqManagerEnable){
        this.redisSeqManagerEnable = redisSeqManagerEnable;
    }

    public String getHost(){
        return redisHost;
    }

    public int getPort(){
        return redisPort;
    }

    public void setRedisPort(int port){
        this.redisPort = port;
    }

    public void setRedisHost(String host){
        this.redisHost = host;
    }

    public String getPassword(){
        return redisPassword;
    }

    public void setRedisPassword(String password){
        this.redisPassword = password;
    }
}
