package org.bumo.sdk.core.utils.net;

/**
 * Network address；
 *
 * @author bumo
 */
public class NetworkAddress{

    private String host;

    private int port;

    public NetworkAddress(String host, int port){
        this.host = host;
        this.port = port;
    }

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }

    @Override
    public String toString(){
        return host + ":" + port;
    }

}
