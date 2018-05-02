package io.bumo.sdk.core.utils.http.agent;


import io.bumo.sdk.core.utils.spring.StringUtils;

/**
 * Server settings
 *
 * @author bumo
 */
public class ServiceEndpoint implements Cloneable{

    private String host;

    private int port;

    private String contextPath;

    private boolean isHttps;

    public ServiceEndpoint(String host, int port, boolean isHttps){
        this(host, port, isHttps, null);
    }

    public ServiceEndpoint(String host, int port, Boolean isHttps, String contextPath){
        this.host = host;
        this.port = port;
        this.isHttps = isHttps;
        contextPath = StringUtils.cleanPath(contextPath);
        if (StringUtils.isEmpty(contextPath)) {
            this.contextPath = "/";
        } else {
            this.contextPath = contextPath;
        }
    }

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }


    public boolean isHttps(){
        return isHttps;
    }

    public String getContextPath(){
        return contextPath;
    }

    @Override
    public ServiceEndpoint clone(){
        try {
            return (ServiceEndpoint) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e.getMessage(), e);
        }
    }

}
