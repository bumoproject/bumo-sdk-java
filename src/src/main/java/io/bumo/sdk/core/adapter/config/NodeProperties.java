package io.bumo.sdk.core.adapter.config;

/**
 * @author bumo
 * @since 18/03/12 P.M.3:03.
 */
public class NodeProperties{

    private String host;

    private int port;

    private boolean https = false;


    public NodeProperties(String host, int port, boolean https){
        this.host = host;
        this.port = port;
        this.https = https;
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

    public boolean isHttps(){
        return https;
    }

    public void setHttps(boolean https){
        this.https = https;
    }

}
