package org.bumo.sdk.core.adapter.bc.request.test;



import org.bumo.sdk.core.adapter.bc.response.SubTransaction;
/***
 * 
 * @author 布萌
 * @since 2018/3/16 下午2:09.
 *
 */
public class ReqSubTransaction extends SubTransaction {
	private long fee;

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}
	
}
