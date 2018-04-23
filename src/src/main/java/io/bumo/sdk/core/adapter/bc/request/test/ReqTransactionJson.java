package io.bumo.sdk.core.adapter.bc.request.test;

import com.alibaba.fastjson.annotation.JSONField;

/***
 * 
 * @author 布萌
 * @since 2018/3/16 下午2:09.
 *
 */
public class ReqTransactionJson {
	@JSONField(name = "transaction_json")
	private ReqSubTransaction reqSubTransaction;

	public ReqSubTransaction getReqSubTransaction() {
		return reqSubTransaction;
	}

	public void setReqSubTransaction(ReqSubTransaction reqSubTransaction) {
		this.reqSubTransaction = reqSubTransaction;
	}
	
	
}
