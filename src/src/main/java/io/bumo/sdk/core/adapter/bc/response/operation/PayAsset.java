package io.bumo.sdk.core.adapter.bc.response.operation;

import com.alibaba.fastjson.annotation.JSONField;

import io.bumo.sdk.core.adapter.bc.response.Asset;

/**
 * 转移资产
 *
 * @author 布萌
 */
public class PayAsset{
    private String metadata;
    @JSONField(name = "dest_address")
    private String destAddress;
    private Asset asset;
    private String input;

    public String getMetadata(){
        return metadata;
    }

    public void setMetadata(String metadata){
        this.metadata = metadata;
    }

    public String getDestAddress(){
        return destAddress;
    }

    public void setDestAddress(String destAddress){
        this.destAddress = destAddress;
    }

    public Asset getAsset(){
        return asset;
    }

    public void setAsset(Asset asset){
        this.asset = asset;
    }

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
    
}
