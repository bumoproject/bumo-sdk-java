package io.bumo.sdk.core.balance.model;

import io.bumo.sdk.core.adapter.bc.RpcService;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public class RpcServiceContent{

    private String host;
    private RpcService rpcService;

    public RpcServiceContent(String host, RpcService rpcService){
        this.host = host;
        this.rpcService = rpcService;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public RpcService getRpcService(){
        return rpcService;
    }

    public void setRpcService(RpcService rpcService){
        this.rpcService = rpcService;
    }
}
