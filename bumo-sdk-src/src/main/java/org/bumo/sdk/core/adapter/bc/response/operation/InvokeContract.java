package org.bumo.sdk.core.adapter.bc.response.operation;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 调用合约
 * @author 布萌
 *
 */
public class InvokeContract {
	private String metadata;
	private String contractor;
	private String input;
	@JSONField(name = "enable_control")
	private boolean enable_control;
	
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public boolean isEnable_control() {
		return enable_control;
	}
	public void setEnable_control(boolean enable_control) {
		this.enable_control = enable_control;
	}
	
}
