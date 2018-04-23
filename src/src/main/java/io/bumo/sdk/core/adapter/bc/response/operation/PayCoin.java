package io.bumo.sdk.core.adapter.bc.response.operation;

import com.alibaba.fastjson.annotation.JSONField;

public class PayCoin {
	private long amount;
	@JSONField(name = "dest_address")
	private String destAddress;
	
	private String input;
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getDestAddress() {
		return destAddress;
	}
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	
	
}
