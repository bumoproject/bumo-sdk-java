package io.bumo.sdk.core.adapter.bc.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易提交请求
 * @author bumo
 *
 */
public class TransactionRequest {
	private SignatureRequest[] signatures;
	@JSONField(name = "transaction_blob")
	private String transactionBlob;
	public SignatureRequest[] getSignatures() {
		return signatures;
	}
	public void setSignatures(SignatureRequest[] signatures) {
		this.signatures = signatures;
	}
	public String getTransactionBlob() {
		return transactionBlob;
	}
	public void setTransactionBlob(String transactionBlob) {
		this.transactionBlob = transactionBlob;
	}

}
