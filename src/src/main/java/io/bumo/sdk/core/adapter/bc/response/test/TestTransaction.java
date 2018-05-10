package io.bumo.sdk.core.adapter.bc.response.test;

import io.bumo.sdk.core.adapter.bc.response.Transaction;
/***
 * 评估费用
 * @author bumo
 *
 */
public class TestTransaction extends Transaction {
	private EvalTxResult result;

	public EvalTxResult getResult() {
		return result;
	}

	public void setResult(EvalTxResult result) {
		this.result = result;
	}
	
}
