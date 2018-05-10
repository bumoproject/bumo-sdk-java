package io.bumo.sdk.core.adapter.bc.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易信息
 *
 * @author bumo
 */
public class SubTransaction{
    @JSONField(name = "source_address")
    private String sourceAddress;
    private long nonce;
    private String metadata;
    private Operation[] operations;
    @JSONField(name = "fee_limit")
    private long feeLimit;
    @JSONField(name = "gas_price")
    private long gasPrice;


    public String getMetadata(){
        return metadata;
    }

    public void setMetadata(String metadata){
        this.metadata = metadata;
    }

    public String getSourceAddress(){
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress){
        this.sourceAddress = sourceAddress;
    }

    public long getNonce(){
        return nonce;
    }

    public void setNonce(long nonce){
        this.nonce = nonce;
    }

    public Operation[] getOperations(){
        return operations;
    }

    public void setOperations(Operation[] operations){
        this.operations = operations;
    }

	public long getFeeLimit() {
		return feeLimit;
	}

	public void setFeeLimit(long feeLimit) {
		this.feeLimit = feeLimit;
	}

	public long getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(long gasPrice) {
		this.gasPrice = gasPrice;
	}
    
    
}
