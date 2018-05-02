package io.bumo.sdk.core.adapter.bc.request;
/**
 * 提交交易
 * @author 布萌
 *
 */
public class SubTransactionRequest {
	private TransactionRequest[] items;

	public TransactionRequest[] getItems() {
		return items;
	}

	public void setItems(TransactionRequest[] items) {
		this.items = items;
	}

}
