package org.bumo.sdk.core.seq.redis;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class RedisConfig{

    private String host;
    private int port;
    private String password;
    private String database = "0";

    public RedisConfig(){
    }

    public RedisConfig(String host, int port){
        this.host = host;
        this.port = port;
    }

    public RedisConfig(String host, int port, String password){
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public RedisConfig(String host, int port, String password, String database){
        this.host = host;
        this.port = port;
        this.password = password;
        this.database = database;
    }

    public String getDatabase(){
        return database;
    }

    public void setDatabase(String database){
        this.database = database;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString(){
        return "RedisConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
