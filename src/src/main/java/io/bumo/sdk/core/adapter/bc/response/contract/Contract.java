package io.bumo.sdk.core.adapter.bc.response.contract;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 合约
 * @author bumo
 *
 */
public class Contract{
	private String[] clients;
	@JSONField(name = "contract_id")
	private String contractId;
	private String payload;
	public String[] getClients() {
		return clients;
	}
	public void setClients(String[] clients) {
		this.clients = clients;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
}
