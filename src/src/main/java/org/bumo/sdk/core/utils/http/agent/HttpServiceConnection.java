package org.bumo.sdk.core.utils.http.agent;

import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

class HttpServiceConnection implements ServiceConnection{

    private ServiceEndpoint endpoint;

    private CloseableHttpClient httpClient;

    HttpServiceConnection(ServiceEndpoint endpoint, CloseableHttpClient httpClient){
        this.endpoint = endpoint;
        this.httpClient = httpClient;
    }

    CloseableHttpClient getHttpClient(){
        CloseableHttpClient cli = httpClient;
        if (cli == null) {
            throw new IllegalArgumentException("HttpServiceConnection has been closed!");
        }
        return cli;
    }

    @Override
    public void close(){
        CloseableHttpClient cli = httpClient;
        if (cli != null) {
            httpClient = null;
            try {
                cli.close();
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }

    @Override
    public ServiceEndpoint getEndpoint(){
        return endpoint;
    }
}
