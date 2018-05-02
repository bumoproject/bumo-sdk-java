package io.bumo.sdk.core.adapter.bc.response;

public class Key {
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
