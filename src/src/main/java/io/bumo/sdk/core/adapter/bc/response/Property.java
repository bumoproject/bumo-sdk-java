package io.bumo.sdk.core.adapter.bc.response;

/**
 * 3.0 资产只会包含这两个属性，不会有更多
 *
 * @author 布萌
 */
public class Property{
    private String code;
    private String issuer;

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getIssuer(){
        return issuer;
    }

    public void setIssuer(String issuer){
        this.issuer = issuer;
    }

}
