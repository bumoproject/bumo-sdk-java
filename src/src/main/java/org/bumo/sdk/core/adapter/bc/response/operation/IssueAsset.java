package org.bumo.sdk.core.adapter.bc.response.operation;

/**
 * 发行资产
 * @author 布萌
 *
 */
public class IssueAsset {
	private String metadata;
	private long amount;
	private String code;
	
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
